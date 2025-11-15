package api.clients;

import api.ApiEndpoints;
import api.models.request.UserRequest;
import api.models.response.UserResponse;
import api.models.response.ErrorResponse;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserClient {

    public static Response registerUser(UserRequest user) {
        return given()
                .header("Content-type", "application/json")
                .body(user)
                .when()
                .post(ApiEndpoints.BASE_URL + ApiEndpoints.REGISTER);
    }

    public static Response loginUser(UserRequest user) {
        return given()
                .header("Content-type", "application/json")
                .body(user)
                .when()
                .post(ApiEndpoints.BASE_URL + ApiEndpoints.LOGIN);
    }

    public static UserResponse extractUserResponse(Response response) {
        return response.as(UserResponse.class);
    }

    public static ErrorResponse extractErrorResponse(Response response) {
        return response.as(ErrorResponse.class);
    }

    public static String extractAccessToken(Response response) {
        UserResponse userResponse = response.as(UserResponse.class);
        return userResponse.getAccessToken();
    }
}