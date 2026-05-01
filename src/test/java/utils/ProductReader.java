package utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Map;

public class ProductReader {

    private Map<String, Map<String, String>> loadData() {
        try {
            // Locate the file in src/test/resources/data/[cite: 1]
            InputStream is = getClass().getClassLoader().getResourceAsStream("data/products.json");

            if (is == null) {
                throw new RuntimeException("Could not find products.json in resources/data/");
            }

            InputStreamReader reader = new InputStreamReader(is);
            Type type = new TypeToken<Map<String, Map<String, String>>>(){}.getType();

            return new Gson().fromJson(reader, type);
        } catch (Exception e) {
            throw new RuntimeException("Error reading JSON file: " + e.getMessage());
        }
    }

    public String readDesc(String name) {
        Map<String, Map<String, String>> data = loadData();
        if (data.containsKey(name)) {
            return data.get(name).get("description");
        }
        return "Description not found for: " + name;
    }

    public String readPrice(String name) {
        Map<String, Map<String, String>> data = loadData();
        if (data.containsKey(name)) {
            return data.get(name).get("price");
        }
        return "Price not found for: " + name;
    }
}