package utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.DataProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.io.FileReader;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;




public class DataUtils {

    public static Map<String, Object> generateRandomUser() {
        Map<String, Object> userPayload = new HashMap<>();
        userPayload.put("name", RandomStringUtils.randomAlphabetic(8));
        userPayload.put("job", RandomStringUtils.randomAlphabetic(5));
        return userPayload;
    }


        @DataProvider(name = "userDataProvider")
        public static Iterator<Object[]> userDataProvider() throws IOException {
            List<Object[]> data = new ArrayList<>();
            String line;

            try (BufferedReader br = new BufferedReader(new FileReader("src/test/resources/user_data.csv"))) {
                while ((line = br.readLine()) != null) {
                    String[] fields = line.split(",");
                    data.add(new Object[]{fields[0], fields[1]});
                }
            }

            return data.iterator();
        }

}
