package bd2.gui.SeeDataByScrolling;

import bd2.gui.AddDataByForm.AddRiderGUI;
import bd2.logic.ErrorCodes;
import com.google.gson.JsonObject;
import kotlin.Pair;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

import static bd2.DBRequests.base_url;
import static bd2.DBRequests.postMethod;

public class RidersScrollGUI extends DataScrollTemplate {

    @Override
    protected void getElementsData() {
        String url = base_url + "riders/get_active_riders/";

        Pair<Integer, JsonObject> response = postMethod(url, new HashMap<>());
        if (response != null) {
            JsonObject responseData = response.getSecond();
            this.nrOfElements = responseData.getAsJsonArray("riders").size();
            this.fittingElementsIds = new Integer[this.nrOfElements];
            for (int i = 0; i < responseData.getAsJsonArray("riders").size(); i++) {
                this.fittingElementsIds[i] = responseData.getAsJsonArray("riders").get(i).getAsJsonObject().get("id").getAsInt();
            }
        }
        else {
            this.nrOfElements = 0;
            this.fittingElementsIds = new Integer[]{};
        }
    }

    @Override
    protected HashMap<String, String> getElementData(int elementId) {
        String url = base_url + "riders/get_by_id/";

        Map<String, Object> data = new HashMap<>();
        data.put("ids", List.of(elementId));

        Pair<Integer, JsonObject> response = postMethod(url, data);
        if (response != null) {
            JsonObject responseData = response.getSecond().getAsJsonArray("riders").get(0).getAsJsonObject();
            HashMap<String, String> dataInfo = new HashMap<>();
            JsonObject member = responseData.get("member").getAsJsonObject();
            dataInfo.put("name", member.get("name").getAsString());
            dataInfo.put("surname", member.get("surname").getAsString());
            dataInfo.put("username", member.get("username").getAsString());
            dataInfo.put("date_of_birth", member.get("date_of_birth").getAsString());
            dataInfo.put("email", member.get("email").getAsString());
            dataInfo.put("phone_number", member.get("phone_number").getAsString());
            JsonObject address = member.get("address").getAsJsonObject();
            dataInfo.put("address", "ul. " + address.get("street").getAsString() + " " + address.get("street_no").getAsString() + ", " + address.get("city").getAsString() + " " + address.get("postal_code").getAsString() + ", " + address.get("country").getAsString());
            dataInfo.put("has_parent_consent", responseData.get("parent_consent_id").getAsBoolean() ? "yes" : "no");
            dataInfo.put("licence_nr", member.get("licence").getAsJsonObject().get("licence_level").getAsString());
            return dataInfo;

        }
        else {
            return null;
        }
    }

    @Override
    protected void addInfoToDataInfoPanel (int elementId, JPanel dataInfoPanel) {
        HashMap<String, String> dataInfo = getElementData(elementId);
        addJLabel(dataInfo.get("name") + " " + dataInfo.get("surname"), Color.BLACK, fontBiggerBold, dataInfoPanel, elementWidth, elementHeight);
        addJLabel("System username: " + dataInfo.get("username") + ", Date of birth: " + dataInfo.get("date_of_birth"), Color.BLACK, fontSmaller, dataInfoPanel, elementWidth, elementHeight);
        addJLabel("Contact: " + dataInfo.get("email") + ", " + dataInfo.get("phone_number"), Color.BLACK, fontSmaller, dataInfoPanel, elementWidth, elementHeight);
        addJLabel(dataInfo.get("address"), Color.BLACK, fontSmaller, dataInfoPanel, elementWidth, elementHeight);
        addJLabel("Parent consent: " + dataInfo.get("has_parent_consent") + ", Licence: " + dataInfo.get("licence_nr"), Color.BLACK, fontSmaller, dataInfoPanel, elementWidth, elementHeight);
    }

    @Override
    protected void handleAddData() {
        new AddRiderGUI(userId, userType).createGUI();
        frame.setVisible(false);
    }

    @Override
    protected void handleEditData(int elementId) {
        new AddRiderGUI(userId, userType, elementId).createGUI();
        frame.setVisible(false);
    }

    @Override
    protected void handleRemoveData(int elementId) {
        String[] options = {"No", "Yes"};
        int pickedOption = JOptionPane.showOptionDialog(null, "This action is irreversible. Are you sure you want to continue?",
                "Confirm action", 0, 0, null, options, "No");

        if (pickedOption == 1) {
            List<Integer> errorCodes = new ArrayList<>();
            String url = base_url + "riders/deactivate_account/";

            Map<String, Object> data = new HashMap<>();
            data.put("id", elementId);

            Pair<Integer, JsonObject> response = postMethod(url, data);

            if (response != null) {
                JsonObject responseData = response.getSecond();
                int response_code = response.getFirst();
                if (response_code != 200) {
                    errorCodes.add(response_code);
                }
            }
            else {
                errorCodes.add(-1);
            }

            if (errorCodes.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Rider deleted successfully.");
                new RidersScrollGUI(userId, userType).createGUI();
                frame.setVisible(false);
            } else {
                StringBuilder errorText = new StringBuilder();
                for (Integer errorCode : errorCodes) {
                    errorText.append(ErrorCodes.getErrorDescription(errorCode)).append(" ");
                }

                JOptionPane.showMessageDialog(frame, errorText.toString());
            }
        }
    }

    public RidersScrollGUI(int userId, String userType){
        super(userId, userType, "Riders");
        elementHeight = frameHeight*5/24;
        elementWidth = frameWidth*6/10;
    }

    public static void main(String[] args) {
        new RidersScrollGUI(-1, "None").createGUI();
    }

}
