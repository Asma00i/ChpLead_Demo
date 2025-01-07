package tests;

import com.aventstack.extentreports.ExtentTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import pages.UserAPI;
import utils.DataUtils;
import utils.ExtentReportManager;
import utils.Validator;

import java.util.Map;

public class UserTests {

    UserAPI userAPI = new UserAPI();
    ExtentTest test;
    private String createdUserId;
    private String generatedName;
    private String generatedJob;


    @BeforeSuite
    public void setup() {
        // Initialize Extent Reports
        ExtentReportManager.initializeReport();
    }


    @Test
    public void testPingAPI() {
        Response response = RestAssured.get("https://reqres.in/api/users?page=2");
        Assert.assertEquals(response.getStatusCode(), 200, "API is not reachable!");
    }
    @Test(priority = 1)
    public void testCreateUser() {
        Map<String, Object> userPayload = DataUtils.generateRandomUser();
        Response response = userAPI.createUser(userPayload);

        Validator.validateStatusCode(response, 201);
        Validator.validateResponseField(response, "name", userPayload.get("name").toString());
        Validator.validateResponseTime(response, 2000);
    }

    @Test(priority = 2)
    public void testUpdateUser() {
        Map<String, Object> userPayload = DataUtils.generateRandomUser();
        Response response = userAPI.updateUser(2, userPayload);

        Validator.validateStatusCode(response, 200);
        Validator.validateResponseField(response, "name", userPayload.get("name").toString());
    }

    @Test(priority = 3)
    public void testListUsers() {
        Response response = userAPI.listUsers(2);

        Validator.validateStatusCode(response, 200);
        Validator.validateResponseTime(response, 2000);
    }

    @Test(priority = 4)
    public void testDeleteUser() {
        Response response = userAPI.deleteUser(2);

        Validator.validateStatusCode(response, 204);
    }

    @AfterSuite
    public void tearDown() {
        // Finalize the report
        ExtentReportManager.flushReport();
    }
}
