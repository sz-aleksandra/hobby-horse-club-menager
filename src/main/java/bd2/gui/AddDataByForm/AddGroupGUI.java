package bd2.gui.AddDataByForm;

import bd2.gui.SeeDataByScrolling.GroupsScrollGUI;
import kotlin.Pair;

import java.util.HashMap;

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
    protected Pair<Integer, String> addToDB(HashMap<String, String> textFieldsValues) {
        /*new AddNewUser(textFieldsValues.get("Username"), textFieldsValues.get("Password"), textFieldsValues.get("Name"), textFieldsValues.get("Surname"),
                textFieldsValues.get("Email"), textFieldsValues.get("Phone number"), textFieldsValues.get("Country"), textFieldsValues.get("City"),
                textFieldsValues.get("Street"), textFieldsValues.get("Postal Code"), textFieldsValues.get("Street number"), LocalDate.parse(textFieldsValues.get("Date of birth")),
                textFieldsValues.get("Nationality"), textFieldsValues.get("Gender"), true).insertIntoDatabase(); [MOCK]*/
        return null;
    }

    public static void main(String[] args) {
        new AddGroupGUI(-1, "None").createGUI();
    }
}
