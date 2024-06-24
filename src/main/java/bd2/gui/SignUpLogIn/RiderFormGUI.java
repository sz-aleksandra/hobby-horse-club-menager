package bd2.gui.SignUpLogIn;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import okhttp3.*;

public class RiderFormGUI extends RegisterUserFormTemplate {

    public RiderFormGUI(int userId, String userType) {
        super(userId, userType);
        pageName = "Create Rider Account";
    }

    @Override
    protected String[] getFieldLabels() {
        String[] fieldLabels = {"Username", "Password", "Name", "Surname", "Date of birth", "Email", "Phone number", "Country", "City", "Street", "Street number", "Postal Code", "Parent consent", "Horse no", "Group", "License level"};
        return fieldLabels;
    }

    @Override
    protected String[] getFieldTypes() {
        String[] fieldTypes = {"text", "password", "text", "text", "comboBoxDate", "text", "text", "text", "text", "text", "text", "text", "text", "text", "text", "text"};
        return fieldTypes;
    }

    @Override
    protected Object[] getFieldParameters() {

        Integer[] days = new Integer[31];
        for (int i=0; i < days.length; i++) {
            days[i] = i+1;
        }
        Integer[] months = new Integer[12];
        for (int i=0; i < months.length; i++) {
            months[i] = i+1;
        }

        int baseYear = Year.now().getValue();
        Integer[] years = new Integer[110];
        for (int i=0; i < years.length; i++) {
            years[i] = baseYear-i;
        }

        Object[] fieldParameters = {15, 15, 15, 15, new Integer[][]{days, months, years}, 20, 10, 15, 15, 15, 6, 6, 40, 6, 6, 6};
        // new String[][]{new String[]{"Credit card", "Debet card", "Bank transfer", "BLIK", "Cash"}}
        return fieldParameters;
    }

    @Override
    protected void createUser(HashMap<String, String> textFieldsValues) {
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
			HttpClient client = HttpClient.newHttpClient();
			Gson gson = new Gson();
			
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


    public static void main(String[] args) {
        new RiderFormGUI(-1, "None").createGUI();
    }
}
