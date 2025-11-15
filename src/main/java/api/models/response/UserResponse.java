package api.models.response;

import lombok.Data;

@Data
public class UserResponse {
    private boolean success;
    private User user;
    private String accessToken;
    private String refreshToken;

    @Data
    public static class User {
        private String email;
        private String name;
    }
}