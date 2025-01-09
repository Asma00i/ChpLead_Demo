package base;

import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;

import static io.restassured.config.ConnectionConfig.connectionConfig;

public class BaseAPI {


    @BeforeClass
    public static RequestSpecification getRequestSpec() {

        RestAssured.baseURI = "https://reqres.in/api";


        RestAssured.useRelaxedHTTPSValidation();

        RestAssured.config = RestAssuredConfig.config()
                .httpClient(HttpClientConfig.httpClientConfig()
                        .setParam("http.connection.timeout", 10000)
                        .setParam("http.socket.timeout", 10000)
                        .setParam("http.connection-manager.timeout", 10000));

        return RestAssured.given()
                .contentType("application/json");
    }

    public void setRequestTimeout(int timeoutInMilliseconds) {
        RestAssured.config = RestAssuredConfig.config()
                .httpClient(HttpClientConfig.httpClientConfig()
                        .setParam("http.connection.timeout", timeoutInMilliseconds)
                        .setParam("http.socket.timeout", timeoutInMilliseconds));
    }

}






