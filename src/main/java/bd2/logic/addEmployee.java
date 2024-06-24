package bd2.logic;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class addEmployee {

    private static final HttpClient client = HttpClient.newHttpClient();
    private static final Gson gson = new Gson();

    public static void add(HashMap<String, String> textFieldsValues) {
        String username = textFieldsValues.get("Username");
        String password = textFieldsValues.get("Password");
        String name = textFieldsValues.get("Name");
        String surname = textFieldsValues.get("Surname");
        String dateOfBirth = textFieldsValues.get("Date of birth");
        String email = textFieldsValues.get("Email");
        String phoneNumber = textFieldsValues.get("Phone number");
        String country = textFieldsValues.get("Country");
        String city = textFieldsValues.get("City");
        String street = textFieldsValues.get("Street");
        String streetNo = textFieldsValues.get("Street number");
        String postalCode = textFieldsValues.get("Postal Code");
        String license = textFieldsValues.get("Licence");
        String position = textFieldsValues.get("Position");
        String salary = textFieldsValues.get("Salary (PLN)");
        String dateEmployed = textFieldsValues.get("Date employed");

        try {
            JsonObject employees = new JsonObject();

            JsonObject member = new JsonObject();
            member.addProperty("name", name);
            member.addProperty("surname", surname);
            member.addProperty("username", username);
            member.addProperty("password", password);
            member.addProperty("date_of_birth", dateOfBirth);

            JsonObject address = new JsonObject();
            address.addProperty("country", country);
            address.addProperty("city", city);
            address.addProperty("street", street);
            address.addProperty("street_no", streetNo);
            address.addProperty("postal_code", postalCode);

            member.add("address", address);
            member.addProperty("phone_number", phoneNumber);
            member.addProperty("email", email);
            member.addProperty("is_active", true);

            JsonObject licence = new JsonObject();
            licence.addProperty("id", Integer.parseInt(license));
            member.add("licence", licence);

            employees.add("member", member);

            // Retrieve positions and map
            Map<String, Integer> positionMap = getPositionMap();

            JsonObject positionObject = new JsonObject();
            positionObject.addProperty("id", positionMap.get(position));
            employees.add("position", positionObject);

            employees.addProperty("salary", salary);
            employees.addProperty("date_employed", dateEmployed);

            JsonArray employeesArray = new JsonArray();
            employeesArray.add(employees);

            JsonObject data = new JsonObject();
            data.add("employees", employeesArray);

            String jsonData = gson.toJson(data);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://127.0.0.1:8000/employees/add/"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonData))
                    .build();

            System.out.println(client.send(request, HttpResponse.BodyHandlers.ofString()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Map<String, Integer> getPositionMap() {
        Map<String, Integer> positionMap = new HashMap<>();

        try {
            HttpResponse<String> response = getAllInfo.getInfo("positions/get_all/");

            JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();
            JsonArray jsonPositionsArray = jsonObject.getAsJsonArray("positions");

            for (int i = 0; i < jsonPositionsArray.size(); i++) {
                JsonObject positions = jsonPositionsArray.get(i).getAsJsonObject();
                int positionId = positions.get("id").getAsInt();
                String positionName = positions.get("name").getAsString();
                positionMap.put(positionName, positionId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return positionMap;
    }
}