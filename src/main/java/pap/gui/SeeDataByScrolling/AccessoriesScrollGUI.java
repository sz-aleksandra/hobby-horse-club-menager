package pap.gui.SeeDataByScrolling;

import pap.gui.AddDataByForm.AddAccessoryGUI;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class AccessoriesScrollGUI extends DataScrollTemplate {

    // [MOCK]
    @Override
    protected void getElementsData() {
        this.fittingElementsIds = new Integer[]{1,2,3,4,5,6,7,8,9};
        this.nrOfElements = fittingElementsIds.length;
    }

    // [MOCK]
    @Override
    protected HashMap<String, String> getElementData(int elementId) {
        HashMap<String, String> dataInfo = new HashMap<String, String>();
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
    protected void handleEditData() {

    }

    @Override
    protected void handleRemoveData() {

    }

    public AccessoriesScrollGUI(int userId, String userType){
        super(userId, userType, "Accessories");
    }

    public static void main(String[] args) {
        new AccessoriesScrollGUI(-1, "None").createGUI();
    }

}
