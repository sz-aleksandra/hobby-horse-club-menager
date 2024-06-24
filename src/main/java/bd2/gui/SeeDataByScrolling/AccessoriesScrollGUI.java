package bd2.gui.SeeDataByScrolling;

import bd2.gui.AddDataByForm.AddAccessoryGUI;
import com.google.gson.JsonObject;
import kotlin.Pair;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

import static bd2.DBRequests.base_url;
import static bd2.DBRequests.postMethod;

public class AccessoriesScrollGUI extends DataScrollTemplate {

    @Override
    protected void getElementsData() {
        String url = base_url + "accessories/get_all/";

        Pair<Integer, JsonObject> response = postMethod(url, new HashMap<>());
        if (response != null) {
            JsonObject responseData = response.getSecond();
            this.nrOfElements = responseData.getAsJsonArray("accessories").size();
            this.fittingElementsIds = new Integer[this.nrOfElements];
            for (int i = 0; i < responseData.getAsJsonArray("accessories").size(); i++) {
                this.fittingElementsIds[i] = responseData.getAsJsonArray("accessories").get(i).getAsJsonObject().get("id").getAsInt();
            }
        }
        else {
            this.nrOfElements = 0;
            this.fittingElementsIds = new Integer[]{};
        }
    }

    // [MOCK]
    @Override
    protected HashMap<String, String> getElementData(int elementId) {
        HashMap<String, String> dataInfo = new HashMap<>();
        dataInfo.put("name", "Saddle");
        dataInfo.put("custom_info", "<html>Material: Leather, Size: Large, Durability: High, Fastenings: Stainless steel, Color: Dark brown, Brand: Prestige Saddles, Warranty: 5-year warranty</html>");
        return dataInfo;
    }

    @Override
    protected void addInfoToDataInfoPanel (int elementId, JPanel dataInfoPanel) {
        HashMap<String, String> dataInfo = getElementData(elementId);
        addJLabel(dataInfo.get("name"), Color.BLACK, fontBiggerBold, dataInfoPanel, elementWidth, elementHeight);
        addJLabel(dataInfo.get("custom_info"), Color.BLACK, fontMiddle, dataInfoPanel, elementWidth, elementHeight);
    }

    @Override
    protected void handleAddData() {
        new AddAccessoryGUI(userId, userType).createGUI();
        frame.setVisible(false);
    }

    @Override
    protected void handleEditData(int elementId) {
        new AddAccessoryGUI(userId, userType, elementId).createGUI();
        frame.setVisible(false);
    }

    @Override
    protected void handleRemoveData(int elementId) {

    }

    public AccessoriesScrollGUI(int userId, String userType){
        super(userId, userType, "Accessories");
    }

    public static void main(String[] args) {
        new AccessoriesScrollGUI(-1, "None").createGUI();
    }

}
