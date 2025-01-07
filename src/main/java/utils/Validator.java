package utils;

import io.restassured.response.Response;
import org.testng.Assert;

public class Validator {

    public static void validateStatusCode(Response response, int expectedStatusCode) {
        Assert.assertEquals(response.getStatusCode(), expectedStatusCode, "Status Code mismatch!");
    }

    public static void validateResponseField(Response response, String field, String expectedValue) {
        Assert.assertEquals(response.jsonPath().getString(field), expectedValue, "Field value mismatch!");
    }

    public static void validateResponseTime(Response response, long maxResponseTime) {
        Assert.assertTrue(response.getTime() <= maxResponseTime, "Response time exceeded!");
    }
}
