package pap.gui.SeeDataByScrolling;

import pap.gui.components.ScrollElementButton;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;

public class TrainingsScrollGUI extends DataScrollTemplate {

    //[MOCK] Tworze tymczasowe pole klasy, powinna to byc funkcja z logic, ktora wykonujemy gdy chcemy sprawdzic czy danu user jest na trening juz zapisany
    boolean[] isUserRegisteredForTraining = {true, false, false, true, false, true, true};

    //[MOCK]
    @Override
    protected void getElementsData() {
        this.fittingElementsIds = new Integer[]{1,2,3,4,5,6,7};
        this.nrOfElements = fittingElementsIds.length;
    }

    // [MOCK]
    @Override
    protected HashMap<String, String> getElementData(int elementId) {
        HashMap<String, String> dataInfo = new HashMap<String, String>();
        dataInfo.put("type", "Jumping");
        dataInfo.put("date", "Mondays, 3PM");
        dataInfo.put("trainer", "Adam Kaczka");
        dataInfo.put("group", "3");
        dataInfo.put("stable", "Horse Palace, ul. Ogrodowa 5");
        return dataInfo;
    }

    @Override
    protected void addInfoToDataInfoPanel (int elementId, JPanel dataInfoPanel) {
        HashMap<String, String> dataInfo = getElementData(elementId);
        addJLabel("Training " + dataInfo.get("type"), Color.BLACK, fontBiggerBold, dataInfoPanel, elementWidth, elementHeight);
        addJLabel("hosted on " + dataInfo.get("date") + " in " + dataInfo.get("stable"), Color.BLACK, fontMiddle, dataInfoPanel, elementWidth, elementHeight);
        addJLabel("by " + dataInfo.get("trainer") + " for group " + dataInfo.get("group"), Color.BLACK, fontMiddle, dataInfoPanel, elementWidth, elementHeight);
    }

    protected void createScrollButtons(int elementId, JPanel dataPanel) {

        int buttonSize = scrollButtonSize; buttonsGapSize = buttonSize/3;
        dataPanel.add(Box.createRigidArea(new Dimension(buttonsGapSize,0)));

        //[MOCK] normalnie nie byloby tablicy tylko modyfikacja danych Ridera
        int indexOfElementInFittingElements = Arrays.asList(this.fittingElementsIds).indexOf(elementId);

        if (userType.equals("Rider")) {
            if (this.isUserRegisteredForTraining[indexOfElementInFittingElements])
            {
                ScrollElementButton unregisterButton = new ScrollElementButton("Cancel", buttonSize, buttonSize, statusWrongLighter, statusWrong, fontButtons, true, elementId);
                unregisterButton.addActionListener(actionEvent -> {
                    //[MOCK]
                    this.isUserRegisteredForTraining[indexOfElementInFittingElements] = false;
                    JOptionPane.showMessageDialog(frame, "Successfully cancelled trainings.");
                    switchRegisterButtons(elementId, dataPanel);
                });
                dataPanel.add(unregisterButton);
            } else {
                ScrollElementButton registerButton = new ScrollElementButton("Register", buttonSize, buttonSize, secondColor, secondColorDarker, fontButtons, true, elementId);
                registerButton.addActionListener(actionEvent -> {
                    //[MOCK]
                    this.isUserRegisteredForTraining[indexOfElementInFittingElements] = true;
                    JOptionPane.showMessageDialog(frame, "Successfully registered for trainings!");
                    switchRegisterButtons(elementId, dataPanel);
                });
                dataPanel.add(registerButton);
            }
        } else if (userType.equals("Employee") && doesEmployeeHaveWritePermissions()){
            ScrollElementButton editButton = new ScrollElementButton("Edit", buttonSize, buttonSize, secondColor, secondColorDarker, fontButtons, true, elementId);
            editButton.addActionListener(actionEvent -> {
                handleEditData();
            });
            dataPanel.add(editButton);
            dataPanel.add(Box.createRigidArea(new Dimension(buttonsGapSize,0)));

            ScrollElementButton removeButton = new ScrollElementButton("Delete", buttonSize, buttonSize, statusWrongLighter, statusWrong, fontButtons, true, elementId);
            editButton.addActionListener(actionEvent -> {
                handleEditData();
            });
            dataPanel.add(removeButton);
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
    protected void handleAddData() {

    }

    @Override
    protected void handleEditData() {

    }

    @Override
    protected void handleRemoveData() {

    }

    public TrainingsScrollGUI(int userId, String userType){
        super(userId, userType, "Trainings");
        elementHeight = frameHeight/6;
        elementWidth = frameWidth*3/5;
    }

    public static void main(String[] args) {
        new TrainingsScrollGUI(-1, "None").createGUI();
    }

}
