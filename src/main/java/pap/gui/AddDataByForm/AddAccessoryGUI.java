package pap.gui.AddDataByForm;

import pap.gui.SeeDataByScrolling.AccessoriesScrollGUI;
import pap.gui.SeeDataByScrolling.HorsesScrollGUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddAccessoryGUI extends AddDataTemplate {

    public AddAccessoryGUI(int userId, String userType) {
        super(userId, userType, "Add Accessory");
    }

    @Override
    protected String[] getFieldLabels() {
        String[] fieldLabels = {"Name", "Info"};
        return fieldLabels;
    }

    @Override
    protected String[] getFieldTypes() {
        String[] fieldTypes = {"text", "text"};
        return fieldTypes;
    }

    @Override
    protected Object[] getFieldParameters() {
        Object[] fieldParameters = {20, 150};
        return fieldParameters;
    }

    @Override
    protected void undoBtnClickedAction() {
        new AccessoriesScrollGUI(userId, userType).createGUI();
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
        new AddAccessoryGUI(-1, "None").createGUI();
    }
}
