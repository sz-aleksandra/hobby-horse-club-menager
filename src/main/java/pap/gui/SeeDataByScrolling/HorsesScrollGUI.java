package pap.gui.SeeDataByScrolling;

import pap.gui.HomePageGUI;
import pap.gui.components.ScrollElementButton;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class HorsesScrollGUI extends ScrollGUITemplate {

    //[MOCK]
    protected void getElementsData() {
        this.fittingElementsIds = new Integer[]{1,2,3,4,5,6,7,8,9,10,12,13,14,15,16,17,18,19,20,21,22,23,24,25};
        this.nrOfElements = fittingElementsIds.length;
    }

    // [MOCK]
    protected HashMap<String, String> getElementData(int elementId) {
        HashMap<String, String> horseInfo = new HashMap<String, String>();
        horseInfo.put("bread", "Appaloosa");
        horseInfo.put("height", "100");
        horseInfo.put("color", "Chestnut");
        horseInfo.put("eye_color", "Blue");
        horseInfo.put("age", "4");
        horseInfo.put("origin", "Peru");
        horseInfo.put("hairstyle", "Braided");
        return horseInfo;
    }

    protected JPanel createScrollElement(int elementId) {

        HashMap<String, String> horseInfo = getElementData(elementId);

        JPanel horsePanel = new JPanel();
        horsePanel.setBackground(neutralBlue);
        horsePanel.setLayout(new BoxLayout(horsePanel, BoxLayout.LINE_AXIS));
        horsePanel.setPreferredSize(new Dimension(frameWidth, elementHeight));
        horsePanel.setMaximumSize(new Dimension(frameWidth, elementHeight));
        horsePanel.add(Box.createRigidArea(new Dimension(frameWidth/20,0)));

        JPanel horseInfoPanel = new JPanel();
        horseInfoPanel.setBackground(neutralGray);
        horseInfoPanel.setLayout(new BoxLayout(horseInfoPanel, BoxLayout.PAGE_AXIS));
        horseInfoPanel.setPreferredSize(new Dimension(elementWidth, elementHeight));
        horseInfoPanel.setMaximumSize(new Dimension(elementWidth, elementHeight));
        addJLabel("Horse - " + horseInfo.get("bread") + " from " + horseInfo.get("origin"), Color.BLACK, fontBiggerBold, horseInfoPanel, elementWidth, elementHeight);
        addJLabel("Age: " + horseInfo.get("age") + " years, Height: " + horseInfo.get("height") + " cm", Color.BLACK, fontMiddle, horseInfoPanel, elementWidth, elementHeight);
        addJLabel("Color: " + horseInfo.get("color") + ", Eye color: " + horseInfo.get("eye_color") + ", Hairstyle: " + horseInfo.get("hairstyle"), Color.BLACK, fontMiddle, horseInfoPanel, elementWidth, elementHeight);
        horsePanel.add(horseInfoPanel);

        return horsePanel;
    }

    protected void createScrollButtons(int elementId, JPanel horsePanel) {

        int buttonSize = scrollButtonSize; int gapSize = buttonSize/3;
        horsePanel.add(Box.createRigidArea(new Dimension(gapSize,0)));

        ScrollElementButton editButton = new ScrollElementButton("Edit", buttonSize, buttonSize, secondColor, secondColorDarker, fontButtons, true, elementId);
        editButton.addActionListener(actionEvent -> {
            //
        });
        horsePanel.add(editButton);
        horsePanel.add(Box.createRigidArea(new Dimension(gapSize,0)));

        ScrollElementButton removeButton = new ScrollElementButton("Delete", buttonSize, buttonSize, statusWrongLighter, statusWrong, fontButtons, true, elementId);
        editButton.addActionListener(actionEvent -> {
            //
        });
        horsePanel.add(removeButton);

    }

    @Override
    protected void undoBtnClickedAction(){
        new HomePageGUI(userId, userType).createGUI();
        frame.setVisible(false);
    }

    public HorsesScrollGUI(int userId, String userType){
        super(userId, userType);
        elementHeight = frameHeight/6;
        elementWidth = frameWidth*6/10;
        pageName = "Horses";
    }

    public static void main(String[] args) {
        new HorsesScrollGUI(-1, "None").createGUI();
    }

}
