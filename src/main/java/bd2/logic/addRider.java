package bd2.logic;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class addRider {
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
        boolean parentConsent = Boolean.parseBoolean(textFieldsValues.get("Parent consent"));
        String horseNo = textFieldsValues.get("Horse no");
        String group = textFieldsValues.get("Group");
        String license = textFieldsValues.get("License level");

        try {
            JsonObject riders = new JsonObject();

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

            riders.add("member", member);
            riders.addProperty("parent_consent", parentConsent);

            JsonObject groupObject = new JsonObject();
            groupObject.addProperty("id", Integer.parseInt(group));
            riders.add("group", groupObject);

            JsonObject horseObject = new JsonObject();
            horseObject.addProperty("id", Integer.parseInt(horseNo));
            riders.add("horse", horseObject);

            JsonArray ridersArray = new JsonArray();
            ridersArray.add(riders);

            JsonObject data = new JsonObject();
            data.add("riders", ridersArray);

            String jsonData = gson.toJson(data);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://127.0.0.1:8000/riders/add/"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonData))
                    .build();

            System.out.println(client.send(request, HttpResponse.BodyHandlers.ofString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}