package pap.gui.SeeDataByScrolling;

import pap.gui.HomePageGUI;
import pap.gui.components.ScrollElementButton;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class AccessoriesScrollGUI extends ScrollGUITemplate {

    //[MOCK]
    protected void getElementsData() {
        this.fittingElementsIds = new Integer[]{1,2,3,4};
        this.nrOfElements = fittingElementsIds.length;
    }

    // [MOCK]
    protected HashMap<String, String> getElementData(int elementId) {
        HashMap<String, String> accessoryInfo = new HashMap<String, String>();
        accessoryInfo.put("name", "Saddle");
        accessoryInfo.put("custom_info", "<html>Material: Leather, Size: Large, Durability: High, Fastenings: Stainless steel, Color: Dark brown, Brand: Prestige Saddles, Warranty: 5-year warranty</html>");
        return accessoryInfo;
    }

    protected JPanel createScrollElement(int elementId) {

        HashMap<String, String> accessoryInfo = getElementData(elementId);

        JPanel accessoryPanel = new JPanel();
        accessoryPanel.setBackground(neutralBlue);
        accessoryPanel.setLayout(new BoxLayout(accessoryPanel, BoxLayout.LINE_AXIS));
        accessoryPanel.setPreferredSize(new Dimension(frameWidth, elementHeight));
        accessoryPanel.setMaximumSize(new Dimension(frameWidth, elementHeight));
        accessoryPanel.add(Box.createRigidArea(new Dimension(frameWidth/20,0)));

        JPanel accessoryInfoPanel = new JPanel();
        accessoryInfoPanel.setBackground(neutralGray);
        accessoryInfoPanel.setLayout(new BoxLayout(accessoryInfoPanel, BoxLayout.PAGE_AXIS));
        accessoryInfoPanel.setPreferredSize(new Dimension(elementWidth, elementHeight));
        accessoryInfoPanel.setMaximumSize(new Dimension(elementWidth, elementHeight));
        addJLabel(accessoryInfo.get("name"), Color.BLACK, fontBiggerBold, accessoryInfoPanel, elementWidth, elementHeight);
        addJLabel(accessoryInfo.get("custom_info"), Color.BLACK, fontMiddle, accessoryInfoPanel, elementWidth, elementHeight);
        accessoryPanel.add(accessoryInfoPanel);

        return accessoryPanel;
    }

    protected void createScrollButtons(int elementId, JPanel accessoryPanel) {

        int buttonSize = scrollButtonSize; int gapSize = buttonSize/3;
        accessoryPanel.add(Box.createRigidArea(new Dimension(gapSize,0)));

        ScrollElementButton editButton = new ScrollElementButton("Edit", buttonSize, buttonSize, secondColor, secondColorDarker, fontButtons, true, elementId);
        editButton.addActionListener(actionEvent -> {
            //
        });
        accessoryPanel.add(editButton);
        accessoryPanel.add(Box.createRigidArea(new Dimension(gapSize,0)));

        ScrollElementButton removeButton = new ScrollElementButton("Delete", buttonSize, buttonSize, statusWrongLighter, statusWrong, fontButtons, true, elementId);
        editButton.addActionListener(actionEvent -> {
            //
        });
        accessoryPanel.add(removeButton);

    }

    @Override
    protected void undoBtnClickedAction(){
        new HomePageGUI(userId, userType).createGUI();
        frame.setVisible(false);
    }

    public AccessoriesScrollGUI(int userId, String userType){
        super(userId, userType);
        elementHeight = frameHeight/6;
        elementWidth = frameWidth*6/10;
        pageName = "Accessories";
    }

    public static void main(String[] args) {
        new AccessoriesScrollGUI(-1, "None").createGUI();
    }

}
