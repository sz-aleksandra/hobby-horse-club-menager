package pap.gui.SeeDataByScrolling;

import pap.gui.HomePageGUI;
import pap.gui.components.ScrollElementButton;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class GroupsScrollGUI extends ScrollGUITemplate {

    //[MOCK]
    protected void getElementsData() {
        this.fittingElementsIds = new Integer[]{1,2,3,4};
        this.nrOfElements = fittingElementsIds.length;
    }

    // [MOCK]
    protected HashMap<String, String> getElementData(int elementId) {
        HashMap<String, String> groupInfo = new HashMap<String, String>();
        groupInfo.put("id", "1");
        groupInfo.put("no_riders", "7");
        groupInfo.put("trainings_for_group", "Id1, Id4, Id7, Id8, Id9");
        return groupInfo;
    }

    protected JPanel createScrollElement(int elementId) {

        HashMap<String, String> groupInfo = getElementData(elementId);

        JPanel groupPanel = new JPanel();
        groupPanel.setBackground(neutralBlue);
        groupPanel.setLayout(new BoxLayout(groupPanel, BoxLayout.LINE_AXIS));
        groupPanel.setPreferredSize(new Dimension(frameWidth, elementHeight));
        groupPanel.setMaximumSize(new Dimension(frameWidth, elementHeight));
        groupPanel.add(Box.createRigidArea(new Dimension(frameWidth/20,0)));

        JPanel groupInfoPanel = new JPanel();
        groupInfoPanel.setBackground(neutralGray);
        groupInfoPanel.setLayout(new BoxLayout(groupInfoPanel, BoxLayout.PAGE_AXIS));
        groupInfoPanel.setPreferredSize(new Dimension(elementWidth, elementHeight));
        groupInfoPanel.setMaximumSize(new Dimension(elementWidth, elementHeight));
        addJLabel("Group " + groupInfo.get("id"), Color.BLACK, fontBiggerBold, groupInfoPanel, elementWidth, elementHeight);
        addJLabel("Nr of riders: " + groupInfo.get("no_riders"), Color.BLACK, fontMiddle, groupInfoPanel, elementWidth, elementHeight);
        addJLabel("Trainings for group: " + groupInfo.get("trainings_for_group"), Color.BLACK, fontMiddle, groupInfoPanel, elementWidth, elementHeight);
        groupPanel.add(groupInfoPanel);

        return groupPanel;
    }

    protected void createScrollButtons(int elementId, JPanel groupPanel) {

        int buttonSize = scrollButtonSize; int gapSize = buttonSize/3;
        groupPanel.add(Box.createRigidArea(new Dimension(gapSize,0)));

        ScrollElementButton editButton = new ScrollElementButton("Edit", buttonSize, buttonSize, secondColor, secondColorDarker, fontButtons, true, elementId);
        editButton.addActionListener(actionEvent -> {
            //
        });
        groupPanel.add(editButton);
        groupPanel.add(Box.createRigidArea(new Dimension(gapSize,0)));

        ScrollElementButton removeButton = new ScrollElementButton("Delete", buttonSize, buttonSize, statusWrongLighter, statusWrong, fontButtons, true, elementId);
        editButton.addActionListener(actionEvent -> {
            //
        });
        groupPanel.add(removeButton);

    }

    @Override
    protected void undoBtnClickedAction(){
        new HomePageGUI(userId, userType).createGUI();
        frame.setVisible(false);
    }

    public GroupsScrollGUI(int userId, String userType){
        super(userId, userType);
        elementHeight = frameHeight/6;
        elementWidth = frameWidth*6/10;
        pageName = "Groups";
    }

    public static void main(String[] args) {
        new GroupsScrollGUI(-1, "None").createGUI();
    }

}
