package bd2.gui.SignUpLogIn;

import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RiderFormGUI extends RegisterUserFormTemplate {

    public RiderFormGUI(int userId, String userType) {
        super(userId, userType);
        pageName = "Create Rider Account";
    }

    @Override
    protected String[] getFieldLabels() {
        String[] fieldLabels = {"Username", "Password", "Name", "Surname", "Date of birth", "Email", "Phone number", "Country", "City", "Street", "Street number", "Postal Code", "Parent consent"};
        return fieldLabels;
    }

    @Override
    protected String[] getFieldTypes() {
        String[] fieldTypes = {"text", "password", "text", "text", "comboBoxDate", "text", "text", "text", "text", "text", "text", "text", "text"};
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

        Object[] fieldParameters = {15, 15, 15, 15, new Integer[][]{days, months, years}, 20, 10, 15, 15, 15, 6, 6, 40};
        // new String[][]{new String[]{"Credit card", "Debet card", "Bank transfer", "BLIK", "Cash"}}
        return fieldParameters;
    }

    @Override
    protected List<Integer> validateCredentials(HashMap<String, String> textFieldsValues) {
        /*List <Integer> errorCodes = new ClientValidator(textFieldsValues.get("Username"), textFieldsValues.get("Password"), textFieldsValues.get("Name"), textFieldsValues.get("Surname"),
                textFieldsValues.get("Email"), textFieldsValues.get("Phone number"), textFieldsValues.get("Country"), textFieldsValues.get("City"),
                textFieldsValues.get("Street"), textFieldsValues.get("Postal Code"), textFieldsValues.get("Street number"), LocalDate.parse(textFieldsValues.get("Date of birth")),
                textFieldsValues.get("Nationality"), textFieldsValues.get("Gender")).validateCredentials();*/
        List <Integer> errorCodes = new ArrayList<>(); // [MOCK]
        return errorCodes;
    }

    @Override
    protected void createUser(HashMap<String, String> textFieldsValues) {
        /*new AddNewUser(textFieldsValues.get("Username"), textFieldsValues.get("Password"), textFieldsValues.get("Name"), textFieldsValues.get("Surname"),
                textFieldsValues.get("Email"), textFieldsValues.get("Phone number"), textFieldsValues.get("Country"), textFieldsValues.get("City"),
                textFieldsValues.get("Street"), textFieldsValues.get("Postal Code"), textFieldsValues.get("Street number"), LocalDate.parse(textFieldsValues.get("Date of birth")),
                textFieldsValues.get("Nationality"), textFieldsValues.get("Gender"), true).insertIntoDatabase(); [MOCK]*/
    }


    public static void main(String[] args) {
        new RiderFormGUI(-1, "None").createGUI();
    }
}
