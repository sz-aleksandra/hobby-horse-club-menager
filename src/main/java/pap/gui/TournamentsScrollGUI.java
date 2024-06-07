package pap.gui;

import pap.gui.components.ScrollElementButton;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;

public class TournamentsScrollGUI extends ScrollGUITemplate{

    //[MOCK] Tworze tymczasowe pole klasy, powinna to byc funkcja z logic, ktora wykonujemy gdy chcemy sprawdzic czy danu user jest na turniej juz zapisany
    boolean[] isUserRegisteredForTournament = {true, false, false};

    //[MOCK]
    void getElementsData() {
        this.fittingElementsIds = new Integer[]{1,2,3};
        this.nrOfElements = fittingElementsIds.length;
    }

    // [MOCK]
    HashMap<String, String> getElementData(int elementId) {
        HashMap<String, String> tournamentInfo = new HashMap<String, String>();
        tournamentInfo.put("name", "XXIV Monthly Hobby Horsing Contest");
        tournamentInfo.put("date", "2024-06-12");
        tournamentInfo.put("address", "ul. Kolorowa 41, Warszawa");
        return tournamentInfo;
    }

    JPanel createScrollElement(int elementId) {

        HashMap<String, String> tournamentInfo = getElementData(elementId);

        JPanel tournamentPanel = new JPanel();
        tournamentPanel.setBackground(neutralBlue);
        tournamentPanel.setLayout(new BoxLayout(tournamentPanel, BoxLayout.LINE_AXIS));
        tournamentPanel.setPreferredSize(new Dimension(frameWidth, elementHeight));
        tournamentPanel.setMaximumSize(new Dimension(frameWidth, elementHeight));
        tournamentPanel.add(Box.createRigidArea(new Dimension(frameWidth/20,0)));

        JPanel tournamentInfoPanel = new JPanel();
        tournamentInfoPanel.setBackground(neutralGray);
        tournamentInfoPanel.setLayout(new BoxLayout(tournamentInfoPanel, BoxLayout.PAGE_AXIS));
        tournamentInfoPanel.setPreferredSize(new Dimension(elementWidth, elementHeight));
        tournamentInfoPanel.setMaximumSize(new Dimension(elementWidth, elementHeight));
        addJLabel("Tournament " + tournamentInfo.get("name"), Color.BLACK, fontBiggerBold, tournamentInfoPanel, elementWidth, elementHeight);
        addJLabel("hosted on " + tournamentInfo.get("date") + " at " + tournamentInfo.get("address"), Color.BLACK, fontMiddle, tournamentInfoPanel, elementWidth, elementHeight);
        tournamentPanel.add(tournamentInfoPanel);

        return tournamentPanel;
    }

    void createScrollButtons(int elementId, JPanel tournamentPanel) {

        int buttonSize = scrollButtonSize; int gapSize = buttonSize/3;
        tournamentPanel.add(Box.createRigidArea(new Dimension(gapSize,0)));

        //[MOCK] normalnie nie byloby tablicy tylko modyfikacja danych Ridera
        int indexOfElementInFittingElements = Arrays.asList(this.fittingElementsIds).indexOf(elementId);

        if (userType == "Rider") {
            if (this.isUserRegisteredForTournament[indexOfElementInFittingElements])
            {
                ScrollElementButton unregisterButton = new ScrollElementButton("Surrender", buttonSize, buttonSize, statusWrongLighter, statusWrong, fontButtons, true, elementId);
                unregisterButton.addActionListener(actionEvent -> {
                    //[MOCK]
                    this.isUserRegisteredForTournament[indexOfElementInFittingElements] = false;
                    JOptionPane.showMessageDialog(frame, "You have cancelled participation in a tournament.");
                    switchRegisterButtons(elementId, tournamentPanel);
                });
                tournamentPanel.add(unregisterButton);
            } else {
                ScrollElementButton registerButton = new ScrollElementButton("Enter", buttonSize, buttonSize, secondColor, secondColorDarker, fontButtons, true, elementId);
                registerButton.addActionListener(actionEvent -> {
                    //[MOCK]
                    this.isUserRegisteredForTournament[indexOfElementInFittingElements] = true;
                    JOptionPane.showMessageDialog(frame, "Successfully entered a tournament!");
                    switchRegisterButtons(elementId, tournamentPanel);
                });
                tournamentPanel.add(registerButton);
            }
        } else {
            ScrollElementButton editButton = new ScrollElementButton("Edit", buttonSize, buttonSize, secondColor, secondColorDarker, fontButtons, true, elementId);
            editButton.addActionListener(actionEvent -> {
                //
            });
            tournamentPanel.add(editButton);
        }

    }

    void switchRegisterButtons(int elementId, JPanel tournamentPanel){
        // Removing button
        tournamentPanel.remove(tournamentPanel.getComponentCount()-1);
        // Removing space before the button
        tournamentPanel.remove(tournamentPanel.getComponentCount()-1);
        createScrollButtons(elementId, tournamentPanel);
        tournamentPanel.revalidate(); tournamentPanel.repaint();
    }

    @Override
    void undoBtnClickedAction(){
        new HomePageGUI(userId, userType).createGUI();
        frame.setVisible(false);
    }

    public TournamentsScrollGUI(int userId, String userType){
        super(userId, userType);
        elementHeight = frameHeight/6;
        elementWidth = frameWidth*3/5;
        pageName = "Tournaments";
    }

    public static void main(String[] args) {
        new TournamentsScrollGUI(-1, "None").createGUI();
    }

}
