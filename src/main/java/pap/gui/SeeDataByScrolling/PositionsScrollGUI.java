package pap.gui.SeeDataByScrolling;

import pap.gui.AddDataByForm.AddPositionGUI;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class PositionsScrollGUI extends DataScrollTemplate {

    // [MOCK]
    @Override
    protected void getElementsData() {
        this.fittingElementsIds = new Integer[]{1,2,3,4};
        this.nrOfElements = fittingElementsIds.length;
    }

    // [MOCK]
    @Override
    protected HashMap<String, String> getElementData(int elementId) {
        HashMap<String, String> dataInfo = new HashMap<String, String>();
        dataInfo.put("name", "Trainer");
        dataInfo.put("salary_min", "2400");
        dataInfo.put("salary_max", "6000");
        dataInfo.put("speciality", "Jumping Trainings");
        return dataInfo;
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
    protected void handleEditData() {

    }

    @Override
    protected void handleRemoveData() {

    }

    public PositionsScrollGUI(int userId, String userType){
        super(userId, userType, "Positions");
    }

    public static void main(String[] args) {
        new PositionsScrollGUI(-1, "None").createGUI();
    }

}
