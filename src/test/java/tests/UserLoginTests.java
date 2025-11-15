package tests;

import api.models.request.UserRequest;
import api.models.response.UserResponse;
import api.models.response.ErrorResponse;
import steps.UserSteps;
import utils.TestDataGenerator;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

public class UserLoginTests {

    @Test
    @DisplayName("Вход под существующим пользователем")
    @Description("Проверка успешного входа с валидными учетными данными")
    public void loginWithValidCredentials() {
        // Arrange
        UserRequest user = TestDataGenerator.generateUniqueUser();
        UserSteps.createUser(user);

        // Act
        UserResponse response = UserSteps.loginUser(user);

        // Assert
        TestAssertions.assertSuccessResponse(response);
        TestAssertions.assertUserData(response, user.getEmail(), user.getName());
        TestAssertions.assertAccessTokenNotNull(response);
    }

    @Test
    @DisplayName("Вход с неверными учетными данными")
    @Description("Проверка ошибки при вводе неверного email или пароля")
    public void loginWithInvalidCredentials() {
        // Arrange
        UserRequest invalidUser = TestDataGenerator.generateUserWithInvalidCredentials();

        // Act
        Response response = UserSteps.loginUserAndGetResponse(invalidUser);
        ErrorResponse errorResponse = UserSteps.extractError(response);

        // Assert
        TestAssertions.assertStatusCode(response, 401);
        TestAssertions.assertErrorResponse(errorResponse, "email or password are incorrect");
    }
}