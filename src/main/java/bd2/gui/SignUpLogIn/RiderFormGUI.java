package bd2.gui.SignUpLogIn;

import java.io.IOException;
import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
		
		String json = "{ \"riders\": [ { \"member\": { \"name\": \"" + name + "\", \"surname\": \"" + surname + "\", \"username\": \"" + username + "\", \"date_of_birth\": \"" + dateOfBirth + "\", \"address\": { \"country\": \"" + country + "\", \"city\": \"" + city + "\", \"street\": \"" + street + "\", \"street_no\": \"" + streetNo + "\", \"postal_code\": \"" + postalCode + "\" }, \"phone_number\": \"" + phoneNumber + "\", \"email\": \"" + email + "\", \"is_active\": true, \"licence\": { \"id\": \"" + license + "\" } }, \"parent_consent\": " + parentConsent + ", \"group\": { \"id\": \"" + group + "\" }, \"horse\": { \"id\": \"" + horseNo + "\" } } ] }";

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(
                json, MediaType.get("application/json; charset=utf-8"));

        Request request = new Request.Builder()
                .url("http://localhost:8000/riders/add/")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println(response.body().string());
            }
        });
    }


    public static void main(String[] args) {
        new RiderFormGUI(-1, "None").createGUI();
    }
}
