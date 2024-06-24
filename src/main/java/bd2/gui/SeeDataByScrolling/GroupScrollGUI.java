package bd2.gui.SeeDataByScrolling;

import bd2.gui.AddDataByForm.AddGroupGUI;
import bd2.gui.components.ScrollElementButton;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;

public class GroupScrollGUI extends DataScrollTemplate {

    //[MOCK] Tworze tymczasowe pole klasy, powinna to byc funkcja z logic, ktora wykonujemy gdy chcemy sprawdzic czy danu user jest na trening juz zapisany
    boolean[] isUserInTheGroup = {true, false, false, true, false, true, true};

    //[MOCK]
    @Override
    protected void getElementsData() {
        this.fittingElementsIds = new Integer[]{1,2,3,4,5,6,7};
        this.nrOfElements = fittingElementsIds.length;
    }

    // [MOCK]
    @Override
    protected HashMap<String, String> getElementData(int elementId) {
        HashMap<String, String> dataInfo = new HashMap<>();
        dataInfo.put("name", "Kucyki");
        dataInfo.put("group_members", "10");
        dataInfo.put("max_group_members", "13");
        return dataInfo;
    }

    @Override
    protected void addInfoToDataInfoPanel (int elementId, JPanel dataInfoPanel) {
        HashMap<String, String> dataInfo = getElementData(elementId);
        addJLabel("Group " + dataInfo.get("name"), Color.BLACK, fontBiggerBold, dataInfoPanel, elementWidth, elementHeight);
        addJLabel("Group members: " + dataInfo.get("group_members"), Color.BLACK, fontMiddle, dataInfoPanel, elementWidth, elementHeight);
        addJLabel("Max group members: " + dataInfo.get("max_group_members"), Color.BLACK, fontMiddle, dataInfoPanel, elementWidth, elementHeight);
    }

    protected void createScrollButtons(int elementId, JPanel dataPanel) {

        int buttonSize = scrollButtonSize; buttonsGapSize = buttonSize/3;
        dataPanel.add(Box.createRigidArea(new Dimension(buttonsGapSize,0)));

        //[MOCK] normalnie nie byloby tablicy tylko modyfikacja danych Ridera
        int indexOfElementInFittingElements = Arrays.asList(this.fittingElementsIds).indexOf(elementId);

        if (userType.equals("Rider")) {
            if (this.isUserInTheGroup[indexOfElementInFittingElements])
            {
                ScrollElementButton unregisterButton = new ScrollElementButton("Cancel", buttonSize, buttonSize, statusWrongLighter, statusWrong, fontButtons, true, elementId);
                unregisterButton.addActionListener(actionEvent -> {
                    //[MOCK]
                    this.isUserInTheGroup[indexOfElementInFittingElements] = false;
                    JOptionPane.showMessageDialog(frame, "Successfully cancelled trainings.");
                    switchRegisterButtons(elementId, dataPanel);
                });
                dataPanel.add(unregisterButton);
            } else {
                ScrollElementButton registerButton = new ScrollElementButton("Register", buttonSize, buttonSize, secondColor, secondColorDarker, fontButtons, true, elementId);
                registerButton.addActionListener(actionEvent -> {
                    //[MOCK]
                    this.isUserInTheGroup[indexOfElementInFittingElements] = true;
                    JOptionPane.showMessageDialog(frame, "Successfully registered for trainings!");
                    switchRegisterButtons(elementId, dataPanel);
                });
                dataPanel.add(registerButton);
            }
        } else if (userType.equals("Employee") && doesEmployeeHaveWritePermissions()){
            ScrollElementButton editButton = new ScrollElementButton("Edit", buttonSize, buttonSize, secondColor, secondColorDarker, fontButtons, true, elementId);
            editButton.addActionListener(actionEvent -> handleEditData(elementId));
            dataPanel.add(editButton);
            dataPanel.add(Box.createRigidArea(new Dimension(buttonsGapSize,0)));

            ScrollElementButton removeButton = new ScrollElementButton("Delete", buttonSize, buttonSize, statusWrongLighter, statusWrong, fontButtons, true, elementId);
            editButton.addActionListener(actionEvent -> handleEditData(elementId));
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
        new AddGroupGUI(userId, userType).createGUI();
        frame.setVisible(false);
    }

    @Override
    protected void handleEditData(int elementId) {
        new AddGroupGUI(userId, userType, elementId).createGUI();
        frame.setVisible(false);
    }

    @Override
    protected void handleRemoveData(int elementId) {

    }

    public GroupScrollGUI(int userId, String userType){
        super(userId, userType, "Groups");
        elementHeight = frameHeight/6;
        elementWidth = frameWidth*3/5;
    }

    public static void main(String[] args) {
        new GroupScrollGUI(-1, "None").createGUI();
    }

}
