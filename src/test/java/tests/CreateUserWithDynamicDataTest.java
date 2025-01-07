package tests;

import base.BaseAPI;
import io.restassured.http.ContentType;
import org.apache.logging.log4j.LogManager;
import org.json.JSONObject;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import utils.ExtentReportManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.UUID;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class CreateUserWithDynamicDataTest extends BaseAPI {

    private static final Logger logger = LogManager.getLogger(CreateUserWithDynamicDataTest.class);

    private String createdUserId;
    private String generatedName;
    private String generatedJob;

    private void generateTestData() {
        generatedName = "User_" + UUID.randomUUID().toString().substring(0, 8);
        generatedJob = "Job_" + LocalDateTime.now().toString().substring(11, 19).replace(":", "");
    }

    @BeforeSuite
    public void setup() throws FileNotFoundException {
        ExtentReportManager.initializeReport();

    }

    @Test(priority = 1)
    public void createUser() {
        generateTestData();

        JSONObject payload = new JSONObject();
        payload.put("name", generatedName);
        payload.put("job", generatedJob);

        createdUserId = given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(payload.toString())
                .when()
                .post("/users")
                .then()
                .log().all()
                .statusCode(201)
                .contentType("application/json; charset=utf-8")
                .header("Date", notNullValue())
                .time(lessThan(2000L))
                .body("name", equalTo(generatedName))
                .body("job", equalTo(generatedJob))
                .log().all()
                .extract().path("id");
        logger.info("createUser completed successfully");

    }

    @Test(priority = 2, dependsOnMethods = "createUser")
    public void updateUser() {
        generateTestData();

        JSONObject payload = new JSONObject();
        payload.put("name", generatedName);
        payload.put("job", generatedJob);

        given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(payload.toString())
                .when()
                .put("/users/" + createdUserId)
                .then()
                .log().all()
                .statusCode(200)
                .contentType("application/json; charset=utf-8")
                .header("Date", notNullValue())
                .time(lessThan(2000L))
                .body("name", equalTo(generatedName))
                .body("job", equalTo(generatedJob))
                .log().all();
        logger.info("updateUser completed successfully");

    }

    @Test(priority = 3)
    public void listUsers() {
        given()
                .log().all()
                .when()
                .get("/users?page=2")
                .then()
                .log().all()
                .statusCode(200)
                .contentType("application/json; charset=utf-8")
                .header("Date", notNullValue())
                .time(lessThan(2000L))
                .body("data", notNullValue())
                .body("page", equalTo(2))
                .log().all();
        logger.info("listUser completed successfully");

    }

    @Test(priority = 4, dependsOnMethods = "createUser")
    public void deleteUser() {
        given()
                .log().all().when()
                .delete("/users/" + createdUserId)
                .then()
                .log().all()
                .statusCode(204)
                .time(lessThan(2000L))
                .log().all();
        logger.info("deleteUser completed successfully");

    }
}


