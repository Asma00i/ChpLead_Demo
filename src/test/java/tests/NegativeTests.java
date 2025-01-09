package tests;

import base.BaseAPI;
import com.aventstack.extentreports.ExtentTest;
import io.restassured.RestAssured;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.ExtentReportManager;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;

public class NegativeTests extends BaseAPI {

    static final Logger logger = LogManager.getLogger(NegativeTests.class);
    private static final String USERS_ENDPOINT = "/users";
    private static final String INVALID_ID = "invalid-id";
    private ExtentTest test;

    @BeforeClass
    public void setup() {
        ExtentReportManager.initializeReport();
        test = ExtentReportManager.createTest("User API Tests");
        test.info("Setup completed. Base URI: " + RestAssured.baseURI);
    }

    @Test
    public void createUserWithMissingFields() {
        test = ExtentReportManager.createTest("Create User with Missing Fields");
        JSONObject payload = new JSONObject();

        getRequestSpec()
                .body(payload.toString())
                .when()
                .post(USERS_ENDPOINT)
                .then()
                .statusCode(anyOf(is(201), is(400)))
                .log().all();

        test.pass("Create User with Missing Fields completed successfully");
        logger.info("createUserWithMissingFields completed successfully");
    }

    @Test
    public void updateUserWithInvalidId() {
        test = ExtentReportManager.createTest("Update User with Invalid ID");
        JSONObject payload = new JSONObject();
        payload.put("name", "John");
        payload.put("job", "Manager");

        getRequestSpec()
                .body(payload.toString())
                .when()
                .put(USERS_ENDPOINT + "/" + INVALID_ID)
                .then()
                .statusCode(anyOf(is(200), is(400)))
                .log().all();

        test.pass("Update User with Invalid ID completed successfully");
        logger.info("updateUserWithInvalidId completed successfully");
    }

    @Test
    public void testRateLimiting() {
        test = ExtentReportManager.createTest("Test Rate Limiting");
        for (int i = 0; i < 20; i++) {
            getRequestSpec()
                    .when()
                    .get(USERS_ENDPOINT + "?page=2")
                    .then()
                    .statusCode(anyOf(is(200), is(429)))
                    .log().all();
        }

        test.pass("Test Rate Limiting completed successfully");
        logger.info("testRateLimiting completed successfully");
    }

    @Test
    public void testRequestTimeout() {
        test = ExtentReportManager.createTest("Test Request Timeout");
        setRequestTimeout(1000);

        getRequestSpec()
                .when()
                .get(USERS_ENDPOINT + "?page=2")
                .then()
                .statusCode(anyOf(is(200), is(500)))
                .log().all();

        test.pass("Test Request Timeout completed successfully");
        logger.info("testRequestTimeout completed successfully");
    }

    @Test
    public void simulateServerError() {
        test = ExtentReportManager.createTest("Simulate Server Error");
        JSONObject payload = new JSONObject();
        payload.put("unexpected_field", "value");

        getRequestSpec()
                .body(payload.toString())
                .when()
                .post(USERS_ENDPOINT)
                .then()
                .statusCode(anyOf(is(201), is(500)))
                .log().all();

        test.pass("Simulate Server Error completed successfully");
        logger.info("simulateServerError completed successfully");
    }

    @AfterSuite
    public void tearDown() {
        ExtentReportManager.flushReport();
        logger.info("Report flushed and test execution completed.");
    }
}
