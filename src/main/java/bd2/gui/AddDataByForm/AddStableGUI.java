package bd2.gui.AddDataByForm;

import bd2.gui.SeeDataByScrolling.HorsesScrollGUI;
import bd2.gui.SeeDataByScrolling.StablesScrollGUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddStableGUI extends AddDataTemplate {

    public AddStableGUI(int userId, String userType) {
        super(userId, userType, "Add Stable");
    }

    @Override
    protected String[] getFieldLabels() {
        String[] fieldLabels = {"Name", "Country", "City", "Street", "Street number", "Postal Code"};
        return fieldLabels;
    }

    @Override
    protected String[] getFieldTypes() {
        String[] fieldTypes = {"text", "text", "text", "text", "text", "text"};
        return fieldTypes;
    }

    @Override
    protected Object[] getFieldParameters() {
        Object[] fieldParameters = {30, 15, 15, 15, 6, 6};
        return fieldParameters;
    }

    @Override
    protected void undoBtnClickedAction() {
        new StablesScrollGUI(userId, userType).createGUI();
        frame.setVisible(false);
    }

    @Override
    protected List<Integer> validateInput(HashMap<String, String> textFieldsValues) {
        /*List <Integer> errorCodes = new ClientValidator(textFieldsValues.get("Username"), textFieldsValues.get("Password"), textFieldsValues.get("Name"), textFieldsValues.get("Surname"),
                textFieldsValues.get("Email"), textFieldsValues.get("Phone number"), textFieldsValues.get("Country"), textFieldsValues.get("City"),
                textFieldsValues.get("Street"), textFieldsValues.get("Postal Code"), textFieldsValues.get("Street number"), LocalDate.parse(textFieldsValues.get("Date of birth")),
                textFieldsValues.get("Nationality"), textFieldsValues.get("Gender")).validateCredentials();*/
        List <Integer> errorCodes = new ArrayList<>(); // [MOCK]
        return errorCodes;
    }

    @Override
    protected void addToDB(HashMap<String, String> textFieldsValues) {
        /*new AddNewUser(textFieldsValues.get("Username"), textFieldsValues.get("Password"), textFieldsValues.get("Name"), textFieldsValues.get("Surname"),
                textFieldsValues.get("Email"), textFieldsValues.get("Phone number"), textFieldsValues.get("Country"), textFieldsValues.get("City"),
                textFieldsValues.get("Street"), textFieldsValues.get("Postal Code"), textFieldsValues.get("Street number"), LocalDate.parse(textFieldsValues.get("Date of birth")),
                textFieldsValues.get("Nationality"), textFieldsValues.get("Gender"), true).insertIntoDatabase(); [MOCK]*/
    }


    public static void main(String[] args) {
        new AddStableGUI(-1, "None").createGUI();
    }
}
