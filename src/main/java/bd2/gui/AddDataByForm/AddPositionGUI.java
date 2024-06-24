package bd2.gui.AddDataByForm;

import bd2.gui.SeeDataByScrolling.PositionsScrollGUI;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import kotlin.Pair;

import java.util.HashMap;

import static bd2.DBRequests.base_url;
import static bd2.DBRequests.postMethod;

public class AddPositionGUI extends AddDataTemplate {

    public AddPositionGUI(int userId, String userType) {
        super(userId, userType, "Add Position");
    }

    public AddPositionGUI(int userId, String userType, int editedElementId) {
        super(userId, userType, "Edit Position");
        this.editedElementId = editedElementId;
        this.editMode = true;
    }

    @Override
    protected void createCustomGUI() {
        super.createCustomGUI();
        if (this.editMode) {
            populateFieldValues(getPositionDataFromDB(this.editedElementId));
        }
    }

    // [MOCK]
    HashMap<String, String> getPositionDataFromDB(int elementId) {
        HashMap<String, String> myMap = new HashMap<String, String>() {{
            put("Name", "Cleaner");
            put("Minimal salary", "4200");
        }};
        return myMap;
    }

    @Override
    protected String[] getFieldLabels() {
        String[] fieldLabels = {"Name", "Minimal salary", "Maximal salary", "Speciality"};
        return fieldLabels;
    }

    @Override
    protected String[] getFieldTypes() {
        String[] fieldTypes = {"text", "text", "text", "text"};
        return fieldTypes;
    }

    @Override
    protected Object[] getFieldParameters() {
        Object[] fieldParameters = {20, 10, 10, 30};
        return fieldParameters;
    }

    @Override
    protected void undoBtnClickedAction() {
        new PositionsScrollGUI(userId, userType).createGUI();
        frame.setVisible(false);
    }

    @Override
    protected Pair<Integer, String> addToDB(HashMap<String, String> textFieldsValues) {
//        String url = base_url + "positions/add/";
//
//        JsonObject position = new JsonObject();
//        position.addProperty("name", textFieldsValues.get("Name"));
//        position.addProperty("salary_min", textFieldsValues.get("Minimal salary"));
//        position.addProperty("salary_max", textFieldsValues.get("Maximal salary"));
//        position.addProperty("speciality", textFieldsValues.get("Speciality"));
//
//        JsonArray positionsArray = new JsonArray();
//        positionsArray.add(position);
//
//        JsonObject data = new JsonObject();
//        data.add("positions", positionsArray);
//
//        Pair<Integer, JsonObject> response = postMethod(url, data);
//        if (response != null) {
//            if (response.getFirst() == 200 || response.getFirst() == 201) {
//                return new Pair<>(response.getFirst(), "");
//            }
//            else {
//                String errorMsg = response.getSecond().get("error").getAsString();
//                return new Pair<>(response.getFirst(), errorMsg);
//            }
//        }
//        else {
//            return new Pair<>(-1, "Unknown error");
//        }
        return new Pair<>(-1, "Not implemented");
    }


    public static void main(String[] args) {
        new AddPositionGUI(-1, "None").createGUI();
    }
}
