package pap.gui.AddDataByForm;

import pap.gui.SeeDataByScrolling.HorsesScrollGUI;
import pap.gui.SeeDataByScrolling.TrainingsScrollGUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddTrainingGUI extends AddDataTemplate {

    public AddTrainingGUI(int userId, String userType) {
        super(userId, userType, "Add Training");
    }

    @Override
    protected String[] getFieldLabels() {
        String[] fieldLabels = {"Type", "Day of the week", "Hour (+AM/PM)", "Trainer", "Group", "Stable"};
        return fieldLabels;
    }

    @Override
    protected String[] getFieldTypes() {
        String[] fieldTypes = {"text", "text", "text", "comboBoxString", "comboBoxString", "comboBoxString"};
        return fieldTypes;
    }

    @Override
    protected Object[] getFieldParameters() {
        //[MOCK] Powinna być pobierana z bazy lista trenerów, lista stajni, lista grup
        String[][] trainers = new String[][]{{"Adam Małysz", "Bartek Górski", "Ania Kabacka", "Kasia Boryn"}};
        String[][] groups = new String[][]{{"Group 1", "Group 2", "Group 3", "Group 4", "Group 5", "Group 6", "Group 7", }};
        String[][] stables = new String[][]{{"Stable 1", "Stable 2", "Stable 3", "Stable 4"}};

        Object[] fieldParameters = {20, 15, 10, trainers, groups, stables};
        return fieldParameters;
    }

    @Override
    protected void undoBtnClickedAction() {
        new TrainingsScrollGUI(userId, userType).createGUI();
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
        new AddTrainingGUI(-1, "None").createGUI();
    }
}
