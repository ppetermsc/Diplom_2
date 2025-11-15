package api.clients;

import api.ApiEndpoints;
import api.models.request.OrderRequest;
import api.models.response.OrderResponse;
import api.models.response.ErrorResponse;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderClient {

    public static Response createOrderWithAuth(OrderRequest order, String accessToken) {
        return given()
                .header("Content-type", "application/json")
                .header("Authorization", accessToken)
                .body(order)
                .when()
                .post(ApiEndpoints.BASE_URL + ApiEndpoints.ORDERS);
    }

    public static Response createOrderWithoutAuth(OrderRequest order) {
        return given()
                .header("Content-type", "application/json")
                .body(order)
                .when()
                .post(ApiEndpoints.BASE_URL + ApiEndpoints.ORDERS);
    }

    public static OrderResponse extractOrderResponse(Response response) {
        return response.as(OrderResponse.class);
    }

    public static ErrorResponse extractErrorResponse(Response response) {
        return response.as(ErrorResponse.class);
    }
}