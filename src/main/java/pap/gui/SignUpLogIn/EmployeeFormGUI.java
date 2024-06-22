package pap.gui.SignUpLogIn;

import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EmployeeFormGUI extends RegisterUserFormTemplate {

    public EmployeeFormGUI(int userId, String userType) {
        super(userId, userType);
        pageName = "Create Employee Account";
    }

    @Override
    protected String[] getFieldLabels() {
        String[] fieldLabels = {"Username", "Password", "Name", "Surname", "Date of birth", "Email", "Phone number", "Country", "City", "Street", "Street number", "Postal Code", "Position", "Salary (PLN)", "Date employed"};
        return fieldLabels;
    }

    @Override
    protected String[] getFieldTypes() {
        String[] fieldTypes = {"text", "password", "text", "text", "comboBoxDate", "text", "text", "text", "text", "text", "text", "text", "comboBox", "text", "comboBoxDate"};
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

        Object[] fieldParameters = {15, 15, 15, 15, new Integer[][]{days, months, years}, 20, 10, 15, 15, 15, 6, 6, new String[][]{{"Cleaner", "Trainer", "Accountant", "Owner"}}, 10, new Integer[][]{days, months, years}};

        return fieldParameters;
    }

    @Override
    protected List<Integer> validateCredentials(HashMap<String, String> textFieldsValues) {
        /*List <Integer> errorCodes = new OwnerValidator(textFieldsValues.get("Username"), textFieldsValues.get("Password"), textFieldsValues.get("Company name"),
                textFieldsValues.get("Email"), textFieldsValues.get("Phone number"), textFieldsValues.get("Country"), textFieldsValues.get("City"),
                textFieldsValues.get("Street"), textFieldsValues.get("Postal Code"), textFieldsValues.get("Street number"), textFieldsValues.get("NIP")).validateOwnerCredentials();*/
        List <Integer> errorCodes = new ArrayList<>(); // [MOCK]
        return errorCodes;
    }

    @Override
    protected void createUser(HashMap<String, String> textFieldsValues) {
        /*new AddNewOwner(textFieldsValues.get("Username"), textFieldsValues.get("Password"), textFieldsValues.get("Company name"),
                textFieldsValues.get("Email"), textFieldsValues.get("Phone number"), textFieldsValues.get("Country"), textFieldsValues.get("City"),
                textFieldsValues.get("Street"), textFieldsValues.get("Postal Code"), textFieldsValues.get("Street number"), textFieldsValues.get("NIP"),
                false, true).insertIntoDatabase(); [MOCK]*/
    }

    public static void main(String[] args) {
        new EmployeeFormGUI(-1, "None").createGUI();
    }
}
