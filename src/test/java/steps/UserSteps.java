package steps;

import api.clients.UserClient;
import api.models.request.UserRequest;
import api.models.response.UserResponse;
import api.models.response.ErrorResponse;
import io.qameta.allure.Step;
import io.restassured.response.Response;

public class UserSteps {

    @Step("Создать пользователя")
    public static UserResponse createUser(UserRequest user) {
        Response response = UserClient.registerUser(user);
        return UserClient.extractUserResponse(response);
    }

    @Step("Создать пользователя и получить Response")
    public static Response createUserAndGetResponse(UserRequest user) {
        return UserClient.registerUser(user);
    }

    @Step("Выполнить вход зарегистрированного пользователя")
    public static UserResponse loginUser(UserRequest user) {
        Response response = UserClient.loginUser(user);
        return UserClient.extractUserResponse(response);
    }

    @Step("Выполнить вход и получить Response")
    public static Response loginUserAndGetResponse(UserRequest user) {
        return UserClient.loginUser(user);
    }

    @Step("Получить access token после регистрации")
    public static String getAccessTokenAfterRegistration(UserRequest user) {
        Response response = UserClient.registerUser(user);
        return UserClient.extractAccessToken(response);
    }

    @Step("Извлечь ошибку из ответа")
    public static ErrorResponse extractError(Response response) {
        return UserClient.extractErrorResponse(response);
    }
}