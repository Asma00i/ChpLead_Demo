package utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.HashMap;
import java.util.Map;

public class DataUtils {

    public static Map<String, Object> generateRandomUser() {
        Map<String, Object> userPayload = new HashMap<>();
        userPayload.put("name", RandomStringUtils.randomAlphabetic(8));
        userPayload.put("job", RandomStringUtils.randomAlphabetic(5));
        return userPayload;
    }
}
