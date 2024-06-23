package bd2.gui.SeeDataByScrolling;

import bd2.gui.AddDataByForm.AddHorseGUI;
import bd2.gui.components.ScrollElementButton;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;

public class HorsesScrollGUI extends DataScrollTemplate {

	boolean[] doesUserChooseThisHorse = {true, false, false, true, false, true, true};

    //[MOCK]
    @Override
    protected void getElementsData() {
        this.fittingElementsIds = new Integer[]{1,2,3,4,5,6,7,8,9,10,12,13,14,15,16,17,18,19,20,21,22,23,24,25};
        this.nrOfElements = fittingElementsIds.length;
    }

    // [MOCK]
    @Override
    protected HashMap<String, String> getElementData(int elementId) {
        HashMap<String, String> dataInfo = new HashMap<String, String>();
        dataInfo.put("bread", "Appaloosa");
        dataInfo.put("height", "100");
        dataInfo.put("color", "Chestnut");
        dataInfo.put("eye_color", "Blue");
        dataInfo.put("age", "4");
        dataInfo.put("origin", "Peru");
        dataInfo.put("hairstyle", "Braided");
        return dataInfo;
    }

    @Override
    protected void addInfoToDataInfoPanel (int elementId, JPanel dataInfoPanel) {
        HashMap<String, String> dataInfo = getElementData(elementId);
        addJLabel("Horse - " + dataInfo.get("bread") + " from " + dataInfo.get("origin"), Color.BLACK, fontBiggerBold, dataInfoPanel, elementWidth, elementHeight);
        addJLabel("Age: " + dataInfo.get("age") + " years, Height: " + dataInfo.get("height") + " cm", Color.BLACK, fontMiddle, dataInfoPanel, elementWidth, elementHeight);
        addJLabel("Color: " + dataInfo.get("color") + ", Eye color: " + dataInfo.get("eye_color") + ", Hairstyle: " + dataInfo.get("hairstyle"), Color.BLACK, fontMiddle, dataInfoPanel, elementWidth, elementHeight);
    }

	protected void createScrollButtons(int elementId, JPanel dataPanel) {

        int buttonSize = scrollButtonSize; buttonsGapSize = buttonSize/3;
        dataPanel.add(Box.createRigidArea(new Dimension(buttonsGapSize,0)));

        //[MOCK] normalnie nie byloby tablicy tylko modyfikacja danych Ridera
        int indexOfElementInFittingElements = Arrays.asList(this.fittingElementsIds).indexOf(elementId);

        if (userType.equals("Rider")) {
            if (this.doesUserChooseThisHorse[indexOfElementInFittingElements])
            {
                ScrollElementButton unregisterButton = new ScrollElementButton("Cancel", buttonSize, buttonSize, statusWrongLighter, statusWrong, fontButtons, true, elementId);
                unregisterButton.addActionListener(actionEvent -> {
                    //[MOCK]
                    this.doesUserChooseThisHorse[indexOfElementInFittingElements] = false;
                    JOptionPane.showMessageDialog(frame, "Successfully cancelled trainings.");
                    switchRegisterButtons(elementId, dataPanel);
                });
                dataPanel.add(unregisterButton);
            } else {
                ScrollElementButton registerButton = new ScrollElementButton("Register", buttonSize, buttonSize, secondColor, secondColorDarker, fontButtons, true, elementId);
                registerButton.addActionListener(actionEvent -> {
                    //[MOCK]
                    this.doesUserChooseThisHorse[indexOfElementInFittingElements] = true;
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
        new AddHorseGUI(userId, userType).createGUI();
        frame.setVisible(false);
    }

    @Override
    protected void handleEditData() {

    }

    @Override
    protected void handleRemoveData() {

    }

    public HorsesScrollGUI(int userId, String userType){
        super(userId, userType, "Horses");
    }

    public static void main(String[] args) {
        new HorsesScrollGUI(-1, "None").createGUI();
    }

}
