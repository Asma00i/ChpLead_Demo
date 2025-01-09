package base;


import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class BaseMockAPI {

    protected WireMockServer wireMockServer;

    @BeforeSuite
    public void setupMockServer() {
        wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().port(8080));
        wireMockServer.start();
        configureFor("localhost", 8080);
    }

    @AfterSuite
    public void tearDownMockServer() {
        if (wireMockServer != null && wireMockServer.isRunning()) {
            wireMockServer.stop();
        }
    }

    protected void stubGetUsers() {
        wireMockServer.stubFor(get(urlPathEqualTo("/users"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"data\": [ { \"id\": 1, \"name\": \"John Doe\" }, { \"id\": 2, \"name\": \"Jane Doe\" } ] }")
                        .withStatus(200)));
    }

    protected void stubCreateUser() {
        wireMockServer.stubFor(post(urlPathEqualTo("/users"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"id\": 101, \"name\": \"Mock User\", \"job\": \"Mock Job\" }")
                        .withStatus(201)));
    }
}
