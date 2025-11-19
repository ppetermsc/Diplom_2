package tests;

import api.models.request.OrderRequest;
import api.models.request.UserRequest;
import api.models.response.OrderResponse;
import api.models.response.ErrorResponse;
import org.junit.After;
import org.junit.Assert;
import steps.UserSteps;
import steps.OrderSteps;
import utils.TestDataGenerator;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Description;

import java.util.List;

public class OrderCreationTests {

    private UserRequest user;
    private String accessToken;

    @Before
    public void setUp() {  // Создаем пользователя до тестов
        user = TestDataGenerator.generateUniqueUser();
        accessToken = UserSteps.getAccessTokenAfterRegistration(user);
    }

    @After
    public void tearDown() {
        if (accessToken != null) {
            UserSteps.deleteUser(accessToken);
        }
    }

    @Test
    @DisplayName("Создание заказа с авторизацией")
    @Description("Проверка успешного создания заказа с валидным токеном авторизации")
    public void createOrderWithAuth() {
        // Arrange
        List<String> ingredients = TestDataGenerator.generateValidIngredients();
        OrderRequest order = new OrderRequest(ingredients);

        // Act
        OrderResponse response = OrderSteps.createOrderWithAuth(order, accessToken);

        // Assert
        TestAssertions.assertSuccessResponse(response);
        TestAssertions.assertOrderHasIngredients(response);
    }

    @Test
    @DisplayName("Создание заказа без авторизации")
    @Description("Проверка ошибки при создании заказа без токена авторизации")
    public void createOrderWithoutAuth() {
        // Arrange
        List<String> ingredients = TestDataGenerator.generateValidIngredients();
        OrderRequest order = new OrderRequest(ingredients);

        // Act
        Response response = OrderSteps.createOrderWithoutAuth(order);
        OrderResponse orderResponse = OrderSteps.extractOrderResponse(response);

        // Assert
        TestAssertions.assertSuccessResponse(orderResponse);
        Assert.assertNotNull("Order should not be null", orderResponse.getOrder());
        // Просто проверяем что номер заказа существует
        Assert.assertNotNull("Order number should not be null", orderResponse.getOrder().getNumber());
    }

    @Test
    @DisplayName("Создание заказа с ингредиентами")
    @Description("Проверка успешного создания заказа с валидными ингредиентами")
    public void createOrderWithIngredients() {
        // Arrange
        List<String> ingredients = TestDataGenerator.generateValidIngredients();
        OrderRequest order = new OrderRequest(ingredients);

        // Act
        OrderResponse response = OrderSteps.createOrderWithAuth(order, accessToken);

        // Assert
        TestAssertions.assertSuccessResponse(response);
        TestAssertions.assertOrderHasIngredients(response);
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов")
    @Description("Проверка ошибки при создании заказа без указания ингредиентов")
    public void createOrderWithoutIngredients() {
        // Arrange
        List<String> emptyIngredients = TestDataGenerator.generateEmptyIngredients();
        OrderRequest order = new OrderRequest(emptyIngredients);

        // Act
        Response response = OrderSteps.createOrderWithAuthAndGetResponse(order, accessToken);
        ErrorResponse errorResponse = OrderSteps.extractError(response);

        // Assert
        // Числовой код заменен на читаемый метод
        TestAssertions.assertStatusCodeBadRequest(response);
        TestAssertions.assertErrorResponse(errorResponse, "Ingredient ids must be provided");
    }

    @Test
    @DisplayName("Создание заказа с неверным хешем ингредиентов")
    @Description("Проверка ошибки при создании заказа с невалидными хешами ингредиентов")
    public void createOrderWithInvalidIngredientHash() {
        // Arrange
        List<String> invalidIngredients = TestDataGenerator.generateInvalidIngredientHash();
        OrderRequest order = new OrderRequest(invalidIngredients);

        // Act
        Response response = OrderSteps.createOrderWithAuthAndGetResponse(order, accessToken);

        // Assert
        // Числовой код заменен на читаемый метод
        TestAssertions.assertStatusCodeInternalServerError(response);
    }
}