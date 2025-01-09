package utils;

import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ErrorHandler {
    private static final Logger logger = LogManager.getLogger(ErrorHandler.class);

    public static void handleResponseErrors(Response response) {
        int statusCode = response.statusCode();

        switch (statusCode) {
            case 400:
                logger.error("Bad Request: {}", response.body().asString());
                throw new RuntimeException("Bad Request - Invalid input provided");
            case 401:
                logger.error("Unauthorized: {}", response.body().asString());
                throw new RuntimeException("Unauthorized - Check API credentials");
            case 403:
                logger.error("Forbidden: {}", response.body().asString());
                throw new RuntimeException("Forbidden - Access denied");
            case 404:
                logger.error("Not Found: {}", response.body().asString());
                throw new RuntimeException("Not Found - Endpoint or resource does not exist");
            case 500:
                logger.error("Internal Server Error: {}", response.body().asString());
                throw new RuntimeException("Internal Server Error - Issue with the server");
            case 503:
                logger.error("Service Unavailable: {}", response.body().asString());
                throw new RuntimeException("Service Unavailable - Server is temporarily down");
            default:
                if (statusCode >= 400) {
                    logger.error("Unexpected error: {} - {}", statusCode, response.body().asString());
                    throw new RuntimeException("Unexpected error occurred");
                }
        }
    }
}
