package bd2.gui.AddDataByForm;

import bd2.gui.SeeDataByScrolling.StablesScrollGUI;
import bd2.logic.*;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import kotlin.Pair;

import java.net.http.HttpResponse;
import java.util.HashMap;

import static bd2.DBRequests.base_url;
import static bd2.DBRequests.postMethod;

public class AddStableGUI extends AddDataTemplate {
	protected int addressId;

    public AddStableGUI(int userId, String userType) {
        super(userId, userType, "Add Stable");
    }

    public AddStableGUI(int userId, String userType, int editedElementId) {
        super(userId, userType, "Edit Stable");
        this.editedElementId = editedElementId;
        this.editMode = true;
    }

    @Override
    protected void createCustomGUI() {
        super.createCustomGUI();
        if (this.editMode) {
            populateFieldValues(getStableDataFromDB(this.editedElementId));
        }
    }

    HashMap<String, String> getStableDataFromDB(int elementId) {
		HashMap<String, String> stableMap = new HashMap<>();

        try {
            HttpResponse<String> response = getInfoById.getInfo(elementId, "stables/get_by_id/");

			System.out.println(response.body());

            JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();

            JsonArray jsonPositionsArray = jsonObject.getAsJsonArray("stables");
			JsonObject stable = jsonPositionsArray.get(0).getAsJsonObject();
            String name = stable.get("name").getAsString();
            JsonObject address = stable.getAsJsonObject("address");
            int address_id = address.get("id").getAsInt();
            String country = address.get("country").getAsString();
            String city = address.get("city").getAsString();
            String street = address.get("street").getAsString();
            String streetNo = address.get("street_no").getAsString();
            String postalCode = address.get("postal_code").getAsString();

			stableMap.put("Name", name);
			addressId = address_id;
			stableMap.put("Country", country);
			stableMap.put("City", city);
			stableMap.put("Street", street);
			stableMap.put("Street number", streetNo);
			stableMap.put("Postal Code", postalCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stableMap;
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
        String url = base_url + "stables/" + (this.editMode ? "update/" : "add/");

        JsonObject stable = new JsonObject();
        stable.addProperty("id", editedElementId);
        stable.addProperty("name", textFieldsValues.get("Name"));
        JsonObject address = new JsonObject();
        address.addProperty("id", addressId);
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
