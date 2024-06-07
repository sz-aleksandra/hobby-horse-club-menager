package pap.gui.SeeDataByScrolling;

import pap.gui.HomePageGUI;
import pap.gui.components.ScrollElementButton;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class PositionsScrollGUI extends ScrollGUITemplate {

    //[MOCK]
    protected void getElementsData() {
        this.fittingElementsIds = new Integer[]{1,2,3,4};
        this.nrOfElements = fittingElementsIds.length;
    }

    // [MOCK]
    protected HashMap<String, String> getElementData(int elementId) {
        HashMap<String, String> positionInfo = new HashMap<String, String>();
        positionInfo.put("name", "Trainer");
        positionInfo.put("salary_min", "2400");
        positionInfo.put("salary_max", "6000");
        positionInfo.put("speciality", "Jumping Trainings");
        return positionInfo;
    }

    protected JPanel createScrollElement(int elementId) {

        HashMap<String, String> positionInfo = getElementData(elementId);

        JPanel positionPanel = new JPanel();
        positionPanel.setBackground(neutralBlue);
        positionPanel.setLayout(new BoxLayout(positionPanel, BoxLayout.LINE_AXIS));
        positionPanel.setPreferredSize(new Dimension(frameWidth, elementHeight));
        positionPanel.setMaximumSize(new Dimension(frameWidth, elementHeight));
        positionPanel.add(Box.createRigidArea(new Dimension(frameWidth/20,0)));

        JPanel positionInfoPanel = new JPanel();
        positionInfoPanel.setBackground(neutralGray);
        positionInfoPanel.setLayout(new BoxLayout(positionInfoPanel, BoxLayout.PAGE_AXIS));
        positionInfoPanel.setPreferredSize(new Dimension(elementWidth, elementHeight));
        positionInfoPanel.setMaximumSize(new Dimension(elementWidth, elementHeight));
        addJLabel(positionInfo.get("name"), Color.BLACK, fontBiggerBold, positionInfoPanel, elementWidth, elementHeight);
        addJLabel("Salary: " + positionInfo.get("salary_min") + " - " + positionInfo.get("salary_max") + " PLN", Color.BLACK, fontMiddle, positionInfoPanel, elementWidth, elementHeight);
        addJLabel("Speciality: " + positionInfo.get("speciality"), Color.BLACK, fontMiddle, positionInfoPanel, elementWidth, elementHeight);
        positionPanel.add(positionInfoPanel);

        return positionPanel;
    }

    protected void createScrollButtons(int elementId, JPanel positionPanel) {

        int buttonSize = scrollButtonSize; int gapSize = buttonSize/3;
        positionPanel.add(Box.createRigidArea(new Dimension(gapSize,0)));

        ScrollElementButton editButton = new ScrollElementButton("Edit", buttonSize, buttonSize, secondColor, secondColorDarker, fontButtons, true, elementId);
        editButton.addActionListener(actionEvent -> {
            //
        });
        positionPanel.add(editButton);
        positionPanel.add(Box.createRigidArea(new Dimension(gapSize,0)));

        ScrollElementButton removeButton = new ScrollElementButton("Delete", buttonSize, buttonSize, statusWrongLighter, statusWrong, fontButtons, true, elementId);
        editButton.addActionListener(actionEvent -> {
            //
        });
        positionPanel.add(removeButton);

    }

    @Override
    protected void undoBtnClickedAction(){
        new HomePageGUI(userId, userType).createGUI();
        frame.setVisible(false);
    }

    public PositionsScrollGUI(int userId, String userType){
        super(userId, userType);
        elementHeight = frameHeight/6;
        elementWidth = frameWidth*6/10;
        pageName = "Positions";
    }

    public static void main(String[] args) {
        new PositionsScrollGUI(-1, "None").createGUI();
    }

}
