package pages;

import io.restassured.response.Response;
import base.BaseAPI;
import java.util.Map;

public class UserAPI {

    // Create User
    public Response createUser(Map<String, Object> userPayload) {
        return BaseAPI.getRequestSpec()
                .body(userPayload)
                .post("users");
    }

    // Update User
    public Response updateUser(int userId, Map<String, Object> userPayload) {
        return BaseAPI.getRequestSpec()
                .body(userPayload)
                .put("users/" + userId);
    }

    // List Users
    public Response listUsers(int pageNumber) {
        return BaseAPI.getRequestSpec()
                .queryParam("page", pageNumber)
                .get("users");
    }

    // Delete User
    public Response deleteUser(int userId) {
        return BaseAPI.getRequestSpec()
                .delete("users/" + userId);
    }
}
