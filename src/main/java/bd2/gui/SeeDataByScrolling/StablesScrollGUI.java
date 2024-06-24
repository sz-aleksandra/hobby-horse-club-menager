package bd2.gui.SeeDataByScrolling;

import bd2.gui.AddDataByForm.AddStableGUI;
import bd2.logic.ErrorCodes;
import com.google.gson.JsonObject;
import kotlin.Pair;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static bd2.DBRequests.base_url;
import static bd2.DBRequests.postMethod;

public class StablesScrollGUI extends DataScrollTemplate {

    @Override
    protected void getElementsData() {
        String url = base_url + "stables/get_all/";

        Pair<Integer, JsonObject> response = postMethod(url, new HashMap<>());
        if (response != null) {
            JsonObject responseData = response.getSecond();
            this.nrOfElements = responseData.getAsJsonArray("stables").size();
            this.fittingElementsIds = new Integer[this.nrOfElements];
            for (int i = 0; i < responseData.getAsJsonArray("stables").size(); i++) {
                this.fittingElementsIds[i] = responseData.getAsJsonArray("stables").get(i).getAsJsonObject().get("id").getAsInt();
            }
        }
        else {
            this.nrOfElements = 0;
            this.fittingElementsIds = new Integer[]{};
        }
    }

    @Override
    protected HashMap<String, String> getElementData(int elementId) {
        String url = base_url + "stables/get_by_id/";

        Map<String, Object> data = new HashMap<>();
        data.put("ids", List.of(elementId));

        Pair<Integer, JsonObject> response = postMethod(url, data);
        if (response != null) {
            JsonObject responseData = response.getSecond();
            HashMap<String, String> dataInfo = new HashMap<>();
            dataInfo.put("name", responseData.getAsJsonArray("stables").get(0).getAsJsonObject().get("name").getAsString());
            JsonObject address = responseData.getAsJsonArray("stables").get(0).getAsJsonObject().get("address").getAsJsonObject();
            dataInfo.put("address", "ul. " + address.get("street").getAsString() + " " + address.get("street_no").getAsString() + ", " + address.get("city").getAsString() + " " + address.get("postal_code").getAsString() + ", " + address.get("country").getAsString());
            return dataInfo;
        }
        else {
            return null;
        }
    }

    @Override
    protected void addInfoToDataInfoPanel (int elementId, JPanel dataInfoPanel) {
        HashMap<String, String> dataInfo = getElementData(elementId);
        addJLabel(dataInfo.get("name"), Color.BLACK, fontBiggerBold, dataInfoPanel, elementWidth, elementHeight);
        addJLabel(dataInfo.get("address"), Color.BLACK, fontMiddle, dataInfoPanel, elementWidth, elementHeight);
    }

    @Override
    protected void handleAddData() {
        new AddStableGUI(userId, userType).createGUI();
        frame.setVisible(false);
    }

    // @TODO: implement edit data
    @Override
    protected void handleEditData(int elementId) {
        System.out.println("Edit stable");
    }

    @Override
    protected void handleRemoveData(int elementId) {
        String[] options = {"No", "Yes"};
        int pickedOption = JOptionPane.showOptionDialog(null, "This action is irreversible. Are you sure you want to continue?",
                "Confirm action", 0, 0, null, options, "No");

        if (pickedOption == 1) {
            List<Integer> errorCodes = new ArrayList<>();
            String url = base_url + "stables/delete/";

            Map<String, Object> data = new HashMap<>();
            data.put("ids", List.of(elementId));

            Pair<Integer, JsonObject> response = postMethod(url, data);

            if (response != null) {
                JsonObject responseData = response.getSecond();
                int response_code = response.getFirst();
                if (response_code != 200) {
                    errorCodes.add(responseData.get("error_code").getAsInt());
                }
            }
            else {
                errorCodes.add(-1);
            }

            if (errorCodes.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Stable deleted successfully.");
                new StablesScrollGUI(userId, userType).createGUI();
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

    public StablesScrollGUI(int userId, String userType) {
        super(userId, userType, "Stables");
    }

    public static void main(String[] args) {
        new StablesScrollGUI(-1, "None").createGUI();
    }

}
