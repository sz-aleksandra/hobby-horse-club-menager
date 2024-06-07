package pap.gui.SeeDataByScrolling;

import pap.gui.HomePageGUI;
import pap.gui.components.ScrollElementButton;
import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;

public class TrainingsScrollGUI extends ScrollGUITemplate {

    //[MOCK] Tworze tymczasowe pole klasy, powinna to byc funkcja z logic, ktora wykonujemy gdy chcemy sprawdzic czy danu user jest na trening juz zapisany
    boolean[] isUserRegisteredForTraining = {true, false, false, true, false, true, true};

    //[MOCK]
    protected void getElementsData() {
        this.fittingElementsIds = new Integer[]{1,2,3,4,5,6,7};
        this.nrOfElements = fittingElementsIds.length;
    }

    // [MOCK]
    protected HashMap<String, String> getElementData(int elementId) {
        HashMap<String, String> trainingInfo = new HashMap<String, String>();
        trainingInfo.put("type", "Jumping");
        trainingInfo.put("date", "Mondays, 3PM");
        trainingInfo.put("trainer", "Adam Kaczka");
        trainingInfo.put("group", "3");
        trainingInfo.put("stable", "Horse Palace, ul. Ogrodowa 5");
        return trainingInfo;
    }

    protected JPanel createScrollElement(int elementId) {

        HashMap<String, String> trainingInfo = getElementData(elementId);

        JPanel trainingPanel = new JPanel();
        trainingPanel.setBackground(neutralBlue);
        trainingPanel.setLayout(new BoxLayout(trainingPanel, BoxLayout.LINE_AXIS));
        trainingPanel.setPreferredSize(new Dimension(frameWidth, elementHeight));
        trainingPanel.setMaximumSize(new Dimension(frameWidth, elementHeight));
        trainingPanel.add(Box.createRigidArea(new Dimension(frameWidth/20,0)));

        JPanel trainingInfoPanel = new JPanel();
        trainingInfoPanel.setBackground(neutralGray);
        trainingInfoPanel.setLayout(new BoxLayout(trainingInfoPanel, BoxLayout.PAGE_AXIS));
        trainingInfoPanel.setPreferredSize(new Dimension(elementWidth, elementHeight));
        trainingInfoPanel.setMaximumSize(new Dimension(elementWidth, elementHeight));
        addJLabel("Training " + trainingInfo.get("type"), Color.BLACK, fontBiggerBold, trainingInfoPanel, elementWidth, elementHeight);
        addJLabel("hosted on " + trainingInfo.get("date") + " in " + trainingInfo.get("stable"), Color.BLACK, fontMiddle, trainingInfoPanel, elementWidth, elementHeight);
        addJLabel("by " + trainingInfo.get("trainer") + " for group " + trainingInfo.get("group"), Color.BLACK, fontMiddle, trainingInfoPanel, elementWidth, elementHeight);
        trainingPanel.add(trainingInfoPanel);

        return trainingPanel;
    }

    protected void createScrollButtons(int elementId, JPanel trainingPanel) {

        int buttonSize = scrollButtonSize; int gapSize = buttonSize/3;
        trainingPanel.add(Box.createRigidArea(new Dimension(gapSize,0)));

        //[MOCK] normalnie nie byloby tablicy tylko modyfikacja danych Ridera
        int indexOfElementInFittingElements = Arrays.asList(this.fittingElementsIds).indexOf(elementId);

        if (userType == "Rider") {
            if (this.isUserRegisteredForTraining[indexOfElementInFittingElements])
            {
                ScrollElementButton unregisterButton = new ScrollElementButton("Cancel", buttonSize, buttonSize, statusWrongLighter, statusWrong, fontButtons, true, elementId);
                unregisterButton.addActionListener(actionEvent -> {
                    //[MOCK]
                    this.isUserRegisteredForTraining[indexOfElementInFittingElements] = false;
                    JOptionPane.showMessageDialog(frame, "Successfully cancelled trainings.");
                    switchRegisterButtons(elementId, trainingPanel);
                });
                trainingPanel.add(unregisterButton);
            } else {
                ScrollElementButton registerButton = new ScrollElementButton("Register", buttonSize, buttonSize, secondColor, secondColorDarker, fontButtons, true, elementId);
                registerButton.addActionListener(actionEvent -> {
                    //[MOCK]
                    this.isUserRegisteredForTraining[indexOfElementInFittingElements] = true;
                    JOptionPane.showMessageDialog(frame, "Successfully registered for trainings!");
                    switchRegisterButtons(elementId, trainingPanel);
                });
                trainingPanel.add(registerButton);
            }
        } else {
            ScrollElementButton editButton = new ScrollElementButton("Edit", buttonSize, buttonSize, secondColor, secondColorDarker, fontButtons, true, elementId);
            editButton.addActionListener(actionEvent -> {
                //
            });
            trainingPanel.add(editButton);
            trainingPanel.add(Box.createRigidArea(new Dimension(gapSize,0)));

            ScrollElementButton removeButton = new ScrollElementButton("Delete", buttonSize, buttonSize, statusWrongLighter, statusWrong, fontButtons, true, elementId);
            editButton.addActionListener(actionEvent -> {
                //
            });
            trainingPanel.add(removeButton);
        }

    }

    void switchRegisterButtons(int elementId, JPanel trainingPanel){
        // Removing button
        trainingPanel.remove(trainingPanel.getComponentCount()-1);
        // Removing space before the button
        trainingPanel.remove(trainingPanel.getComponentCount()-1);
        createScrollButtons(elementId, trainingPanel);
        trainingPanel.revalidate(); trainingPanel.repaint();
    }

    @Override
    protected void undoBtnClickedAction(){
        new HomePageGUI(userId, userType).createGUI();
        frame.setVisible(false);
    }

    public TrainingsScrollGUI(int userId, String userType){
        super(userId, userType);
        elementHeight = frameHeight/6;
        elementWidth = frameWidth*3/5;
        pageName = "Trainings";
    }

    public static void main(String[] args) {
        new TrainingsScrollGUI(-1, "None").createGUI();
    }

}
