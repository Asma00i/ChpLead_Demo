package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import utils.ErrorHandler;
import utils.RetryHandler;

import static org.testng.Assert.assertEquals;

public class AdvancedErrorHandlingTest {
    private static final Logger logger = LogManager.getLogger(CreateUserWithDynamicDataTests.class);


    @Test
    public void testGetUserWithRetryAndErrorHandling() {
        RequestSpecification request = RestAssured.given()
                .baseUri("https://reqres.in/api")
                .header("Content-Type", "application/json");
        Response response = RetryHandler.executeWithRetry(request, "/users/2", "GET");
        ErrorHandler.handleResponseErrors(response);
        assertEquals(response.statusCode(), 200, "Expected status code is 200");
        logger.info("testGetUserWithRetryAndErrorHandling Completed Successfully");

    }
}
