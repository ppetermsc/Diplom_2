package tests;

import api.models.response.ErrorResponse;
import api.models.response.UserResponse;
import api.models.response.OrderResponse;
import io.restassured.response.Response;
import org.junit.Assert;

import static org.apache.http.HttpStatus.*; //Добавлен импорт

public class TestAssertions {

    public static void assertSuccessResponse(UserResponse response) {
        Assert.assertTrue("Response should be successful", response.isSuccess());
    }

    public static void assertSuccessResponse(OrderResponse response) {
        Assert.assertTrue("Response should be successful", response.isSuccess());
    }

    public static void assertErrorResponse(ErrorResponse response, String expectedMessage) {
        Assert.assertFalse("Response should not be successful", response.isSuccess());
        Assert.assertEquals("Error message should match", expectedMessage, response.getMessage());
    }

    public static void assertStatusCode(Response response, int expectedStatusCode) {
        Assert.assertEquals("Status code should match", expectedStatusCode, response.getStatusCode());
    }

    // Добавлены методы с читаемыми названиями статусов
    public static void assertStatusCodeBadRequest(Response response) {
        assertStatusCode(response, SC_BAD_REQUEST);
    }

    public static void assertStatusCodeUnauthorized(Response response) {
        assertStatusCode(response, SC_UNAUTHORIZED);
    }

    public static void assertStatusCodeForbidden(Response response) {
        assertStatusCode(response, SC_FORBIDDEN);
    }


    public static void assertStatusCodeInternalServerError(Response response) {
        assertStatusCode(response, SC_INTERNAL_SERVER_ERROR);
    }

    public static void assertUserData(UserResponse response, String expectedEmail, String expectedName) {
        Assert.assertEquals("User email should match", expectedEmail, response.getUser().getEmail());
        Assert.assertEquals("User name should match", expectedName, response.getUser().getName());
    }

    public static void assertAccessTokenNotNull(UserResponse response) {
        Assert.assertNotNull("Access token should not be null", response.getAccessToken());
    }

    public static void assertOrderHasIngredients(OrderResponse response) {
        Assert.assertNotNull("Order should not be null", response.getOrder());
        Assert.assertNotNull("Order ingredients should not be null", response.getOrder().getIngredients());
        Assert.assertFalse("Order should have ingredients", response.getOrder().getIngredients().isEmpty());

        // Дополнительная проверка что ингредиенты имеют _id
        for (OrderResponse.Ingredient ingredient : response.getOrder().getIngredients()) {
            Assert.assertNotNull("Ingredient should have _id", ingredient.get_id());
        }
    }
}
