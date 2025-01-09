package tests;

import base.BaseAPI;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import utils.ExtentReportManager;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CreateUserWithDynamicDataTest extends BaseAPI {

    private static final Logger logger = LogManager.getLogger(CreateUserWithDynamicDataTest.class);
    private String createdUserId;
    private String generatedName;
    private String generatedJob;

    @BeforeSuite
    public void setup() throws FileNotFoundException {
        ExtentReportManager.initializeReport();
        logger.info("Extent Report initialized successfully.");
        BaseAPI.getRequestSpec();

    }



    private void generateTestData() {
        generatedName = "User_" + UUID.randomUUID().toString().substring(0, 8);
        generatedJob = "Job_" + LocalDateTime.now().toString().substring(11, 19).replace(":", "");
        logger.info("Generated test data - Name: {}, Job: {}", generatedName, generatedJob);
    }

    public static RequestSpecification getRequestSpec() {
        return given().contentType(ContentType.JSON).log().all();
    }

    @Test(priority = 1)
    public void testCreateUser() {
        generateTestData();

        JSONObject payload = new JSONObject();
        payload.put("name", generatedName);
        payload.put("job", generatedJob);

        createdUserId = getRequestSpec()
                .body(payload.toString())
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .body("name", equalTo(generatedName))
                .body("job", equalTo(generatedJob))
                .extract().path("id");

        logger.info("testCreateUser completed successfully. User ID: {}", createdUserId);
    }

    @Test(priority = 2, dependsOnMethods = "testCreateUser")
    public void testUpdateUser() {
        generateTestData();

        JSONObject payload = new JSONObject();
        payload.put("name", generatedName);
        payload.put("job", generatedJob);

        getRequestSpec()
                .body(payload.toString())
                .when()
                .put("/users/" + createdUserId)
                .then()
                .statusCode(200)
                .body("name", equalTo(generatedName))
                .body("job", equalTo(generatedJob));

        logger.info("testUpdateUser completed successfully.");
    }

    @Test(priority = 3)
    public void testListUsers() {
        getRequestSpec()
                .when()
                .get("/users?page=2")
                .then()
                .statusCode(200)
                .body("data", notNullValue())
                .body("page", equalTo(2));

        logger.info("testListUsers completed successfully.");
    }

    @Test(priority = 4, dependsOnMethods = "testCreateUser")
    public void testDeleteUser() {
        getRequestSpec()
                .when()
                .delete("/users/" + createdUserId)
                .then()
                .statusCode(204);

        logger.info("testDeleteUser completed successfully.");
    }

    @AfterSuite
    public void tearDown() {
        ExtentReportManager.flushReport();
        logger.info("Report flushed and test execution completed.");
    }
}
