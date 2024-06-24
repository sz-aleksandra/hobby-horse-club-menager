package bd2.logic;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import kotlin.Pair;

import static bd2.DBRequests.base_url;
import static bd2.DBRequests.postMethod;

public class addRider {
    public static Pair<Integer, String> add(HashMap<String, String> textFieldsValues) {
        String url = base_url + "riders/add/";

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

		getMapsnIds mapsNIds = new getMapsnIds();
		HashMap<String, Integer> groups = mapsNIds.getGroupMap();
		HashMap<String, Integer> licences = mapsNIds.getLicenceMap();

		Integer groupId = groups.get(group);
		Integer licenseId = licences.get(license);

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
            licence.addProperty("id", licenseId);
            member.add("licence", licence);

            riders.add("member", member);
            riders.addProperty("parent_consent", parentConsent);

            JsonObject groupObject = new JsonObject();
            groupObject.addProperty("id", groupId);
            riders.add("group", groupObject);

            JsonObject horseObject = new JsonObject();
            horseObject.addProperty("id", Integer.parseInt(horseNo));
            riders.add("horse", horseObject);

            JsonArray ridersArray = new JsonArray();
            ridersArray.add(riders);

            JsonObject data = new JsonObject();
            data.add("riders", ridersArray);

            Pair<Integer, JsonObject> response = postMethod(url, data);

            if (response != null) {
                if (response.getFirst() == 200 || response.getFirst() == 201) {
                    return new Pair<>(response.getFirst(), "");
                }
                else {
                    String errorMsg = response.getSecond().get("error").getAsString();
                    return new Pair<>(response.getFirst(), errorMsg);
                }
            }
            else {
                return new Pair<>(-1, "Unknown error");
            }
        }
        catch (Exception e) {
            return new Pair<>(-1, "Invalid data");
        }
    }
}