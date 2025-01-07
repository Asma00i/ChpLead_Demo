package tests;

import base.BaseAPI;
import io.restassured.http.ContentType;
import org.json.JSONObject;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import utils.ExtentReportManager;

import java.time.LocalDateTime;
import java.util.UUID;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class CreateUserWithDynamicDataTest extends BaseAPI {
    private String createdUserId;
    private String generatedName;
    private String generatedJob;

    // Generate dynamic test data
    private void generateTestData() {
        generatedName = "User_" + UUID.randomUUID().toString().substring(0, 8);
        generatedJob = "Job_" + LocalDateTime.now().toString().substring(11, 19).replace(":", "");
    }

    @BeforeSuite
    public void setup() {
        // Initialize Extent Reports
        ExtentReportManager.initializeReport();
    }

    @Test(priority = 1)
    public void createUser() {
        generateTestData();

        JSONObject payload = new JSONObject();
        payload.put("name", generatedName);
        payload.put("job", generatedJob);

        createdUserId = given()
                .contentType(ContentType.JSON)
                .body(payload.toString())
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .contentType("application/json; charset=utf-8")
                .header("Date", notNullValue())
                .time(lessThan(2000L))
                .body("name", equalTo(generatedName))
                .body("job", equalTo(generatedJob))
                .log().all()
                .extract().path("id");
    }

    @Test(priority = 2, dependsOnMethods = "createUser")
    public void updateUser() {
        generateTestData();

        JSONObject payload = new JSONObject();
        payload.put("name", generatedName);
        payload.put("job", generatedJob);

        given()
                .contentType(ContentType.JSON)
                .body(payload.toString())
                .when()
                .put("/users/" + createdUserId)
                .then()
                .statusCode(200)
                .contentType("application/json; charset=utf-8")
                .header("Date", notNullValue())
                .time(lessThan(2000L))
                .body("name", equalTo(generatedName))
                .body("job", equalTo(generatedJob))
                .log().all();
    }

    @Test(priority = 3)
    public void listUsers() {
        given()
                .when()
                .get("/users?page=2")
                .then()
                .statusCode(200)
                .contentType("application/json; charset=utf-8")
                .header("Date", notNullValue())
                .time(lessThan(2000L))
                .body("data", notNullValue())
                .body("page", equalTo(2))
                .log().all();
    }

    @Test(priority = 4, dependsOnMethods = "createUser")
    public void deleteUser() {
        given()
                .when()
                .delete("/users/" + createdUserId)
                .then()
                .statusCode(204)
                .time(lessThan(2000L))
                .log().all();
    }
}


