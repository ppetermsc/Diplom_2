package utils;

import api.models.request.UserRequest;
import java.util.Arrays;
import java.util.List;

public class TestDataGenerator {

    public static String generateUniqueEmail() {
        return "testuser_" + System.currentTimeMillis() + "@example.com";
    }

    public static UserRequest generateUniqueUser() {
        return new UserRequest(
                generateUniqueEmail(),
                "password123",
                "Test User"
        );
    }

    public static UserRequest generateUserWithoutEmail() {
        return new UserRequest(
                null,
                "password123",
                "User Without Email"
        );
    }

    public static UserRequest generateUserWithoutPassword() {
        return new UserRequest(
                "test@example.com",
                null,
                "User Without Password"
        );
    }

    public static List<String> generateValidIngredients() {
        return Arrays.asList("61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f");
    }

    public static List<String> generateEmptyIngredients() {
        return Arrays.asList();
    }

    public static List<String> generateInvalidIngredientHash() {
        return Arrays.asList("invalid_hash_123", "another_invalid_hash");
    }
}
