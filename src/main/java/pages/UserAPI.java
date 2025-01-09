package pages;

import io.restassured.response.Response;
import base.BaseAPI;
import java.util.Map;

public class UserAPI {

    private final BaseAPI baseAPI;

    public UserAPI(BaseAPI baseAPI) {
        this.baseAPI = baseAPI;

    }


    public Response createUser(Map<String, Object> userPayload) {
        return BaseAPI.getRequestSpec()
                .body(userPayload)
                .post("users");
    }

    public Response updateUser(int userId, Map<String, Object> userPayload) {
        return BaseAPI.getRequestSpec()
                .body(userPayload)
                .put("users/" + userId);
    }

    public Response listUsers(int pageNumber) {
        return BaseAPI.getRequestSpec()
                .queryParam("page", pageNumber)
                .get("users");
    }

    public Response deleteUser(int userId) {
        return BaseAPI.getRequestSpec()
                .delete("users/" + userId);
    }
}
