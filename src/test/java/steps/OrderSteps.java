package steps;

import api.clients.OrderClient;
import api.models.request.OrderRequest;
import api.models.response.OrderResponse;
import api.models.response.ErrorResponse;
import io.qameta.allure.Step;
import io.restassured.response.Response;

public class OrderSteps {

    @Step("Создать заказ с авторизацией")
    public static OrderResponse createOrderWithAuth(OrderRequest order, String accessToken) {
        Response response = OrderClient.createOrderWithAuth(order, accessToken);
        return OrderClient.extractOrderResponse(response);
    }

    @Step("Создать заказ с авторизацией и получить Response")
    public static Response createOrderWithAuthAndGetResponse(OrderRequest order, String accessToken) {
        return OrderClient.createOrderWithAuth(order, accessToken);
    }

    @Step("Создать заказ без авторизации")
    public static Response createOrderWithoutAuth(OrderRequest order) {
        return OrderClient.createOrderWithoutAuth(order);
    }

    @Step("Извлечь OrderResponse из ответа")
    public static OrderResponse extractOrderResponse(Response response) {
        return OrderClient.extractOrderResponse(response);
    }

    @Step("Извлечь ошибку из ответа заказа")
    public static ErrorResponse extractError(Response response) {
        return OrderClient.extractErrorResponse(response);
    }
}
