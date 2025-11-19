package tests;

import api.models.request.UserRequest;
import api.models.response.UserResponse;
import api.models.response.ErrorResponse;
import org.junit.After;
import org.junit.Before;
import steps.UserSteps;
import utils.TestDataGenerator;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

public class UserRegistrationTests {

    private UserRequest user;
    private String accessToken;

    @Before
    public void setUp() {
        user = TestDataGenerator.generateUniqueUser();
    }

    @After
    public void tearDown() {
        // Удаляем пользователя после каждого теста, если он был создан
        if (accessToken != null) {
            UserSteps.deleteUser(accessToken);
        }
    }

    @Test
    @DisplayName("Создание уникального пользователя")
    @Description("Проверка успешной регистрации нового пользователя с валидными данными")
    public void createUniqueUser() {
        // Act
        UserResponse response = UserSteps.createUser(user);
        accessToken = response.getAccessToken(); // Сохраняем токен для удаления

        // Assert
        TestAssertions.assertSuccessResponse(response);
        TestAssertions.assertUserData(response, user.getEmail(), user.getName());
        TestAssertions.assertAccessTokenNotNull(response);
    }

    @Test
    @DisplayName("Создание уже зарегистрированного пользователя")
    @Description("Проверка ошибки при попытке регистрации существующего пользователя")
    public void createExistingUser() {
        // Arrange - сначала создаем пользователя
        UserSteps.createUser(user);

        // Act - пытаемся опять создать того же пользователя
        Response response = UserSteps.createUserAndGetResponse(user);
        ErrorResponse errorResponse = UserSteps.extractError(response);

        // Assert
        TestAssertions.assertStatusCodeForbidden(response);
        TestAssertions.assertErrorResponse(errorResponse, "User already exists");
    }

    @Test
    @DisplayName("Создание пользователя без обязательного поля email")
    @Description("Проверка ошибки при регистрации без обязательного поля email")
    public void createUserWithoutEmail() {
        // Arrange
        UserRequest userWithoutEmail = TestDataGenerator.generateUserWithoutEmail();

        // Act
        Response response = UserSteps.createUserAndGetResponse(userWithoutEmail);
        ErrorResponse errorResponse = UserSteps.extractError(response);

        // Assert
        // Числовой код заменен на читаемый метод
        TestAssertions.assertStatusCodeForbidden(response);
        TestAssertions.assertErrorResponse(errorResponse, "Email, password and name are required fields");
    }

    @Test
    @DisplayName("Создание пользователя без обязательного поля password")
    @Description("Проверка ошибки при регистрации без обязательного поля password")
    public void createUserWithoutPassword() {
        // Arrange
        UserRequest userWithoutPassword = TestDataGenerator.generateUserWithoutPassword();

        // Act
        Response response = UserSteps.createUserAndGetResponse(userWithoutPassword);
        ErrorResponse errorResponse = UserSteps.extractError(response);

        // Assert
        // Числовой код заменен на читаемый метод
        TestAssertions.assertStatusCodeForbidden(response);
        TestAssertions.assertErrorResponse(errorResponse, "Email, password and name are required fields");
    }

    @Test
    @DisplayName("Создание пользователя без обязательного поля имя")
    @Description("Проверка ошибки при регистрации без обязательного поля имя")
    public void createUserWithoutName() {
        // Arrange
        UserRequest userWithoutName = new UserRequest(
                "test@example.com",
                "password123",
                null  // отсутствует имя
        );

        // Act
        Response response = UserSteps.createUserAndGetResponse(userWithoutName);
        ErrorResponse errorResponse = UserSteps.extractError(response);

        // Assert
        // Числовой код заменен на читаемый метод
        TestAssertions.assertStatusCodeForbidden(response);
        TestAssertions.assertErrorResponse(errorResponse, "Email, password and name are required fields");
    }
}