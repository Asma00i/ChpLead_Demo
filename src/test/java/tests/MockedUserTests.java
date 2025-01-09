package tests;

import base.BaseMockAPI;
import com.aventstack.extentreports.ExtentTest;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.ExtentReportManager;
import utils.Validator;

import static io.restassured.RestAssured.given;

public class MockedUserTests extends BaseMockAPI {

    static final Logger logger = LogManager.getLogger(MockedUserTests.class);
    private ExtentTest test;

    @BeforeClass
    public void setupStubs() {
        ExtentReportManager.initializeReport();
        stubGetUsers();
        stubCreateUser();
        logger.info("Stubs set up for /users GET and POST endpoints.");
    }

    @Test
    public void testMockedGetUsers() {
        test = ExtentReportManager.createTest("Mocked Get Users Test");

        Response response = given()
                .baseUri("http://localhost:8080")
                .when()
                .get("/users");

        Validator.validateStatusCode(response, 200);
        Validator.validateResponseField(response, "data[0].name", "John Doe");

        test.pass("Mocked Get Users Test passed.");
        logger.info("testMockedGetUsers completed successfully");

    }

    @Test
    public void testMockedCreateUser() {
        test = ExtentReportManager.createTest("Mocked Create User Test");

        String payload = "{ \"name\": \"Mock User\", \"job\": \"Mock Job\" }";

        Response response = given()
                .baseUri("http://localhost:8080")
                .header("Content-Type", "application/json")
                .body(payload)
                .when()
                .post("/users");

        Validator.validateStatusCode(response, 201);
        Validator.validateResponseField(response, "name", "Mock User");
        Validator.validateResponseField(response, "job", "Mock Job");

        test.pass("Mocked Create User Test passed.");
        logger.info("testMockedCreateUser completed successfully");

    }
    @AfterSuite
    public void tearDown() {
        ExtentReportManager.flushReport();
        logger.info("Report flushed and test execution completed.");
    }
}
