package migration.testdata;


import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class TestDataProvider {
    
    public static JSONObject getLoginData() {
        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader("src/test/resources/loginData.json")) {
            // Parse the JSON file
            Object obj = parser.parse(reader);
            return (JSONObject) obj;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
