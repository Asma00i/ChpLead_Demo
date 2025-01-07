package base;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class BaseAPI {


    public static RequestSpecification getRequestSpec() {
        return RestAssured
                .given()
                .baseUri("https://reqres.in/api/")
                .header("Content-Type", "application/json");
    }


}






