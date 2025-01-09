package tests;

import base.BaseAPI;
import com.aventstack.extentreports.ExtentTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import pages.UserAPI;
import utils.DataUtils;
import utils.ExtentReportManager;
import utils.Validator;

public class PositiveCasesWithDataProviderTests extends BaseAPI {
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

    @Test(dataProvider = "userDataProvider", dataProviderClass = DataUtils.class)
    public void testCreateUser(String name, String job) {
        ExtentTest test = ExtentReportManager.createTest("Create User Test for " + name);

        JSONObject payload = new JSONObject();
        payload.put("name", name);
        payload.put("job", job);

        Response response = getRequestSpec()
                .body(payload.toString())
                .post("/users");

        Validator.validateStatusCode(response, 201);
        Validator.validateResponseField(response, "name", name);
        Validator.validateResponseField(response, "job", job);

        String createdUserId = response.jsonPath().getString("id");
        Assert.assertNotNull(createdUserId, "User ID should not be null after creation.");
        test.pass("Create User Test passed for " + name);

        logger.info("testCreateUser completed successfully using Data Provider.");

    }

    @Test(dataProvider = "userDataProvider", dataProviderClass = DataUtils.class)
    public void testUpdateUser(String name, String job) {
        ExtentTest test = ExtentReportManager.createTest("Update User Test for " + name);

        JSONObject payload = new JSONObject();
        payload.put("name", name);
        payload.put("job", job);

        Response response = getRequestSpec()
                .body(payload.toString())
                .put("/users/2");  // Assuming user ID 2 for demonstration

        Validator.validateStatusCode(response, 200);
        Validator.validateResponseField(response, "name", name);
        Validator.validateResponseField(response, "job", job);

        test.pass("Update User Test passed for " + name);
        logger.info("testUpdateUser completed successfully using Data Provider.");

    }

    @Test(dataProvider = "userDataProvider", dataProviderClass = DataUtils.class)
    public void testDeleteUser(String name, String job) {
        ExtentTest test = ExtentReportManager.createTest("Delete User Test for " + name);

        Response response = getRequestSpec()
                .delete("/users/2");  // Assuming user ID 2 for demonstration

        Validator.validateStatusCode(response, 204);
        test.pass("Delete User Test passed for " + name);
        logger.info("testDeleteUser completed successfully using Data Provider.");

    }

    @AfterSuite
    public void tearDown() {
        ExtentReportManager.flushReport();
        logger.info("Report flushed and test execution completed.");
    }
}
