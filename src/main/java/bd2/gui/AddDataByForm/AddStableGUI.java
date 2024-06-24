package bd2.gui.AddDataByForm;

import bd2.gui.SeeDataByScrolling.StablesScrollGUI;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import kotlin.Pair;

import java.util.HashMap;
import static bd2.DBRequests.base_url;
import static bd2.DBRequests.postMethod;

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
    protected Pair<Integer, String> addToDB(HashMap<String, String> textFieldsValues) {
        String url = base_url + "stables/add/";

        JsonObject stable = new JsonObject();
        stable.addProperty("name", textFieldsValues.get("Name"));
        JsonObject address = new JsonObject();
        address.addProperty("country", textFieldsValues.get("Country"));
        address.addProperty("city", textFieldsValues.get("City"));
        address.addProperty("street", textFieldsValues.get("Street"));
        address.addProperty("street_no", textFieldsValues.get("Street number"));
        address.addProperty("postal_code", textFieldsValues.get("Postal Code"));
        stable.add("address", address);

        JsonArray stablesArray = new JsonArray();
        stablesArray.add(stable);

        JsonObject data = new JsonObject();
        data.add("stables", stablesArray);


        Pair<Integer, JsonObject> response = postMethod(url, data);
        if (response != null) {
            if (response.getFirst() == 200 || response.getFirst() == 201) {
                return new Pair<>(response.getFirst(), "");
            }
            else {
                String errorMsg = response.getSecond().get("error").getAsString();
                return new Pair<>(response.getFirst(), errorMsg);
            }
        }
        else {
            return new Pair<>(-1, "Unknown error");
        }
    }


    public static void main(String[] args) {
        new AddStableGUI(-1, "None").createGUI();
    }
}
