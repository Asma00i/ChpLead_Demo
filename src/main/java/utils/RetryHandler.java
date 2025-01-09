package utils;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RetryHandler {
    private static final Logger logger = LogManager.getLogger(RetryHandler.class);
    private static final int MAX_RETRIES = 3;
    private static final int RETRY_DELAY_MS = 2000; // 2 seconds

    public static Response executeWithRetry(RequestSpecification request, String endpoint, String method) {
        int attempts = 0;
        Response response = null;

        while (attempts < MAX_RETRIES) {
            try {
                attempts++;
                logger.info("Attempt {} of {} to call {}", attempts, MAX_RETRIES, endpoint);

                // Execute the request based on the method type
                switch (method.toUpperCase()) {
                    case "GET":
                        response = request.get(endpoint);
                        break;
                    case "POST":
                        response = request.post(endpoint);
                        break;
                    case "PUT":
                        response = request.put(endpoint);
                        break;
                    case "DELETE":
                        response = request.delete(endpoint);
                        break;
                    default:
                        throw new IllegalArgumentException("Unsupported HTTP method: " + method);
                }

                // If successful, return the response
                if (response.statusCode() < 500) {
                    logger.info("Request successful on attempt {}", attempts);
                    return response;
                }

            } catch (Exception e) {
                logger.error("Error during request execution: {}", e.getMessage());
            }

            try {
                Thread.sleep(RETRY_DELAY_MS);
            } catch (InterruptedException e) {
                logger.error("Retry delay interrupted: {}", e.getMessage());
            }
        }

        logger.error("Request failed after {} attempts", MAX_RETRIES);
        return response;
    }
}
