package pap.gui.SeeDataByScrolling;

import pap.gui.AddDataByForm.AddHorseGUI;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class HorsesScrollGUI extends DataScrollTemplate {

    //[MOCK]
    @Override
    protected void getElementsData() {
        this.fittingElementsIds = new Integer[]{1,2,3,4,5,6,7,8,9,10,12,13,14,15,16,17,18,19,20,21,22,23,24,25};
        this.nrOfElements = fittingElementsIds.length;
    }

    // [MOCK]
    @Override
    protected HashMap<String, String> getElementData(int elementId) {
        HashMap<String, String> dataInfo = new HashMap<String, String>();
        dataInfo.put("bread", "Appaloosa");
        dataInfo.put("height", "100");
        dataInfo.put("color", "Chestnut");
        dataInfo.put("eye_color", "Blue");
        dataInfo.put("age", "4");
        dataInfo.put("origin", "Peru");
        dataInfo.put("hairstyle", "Braided");
        return dataInfo;
    }

    @Override
    protected void addInfoToDataInfoPanel (int elementId, JPanel dataInfoPanel) {
        HashMap<String, String> dataInfo = getElementData(elementId);
        addJLabel("Horse - " + dataInfo.get("bread") + " from " + dataInfo.get("origin"), Color.BLACK, fontBiggerBold, dataInfoPanel, elementWidth, elementHeight);
        addJLabel("Age: " + dataInfo.get("age") + " years, Height: " + dataInfo.get("height") + " cm", Color.BLACK, fontMiddle, dataInfoPanel, elementWidth, elementHeight);
        addJLabel("Color: " + dataInfo.get("color") + ", Eye color: " + dataInfo.get("eye_color") + ", Hairstyle: " + dataInfo.get("hairstyle"), Color.BLACK, fontMiddle, dataInfoPanel, elementWidth, elementHeight);
    }

    @Override
    protected void handleAddData() {
        new AddHorseGUI(userId, userType).createGUI();
        frame.setVisible(false);
    }

    @Override
    protected void handleEditData() {

    }

    @Override
    protected void handleRemoveData() {

    }

    public HorsesScrollGUI(int userId, String userType){
        super(userId, userType, "Horses");
    }

    public static void main(String[] args) {
        new HorsesScrollGUI(-1, "None").createGUI();
    }

}
