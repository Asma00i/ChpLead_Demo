package tests;

import base.BaseAPI;
import com.aventstack.extentreports.ExtentTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import pages.UserAPI;
import utils.DataUtils;
import utils.ExtentReportManager;
import utils.Validator;

import java.util.Map;

public class PositiveTests extends BaseAPI {
    static final Logger logger = LogManager.getLogger(NegativeTests.class);


    private final UserAPI userAPI = new UserAPI(this);
    private ExtentTest test;
    private String createdUserId;

    @BeforeSuite
    public void setup() {
        ExtentReportManager.initializeReport();
        test = ExtentReportManager.createTest("User API Tests");
        test.info("Setup completed. Base URI: " + RestAssured.baseURI);

    }

    @Test
    public void testPingAPI() {
        Response response = getRequestSpec().get("/users?page=2");
        Assert.assertEquals(response.getStatusCode(), 200, "API is not reachable!");
        test.pass("Ping API test passed. Status code: 200");
    }

    @Test(priority = 1)
    public void testCreateUser() {
        Map<String, Object> userPayload = DataUtils.generateRandomUser();

        Response response = userAPI.createUser(userPayload);

        Validator.validateStatusCode(response, 201);
        Validator.validateResponseField(response, "name", userPayload.get("name").toString());
        Validator.validateResponseTime(response, 2000);

        createdUserId = response.jsonPath().getString("id");
        Assert.assertNotNull(createdUserId, "User ID should not be null after creation.");
        test.pass("Create user test passed. User ID: " + createdUserId);
        logger.info("testCreateUser completed successfully.");
    }

    @Test(priority = 2, dependsOnMethods = "testCreateUser")
    public void testUpdateUser() {
        Map<String, Object> userPayload = DataUtils.generateRandomUser();
        Response response = userAPI.updateUser(Integer.parseInt(createdUserId), userPayload);

        Validator.validateStatusCode(response, 200);
        Validator.validateResponseField(response, "name", userPayload.get("name").toString());
        test.pass("Update user test passed.");
        logger.info("testUpdateUser completed successfully.");
    }

    @Test(priority = 3)
    public void testListUsers() {
        Response response = userAPI.listUsers(2);

        Validator.validateStatusCode(response, 200);
        Validator.validateResponseTime(response, 2000);
        test.pass("List users test passed.");
        logger.info("testListUser completed successfully.");
    }

    @Test(priority = 4, dependsOnMethods = "testCreateUser")
    public void testDeleteUser() {
        Response response = userAPI.deleteUser(Integer.parseInt(createdUserId));

        Validator.validateStatusCode(response, 204);
        test.pass("Delete user test passed.");

        logger.info("testDeleteUser completed successfully.");
    }


    @AfterSuite
    public void tearDown() {
        ExtentReportManager.flushReport();
        logger.info("Report flushed and test execution completed.");
    }
}
