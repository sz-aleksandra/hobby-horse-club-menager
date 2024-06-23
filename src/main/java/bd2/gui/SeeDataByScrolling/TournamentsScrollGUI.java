package bd2.gui.SeeDataByScrolling;

import bd2.gui.AddDataByForm.AddTournamentGUI;
import bd2.gui.components.ScrollElementButton;
import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;

public class TournamentsScrollGUI extends DataScrollTemplate {

    //[MOCK] Tworze tymczasowe pole klasy, powinna to byc funkcja z logic, ktora wykonujemy gdy chcemy sprawdzic czy danu user jest na turniej juz zapisany
    boolean[] isUserRegisteredForTournament = {true, false, false};

    //[MOCK]
    @Override
    protected void getElementsData() {
        this.fittingElementsIds = new Integer[]{1,2,3};
        this.nrOfElements = fittingElementsIds.length;
    }

    // [MOCK]
    @Override
    protected HashMap<String, String> getElementData(int elementId) {
        HashMap<String, String> dataInfo = new HashMap<String, String>();
        dataInfo.put("name", "XXIV Monthly Hobby Horsing Contest");
        dataInfo.put("date", "2024-06-12");
        dataInfo.put("address", "ul. Kolorowa 41, Warszawa");
        return dataInfo;
    }

    @Override
    protected void addInfoToDataInfoPanel (int elementId, JPanel dataInfoPanel) {
        HashMap<String, String> dataInfo = getElementData(elementId);
        addJLabel("Tournament " + dataInfo.get("name"), Color.BLACK, fontBiggerBold, dataInfoPanel, elementWidth, elementHeight);
        addJLabel("hosted on " + dataInfo.get("date") + " at " + dataInfo.get("address"), Color.BLACK, fontMiddle, dataInfoPanel, elementWidth, elementHeight);
    }

    protected void createScrollButtons(int elementId, JPanel dataPanel) {

        int buttonSize = scrollButtonSize; buttonsGapSize = buttonSize/3;
        dataPanel.add(Box.createRigidArea(new Dimension(buttonsGapSize,0)));

        //[MOCK] normalnie nie byloby tablicy tylko modyfikacja danych Ridera
        int indexOfElementInFittingElements = Arrays.asList(this.fittingElementsIds).indexOf(elementId);

        if (userType.equals("Rider")) {
            if (this.isUserRegisteredForTournament[indexOfElementInFittingElements])
            {
                ScrollElementButton unregisterButton = new ScrollElementButton("Surrender", buttonSize, buttonSize, statusWrongLighter, statusWrong, fontButtons, true, elementId);
                unregisterButton.addActionListener(actionEvent -> {
                    //[MOCK]
                    this.isUserRegisteredForTournament[indexOfElementInFittingElements] = false;
                    JOptionPane.showMessageDialog(frame, "You have cancelled participation in a tournament.");
                    switchRegisterButtons(elementId, dataPanel);
                });
                dataPanel.add(unregisterButton);
            } else {
                ScrollElementButton registerButton = new ScrollElementButton("Enter", buttonSize, buttonSize, secondColor, secondColorDarker, fontButtons, true, elementId);
                registerButton.addActionListener(actionEvent -> {
                    //[MOCK]
                    this.isUserRegisteredForTournament[indexOfElementInFittingElements] = true;
                    JOptionPane.showMessageDialog(frame, "Successfully entered a tournament!");
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

    void switchRegisterButtons(int elementId, JPanel tournementPanel){
        // Removing button
        tournementPanel.remove(tournementPanel.getComponentCount()-1);
        // Removing space before the button
        tournementPanel.remove(tournementPanel.getComponentCount()-1);
        createScrollButtons(elementId, tournementPanel);
        tournementPanel.revalidate(); tournementPanel.repaint();
    }

    @Override
    protected void handleAddData() {
        new AddTournamentGUI(userId, userType).createGUI();
        frame.setVisible(false);
    }

    @Override
    protected void handleEditData() {

    }

    @Override
    protected void handleRemoveData() {

    }

    public TournamentsScrollGUI(int userId, String userType){
        super(userId, userType, "Tournaments");
        elementHeight = frameHeight/6;
        elementWidth = frameWidth*3/5;
    }

    public static void main(String[] args) {
        new TournamentsScrollGUI(-1, "None").createGUI();
    }

}
