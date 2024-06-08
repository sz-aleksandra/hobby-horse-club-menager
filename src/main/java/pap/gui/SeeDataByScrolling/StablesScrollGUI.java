package pap.gui.SeeDataByScrolling;

import pap.gui.AddDataByForm.AddStableGUI;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class StablesScrollGUI extends DataScrollTemplate {

    //[MOCK]
    @Override
    protected void getElementsData() {
        this.fittingElementsIds = new Integer[]{1,2,3,4};
        this.nrOfElements = fittingElementsIds.length;
    }

    // [MOCK]
    @Override
    protected HashMap<String, String> getElementData(int elementId) {
        HashMap<String, String> dataInfo = new HashMap<String, String>();
        dataInfo.put("name", "Horse Palace");
        dataInfo.put("address", "ul. Słoneczna 5, Otwock 05-400, Polska");
        return dataInfo;
    }

    @Override
    protected void addInfoToDataInfoPanel (int elementId, JPanel dataInfoPanel) {
        HashMap<String, String> dataInfo = getElementData(elementId);
        addJLabel("Stable " + dataInfo.get("name"), Color.BLACK, fontBiggerBold, dataInfoPanel, elementWidth, elementHeight);
        addJLabel("Address: " + dataInfo.get("address"), Color.BLACK, fontMiddle, dataInfoPanel, elementWidth, elementHeight);
    }

    @Override
    protected void handleAddData() {
        new AddStableGUI(userId, userType).createGUI();
        frame.setVisible(false);
    }

    @Override
    protected void handleEditData() {

    }

    @Override
    protected void handleRemoveData() {

    }

    public StablesScrollGUI(int userId, String userType) {
        super(userId, userType, "Stables");
    }

    public static void main(String[] args) {
        new StablesScrollGUI(-1, "None").createGUI();
    }

}
