package pap.gui;

import pap.gui.components.ScrollElementButton;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class RidersScrollGUI extends ScrollGUITemplate{

    //[MOCK]
    void getElementsData() {
        this.fittingElementsIds = new Integer[]{1,2,3,4,5,6,7,8,9,10,12,13,14,15,16};
        this.nrOfElements = fittingElementsIds.length;
    }

    // [MOCK]
    HashMap<String, String> getElementData(int elementId) {
        HashMap<String, String> riderInfo = new HashMap<String, String>();
        riderInfo.put("name", "Ola");
        riderInfo.put("surname", "Nowak");
        riderInfo.put("username", "kochamkonieOla123");
        riderInfo.put("date_of_birth", "2009-05-10");
        riderInfo.put("email", "ola.kochamkonie.nowak@email.com");
        riderInfo.put("phone_number", "+48437987171");
        riderInfo.put("address", "ul. Krakowska 23, Warszawa 02-526, Polska");
        riderInfo.put("has_parent_consent", "yes");
        riderInfo.put("licence_nr", "R5I90032EA");
        return riderInfo;
    }

    JPanel createScrollElement(int elementId) {

        HashMap<String, String> riderInfo = getElementData(elementId);

        JPanel riderPanel = new JPanel();
        riderPanel.setBackground(neutralBlue);
        riderPanel.setLayout(new BoxLayout(riderPanel, BoxLayout.LINE_AXIS));
        riderPanel.setPreferredSize(new Dimension(frameWidth, elementHeight));
        riderPanel.setMaximumSize(new Dimension(frameWidth, elementHeight));
        riderPanel.add(Box.createRigidArea(new Dimension(frameWidth/20,0)));

        JPanel riderInfoPanel = new JPanel();
        riderInfoPanel.setBackground(neutralGray);
        riderInfoPanel.setLayout(new BoxLayout(riderInfoPanel, BoxLayout.PAGE_AXIS));
        riderInfoPanel.setPreferredSize(new Dimension(elementWidth, elementHeight));
        riderInfoPanel.setMaximumSize(new Dimension(elementWidth, elementHeight));
        addJLabel(riderInfo.get("name") + " " + riderInfo.get("surname"), Color.BLACK, fontBiggerBold, riderInfoPanel, elementWidth, elementHeight);
        addJLabel("System username: " + riderInfo.get("username") + ", Date of birth: " + riderInfo.get("date_of_birth"), Color.BLACK, fontSmaller, riderInfoPanel, elementWidth, elementHeight);
        addJLabel("Contact: " + riderInfo.get("email") + ", " + riderInfo.get("phone_number"), Color.BLACK, fontSmaller, riderInfoPanel, elementWidth, elementHeight);
        addJLabel(riderInfo.get("address"), Color.BLACK, fontSmaller, riderInfoPanel, elementWidth, elementHeight);
        addJLabel("Parent consent: " + riderInfo.get("has_parent_consent") + ", Licence no: " + riderInfo.get("licence_nr"), Color.BLACK, fontSmaller, riderInfoPanel, elementWidth, elementHeight);
        riderPanel.add(riderInfoPanel);

        return riderPanel;
    }

    void createScrollButtons(int elementId, JPanel riderPanel) {

        int buttonSize = scrollButtonSize; int gapSize = buttonSize/3;
        riderPanel.add(Box.createRigidArea(new Dimension(gapSize,0)));

        ScrollElementButton editButton = new ScrollElementButton("Edit", buttonSize, buttonSize, secondColor, secondColorDarker, fontButtons, true, elementId);
        editButton.addActionListener(actionEvent -> {
            //
        });
        riderPanel.add(editButton);

    }

    @Override
    void undoBtnClickedAction(){
        new HomePageGUI(userId, userType).createGUI();
        frame.setVisible(false);
    }

    public RidersScrollGUI(int userId, String userType){
        super(userId, userType);
        elementHeight = frameHeight*5/24;
        elementWidth = frameWidth*6/10;
        pageName = "Riders";
    }

    public static void main(String[] args) {
        new RidersScrollGUI(-1, "None").createGUI();
    }

}
