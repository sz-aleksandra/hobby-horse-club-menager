package pap.gui.AddDataByForm;

import pap.gui.SeeDataByScrolling.HorsesScrollGUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddRiderGUI extends AddDataTemplate {

    public AddRiderGUI(int userId, String userType) {
        super(userId, userType, "Add Horse");
    }

    @Override
    protected String[] getFieldLabels() {
        String[] fieldLabels = {"Bread", "Origin", "Height", "Age", "Color", "Eye color", "Hairstyle"};
        return fieldLabels;
    }

    @Override
    protected String[] getFieldTypes() {
        String[] fieldTypes = {"comboBoxString", "comboBoxString", "text", "text", "comboBoxString", "comboBoxString", "comboBoxString"};
        return fieldTypes;
    }

    @Override
    protected Object[] getFieldParameters() {
        String[][] breads = new String[][]{{"Arab", "Thoroughbred", "Appaloosa", "Clydesdale", "Morgan", "Andalusian", "Friesian", "Shire", "Paint Horse", "Tennessee Walking Horse"}};
        String[][] origins = new String[][]{{"Arabian Peninsula", "England", "United States", "Scotland", "Spain", "Netherlands", "France", "Portugal", "Russia", "Australia"}};
        String[][] colors = new String[][]{{"Bay", "Black", "Chestnut", "Grey", "Palomino", "Buckskin", "Dun", "Roan", "Pinto"}};
        String[][] eye_colors = new String[][]{{"Brown", "Hazel", "Blue", "Amber", "Green", "Dark Brown", "Light Brown", "Gray", "Blue-Green", "Gold"}};
        String[][] hairstyles = new String[][]{{"Braided", "Running Braid", "Button Braids", "Hunter Braids", "Roached Mane", "Natural Flowing", "Docked Tail", "Braided Tail", "Tail Sets", "Fishtail Braid"}};

        Object[] fieldParameters = {breads, origins, 10, 10, colors, eye_colors, hairstyles};
        return fieldParameters;
    }

    @Override
    protected void undoBtnClickedAction() {
        new HorsesScrollGUI(userId, userType).createGUI();
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
        new AddRiderGUI(-1, "None").createGUI();
    }
}
