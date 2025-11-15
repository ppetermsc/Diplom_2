package api.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.util.List;
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderResponse {
    private boolean success;
    private String name;
    private Order order;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Order {
        private List<Ingredient> ingredients;
        private int number;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Ingredient {
        private String _id;  // для проверки not null
    }
}
