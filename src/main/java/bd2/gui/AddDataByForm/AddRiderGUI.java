package bd2.gui.AddDataByForm;

import bd2.gui.SeeDataByScrolling.RidersScrollGUI;
import bd2.gui.SignUpLogIn.RiderFormGUI;
import bd2.logic.getInfoById;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.http.HttpResponse;
import java.util.HashMap;

public class AddRiderGUI extends RiderFormGUI {

    @Override
    protected void undoBtnClickedAction(){
        new RidersScrollGUI(userId, userType).createGUI();
        frame.setVisible(false);
    }

    public AddRiderGUI(int userId, String userType) {
        super(userId, userType);
    }

    public AddRiderGUI(int userId, String userType, int editedElementId) {
        super(userId, userType);
        this.editedElementId = editedElementId;
        this.editMode = true;
    }

    @Override
    protected void createCustomGUI() {
        super.createCustomGUI();
        if (this.editMode) {
            populateFieldValues(getRiderDataFromDB(this.editedElementId));
        }
    }

    // @TODO: fix default values
    HashMap<String, String> getRiderDataFromDB(int elementId) {
        HashMap<String, String> riderMap = new HashMap<>();

        try {
            HttpResponse<String> response = getInfoById.getInfo(elementId, "riders/get_by_id/");
            JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();

            JsonArray jsonRidersArray = jsonObject.getAsJsonArray("riders");
            JsonObject rider = jsonRidersArray.get(0).getAsJsonObject();

            JsonObject member = rider.get("member").getAsJsonObject();
            riderMap.put("Username", member.get("username").getAsString());
            riderMap.put("Name", member.get("name").getAsString());
            riderMap.put("Surname", member.get("surname").getAsString());
            riderMap.put("Date of birth", member.get("date_of_birth").getAsString());
            riderMap.put("Email", member.get("email").getAsString());
            riderMap.put("Phone number", member.get("phone_number").getAsString());
            JsonObject address = member.get("address").getAsJsonObject();
            riderMap.put("Country", address.get("country").getAsString());
            riderMap.put("City", address.get("city").getAsString());
            riderMap.put("Street", address.get("street").getAsString());
            riderMap.put("Street number", address.get("street_no").getAsString());
            riderMap.put("Postal Code", address.get("postal_code").getAsString());
            riderMap.put("Parent consent", rider.get("parent_consent").getAsBoolean() ? "yes" : "no");
            riderMap.put("Horse no", rider.get("horse").getAsJsonObject().get("id").getAsString());
            riderMap.put("Group", rider.get("group").getAsJsonObject().get("id").getAsString());
            riderMap.put("License level", member.get("licence").getAsJsonObject().get("licence_level").getAsString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return riderMap;
    }

    public static void main(String[] args) {
        new AddRiderGUI(-1, "None").createGUI();
    }
}
