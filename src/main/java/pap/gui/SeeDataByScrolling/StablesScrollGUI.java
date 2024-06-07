package pap.gui.SeeDataByScrolling;

import pap.gui.HomePageGUI;
import pap.gui.components.ScrollElementButton;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class StablesScrollGUI extends ScrollGUITemplate {

    //[MOCK]
    protected void getElementsData() {
        this.fittingElementsIds = new Integer[]{1,2,3,4};
        this.nrOfElements = fittingElementsIds.length;
    }

    // [MOCK]
    protected HashMap<String, String> getElementData(int elementId) {
        HashMap<String, String> stableInfo = new HashMap<String, String>();
        stableInfo.put("name", "Horse Palace");
        stableInfo.put("address", "ul. Słoneczna 5, Otwock 05-400, Polska");
        return stableInfo;
    }

    protected JPanel createScrollElement(int elementId) {

        HashMap<String, String> stableInfo = getElementData(elementId);

        JPanel stablePanel = new JPanel();
        stablePanel.setBackground(neutralBlue);
        stablePanel.setLayout(new BoxLayout(stablePanel, BoxLayout.LINE_AXIS));
        stablePanel.setPreferredSize(new Dimension(frameWidth, elementHeight));
        stablePanel.setMaximumSize(new Dimension(frameWidth, elementHeight));
        stablePanel.add(Box.createRigidArea(new Dimension(frameWidth/20,0)));

        JPanel stableInfoPanel = new JPanel();
        stableInfoPanel.setBackground(neutralGray);
        stableInfoPanel.setLayout(new BoxLayout(stableInfoPanel, BoxLayout.PAGE_AXIS));
        stableInfoPanel.setPreferredSize(new Dimension(elementWidth, elementHeight));
        stableInfoPanel.setMaximumSize(new Dimension(elementWidth, elementHeight));
        addJLabel("Stable " + stableInfo.get("name"), Color.BLACK, fontBiggerBold, stableInfoPanel, elementWidth, elementHeight);
        addJLabel("Address: " + stableInfo.get("address"), Color.BLACK, fontMiddle, stableInfoPanel, elementWidth, elementHeight);
        stablePanel.add(stableInfoPanel);

        return stablePanel;
    }

    protected void createScrollButtons(int elementId, JPanel stablePanel) {

        int buttonSize = scrollButtonSize; int gapSize = buttonSize/3;
        stablePanel.add(Box.createRigidArea(new Dimension(gapSize,0)));

        ScrollElementButton editButton = new ScrollElementButton("Edit", buttonSize, buttonSize, secondColor, secondColorDarker, fontButtons, true, elementId);
        editButton.addActionListener(actionEvent -> {
            //
        });
        stablePanel.add(editButton);
        stablePanel.add(Box.createRigidArea(new Dimension(gapSize,0)));

        ScrollElementButton removeButton = new ScrollElementButton("Delete", buttonSize, buttonSize, statusWrongLighter, statusWrong, fontButtons, true, elementId);
        editButton.addActionListener(actionEvent -> {
            //
        });
        stablePanel.add(removeButton);

    }

    @Override
    protected void undoBtnClickedAction(){
        new HomePageGUI(userId, userType).createGUI();
        frame.setVisible(false);
    }

    public StablesScrollGUI(int userId, String userType){
        super(userId, userType);
        elementHeight = frameHeight/6;
        elementWidth = frameWidth*6/10;
        pageName = "Stables";
    }

    public static void main(String[] args) {
        new StablesScrollGUI(-1, "None").createGUI();
    }

}
