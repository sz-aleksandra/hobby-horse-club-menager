package bd2.gui.AddDataByForm;

import bd2.gui.SeeDataByScrolling.HorsesScrollGUI;
import kotlin.Pair;

import java.util.HashMap;

public class AddHorseGUI extends AddDataTemplate {

    public AddHorseGUI(int userId, String userType) {
        super(userId, userType, "Add Horse");
    }

    public AddHorseGUI(int userId, String userType, int editedElementId) {
        super(userId, userType, "Edit Horse");
        this.editedElementId = editedElementId;
        this.editMode = true;
    }

    @Override
    protected void createCustomGUI() {
        super.createCustomGUI();
        if (this.editMode) {
            populateFieldValues(getHorseDataFromDB(this.editedElementId));
        }
    }

    // [MOCK]
    HashMap<String, String> getHorseDataFromDB(int elementId) {
        HashMap<String, String> myMap = new HashMap<String, String>() {{
            put("Bread", "Appaloosa");
            put("Age", "5");
        }};
        return myMap;
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
    protected Pair<Integer, String> addToDB(HashMap<String, String> textFieldsValues) {
        /*new AddNewUser(textFieldsValues.get("Username"), textFieldsValues.get("Password"), textFieldsValues.get("Name"), textFieldsValues.get("Surname"),
                textFieldsValues.get("Email"), textFieldsValues.get("Phone number"), textFieldsValues.get("Country"), textFieldsValues.get("City"),
                textFieldsValues.get("Street"), textFieldsValues.get("Postal Code"), textFieldsValues.get("Street number"), LocalDate.parse(textFieldsValues.get("Date of birth")),
                textFieldsValues.get("Nationality"), textFieldsValues.get("Gender"), true).insertIntoDatabase(); [MOCK]*/
        return null;
    }


    public static void main(String[] args) {
        new AddHorseGUI(-1, "None").createGUI();
    }
}
