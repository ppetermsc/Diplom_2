package tests;

import api.models.request.UserRequest;
import api.models.response.UserResponse;
import api.models.response.ErrorResponse;
import org.junit.After;
import steps.UserSteps;
import utils.TestDataGenerator;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

public class UserLoginTests {

    private UserRequest existingUser;
    private String accessToken;

    @Before
    public void setUp() {
        // Создаем пользователя до тестов
        existingUser = TestDataGenerator.generateUniqueUser();
        UserSteps.createUser(existingUser);
    }

    @After
    public void tearDown() {
        if (accessToken != null) {
            UserSteps.deleteUser(accessToken);
        }
    }

    @Test
    @DisplayName("Вход под существующим пользователем")
    @Description("Проверка успешного входа с валидными учетными данными")
    public void loginWithValidCredentials() {

        UserResponse response = UserSteps.loginUser(existingUser); // Используем существующего пользователя

        // Assert
        TestAssertions.assertSuccessResponse(response);
        TestAssertions.assertUserData(response, existingUser.getEmail(), existingUser.getName());
        TestAssertions.assertAccessTokenNotNull(response);
    }

    @Test
    @DisplayName("Вход с неверным email")
    @Description("Проверка ошибки при вводе неверного email")
    public void loginWithInvalidEmail() {
        // Arrange
        UserRequest userWithInvalidEmail = new UserRequest(
                "invalid_email@example.com", // неверный email
                existingUser.getPassword(),  // правильный пароль
                existingUser.getName()
        );

        // Act
        Response response = UserSteps.loginUserAndGetResponse(userWithInvalidEmail);
        ErrorResponse errorResponse = UserSteps.extractError(response);

        // Assert
        // Числовой код заменен на читаемый метод
        TestAssertions.assertStatusCodeUnauthorized(response);
        TestAssertions.assertErrorResponse(errorResponse, "email or password are incorrect");
    }

    @Test
    @DisplayName("Вход с неверным паролем")
    @Description("Проверка ошибки при вводе неверного пароля")
    public void loginWithInvalidPassword() {
        // Arrange
        UserRequest userWithInvalidPassword = new UserRequest(
                existingUser.getEmail(),     // правильный email
                "wrong_password",           // неверный пароль
                existingUser.getName()
        );

        // Act
        Response response = UserSteps.loginUserAndGetResponse(userWithInvalidPassword);
        ErrorResponse errorResponse = UserSteps.extractError(response);

        // Assert
        // Числовой код заменен на читаемый метод
        TestAssertions.assertStatusCodeUnauthorized(response);
        TestAssertions.assertErrorResponse(errorResponse, "email or password are incorrect");
    }
}