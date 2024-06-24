package bd2.gui.SeeDataByScrolling;

import bd2.gui.AddDataByForm.AddGroupGUI;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class GroupsScrollGUI extends DataScrollTemplate {

    //[MOCK]
    @Override
    protected void getElementsData() {
        this.fittingElementsIds = new Integer[]{1,2,3,4,5,6};
        this.nrOfElements = fittingElementsIds.length;
    }

    // [MOCK]
    @Override
    protected HashMap<String, String> getElementData(int elementId) {
        HashMap<String, String> dataInfo = new HashMap<>();
        dataInfo.put("id", "1");
        dataInfo.put("no_riders", "7");
        dataInfo.put("trainings_for_group", "Id1, Id4, Id7, Id8, Id9");
        return dataInfo;
    }

    @Override
    protected void addInfoToDataInfoPanel (int elementId, JPanel dataInfoPanel) {
        HashMap<String, String> dataInfo = getElementData(elementId);
        addJLabel("Group " + dataInfo.get("id"), Color.BLACK, fontBiggerBold, dataInfoPanel, elementWidth, elementHeight);
        addJLabel("Nr of riders: " + dataInfo.get("no_riders"), Color.BLACK, fontMiddle, dataInfoPanel, elementWidth, elementHeight);
        addJLabel("Trainings for group: " + dataInfo.get("trainings_for_group"), Color.BLACK, fontMiddle, dataInfoPanel, elementWidth, elementHeight);
    }

    @Override
    protected void handleAddData() {
        new AddGroupGUI(userId, userType).createGUI();
        frame.setVisible(false);
    }

    @Override
    protected void handleEditData(int elementId) {

    }

    @Override
    protected void handleRemoveData(int elementId) {

    }

    public GroupsScrollGUI(int userId, String userType){
        super(userId, userType, "Groups");
    }

    public static void main(String[] args) {
        new GroupsScrollGUI(-1, "None").createGUI();
    }

}
