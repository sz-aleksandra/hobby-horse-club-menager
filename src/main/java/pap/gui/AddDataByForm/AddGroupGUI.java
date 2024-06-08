package pap.gui.AddDataByForm;

import pap.gui.SeeDataByScrolling.GroupsScrollGUI;
import pap.gui.SeeDataByScrolling.HorsesScrollGUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddGroupGUI extends AddDataTemplate {

    public AddGroupGUI(int userId, String userType) {
        super(userId, userType, "Add Group");
    }

    @Override
    protected String[] getFieldLabels() {
        String[] fieldLabels = {"Group trainings"};
        return fieldLabels;
    }

    @Override
    protected String[] getFieldTypes() {
        String[] fieldTypes = {"checkBoxString"};
        return fieldTypes;
    }

    @Override
    protected Object[] getFieldParameters() {
        String[] trainingsIds = {"Training id 1", "Training id 2", "Training id 3", "Training id 4"};
        Object[] fieldParameters = {trainingsIds};
        return fieldParameters;
    }

    @Override
    protected void undoBtnClickedAction() {
        new GroupsScrollGUI(userId, userType).createGUI();
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
        new AddGroupGUI(-1, "None").createGUI();
    }
}
