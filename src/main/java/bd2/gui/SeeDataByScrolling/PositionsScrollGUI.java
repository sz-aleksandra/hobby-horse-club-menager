package bd2.gui.SeeDataByScrolling;

import bd2.gui.AddDataByForm.AddPositionGUI;
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

public class PositionsScrollGUI extends DataScrollTemplate {

    @Override
    protected void getElementsData() {
        String url = base_url + "positions/get_all/";

        Pair<Integer, JsonObject> response = postMethod(url, new HashMap<>());
        if (response != null) {
            JsonObject responseData = response.getSecond();
            this.nrOfElements = responseData.getAsJsonArray("positions").size();
            this.fittingElementsIds = new Integer[this.nrOfElements];
            for (int i = 0; i < responseData.getAsJsonArray("positions").size(); i++) {
                this.fittingElementsIds[i] = responseData.getAsJsonArray("positions").get(i).getAsJsonObject().get("id").getAsInt();
            }
        }
        else {
            this.nrOfElements = 0;
            this.fittingElementsIds = new Integer[]{};
        }
    }

    @Override
    protected HashMap<String, String> getElementData(int elementId) {
        String url = base_url + "positions/get_by_id/";

        Map<String, Object> data = new HashMap<>();
        data.put("ids", List.of(elementId));

        Pair<Integer, JsonObject> response = postMethod(url, data);
        if (response != null) {
            JsonObject responseData = response.getSecond();
            HashMap<String, String> dataInfo = new HashMap<>();
            dataInfo.put("name", responseData.getAsJsonArray("positions").get(0).getAsJsonObject().get("name").getAsString());
            dataInfo.put("salary_min", responseData.getAsJsonArray("positions").get(0).getAsJsonObject().get("salary_min").getAsString());
            dataInfo.put("salary_max", responseData.getAsJsonArray("positions").get(0).getAsJsonObject().get("salary_max").getAsString());
            dataInfo.put("speciality", responseData.getAsJsonArray("positions").get(0).getAsJsonObject().get("speciality").getAsString());
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
        addJLabel("Salary: " + dataInfo.get("salary_min") + " - " + dataInfo.get("salary_max") + " PLN", Color.BLACK, fontMiddle, dataInfoPanel, elementWidth, elementHeight);
        addJLabel("Speciality: " + dataInfo.get("speciality"), Color.BLACK, fontMiddle, dataInfoPanel, elementWidth, elementHeight);
    }

    @Override
    protected void handleAddData() {
        new AddPositionGUI(userId, userType).createGUI();
        frame.setVisible(false);
    }

    @Override
    protected void handleEditData(int elementId) {
        // not used
    }

    @Override
    protected void handleRemoveData(int elementId) {
        // not used
    }

    public PositionsScrollGUI(int userId, String userType){
        super(userId, userType, "Positions");
    }

    public static void main(String[] args) {
        new PositionsScrollGUI(-1, "None").createGUI();
    }

}
