package pap.gui;

import pap.logic.ErrorCodes;

import javax.swing.*;
import java.util.HashMap;
import java.util.List;

abstract public class AddOwnerPropertyFormTemplate extends FormGUITemplate {

    public AddOwnerPropertyFormTemplate(int userId, String userType) {
        super(userId, userType);
    }

    abstract List <Integer> validatePropertyData(HashMap<String, String> textFieldsValues);

    abstract void addProperty(HashMap<String, String> textFieldsValues);


    @Override
    void finishFormButtonClicked(){
        // Get values
        HashMap<String, String> formFieldsValues = getFieldValues();
        // Validate values
        List<Integer> errorCodes = validatePropertyData(formFieldsValues);
        // Add property
        if (errorCodes.isEmpty()) {
            addProperty(formFieldsValues);
            JOptionPane.showMessageDialog(frame, "Success! Property added!");
            new HomePageGUI(userId, userType).createGUI();
            frame.setVisible(false);
        }
        // Errors occured, display them on screen
        else {
            String statusLabelText = "<html>"; String spacingCharacter = "<br/>";
            if (errorCodes.size() > 10) spacingCharacter = " | ";
            for (Integer code : errorCodes) {
                statusLabelText = statusLabelText + ErrorCodes.getErrorDescription(code) + spacingCharacter;
            }
            statusLabelText = statusLabelText + "</html>";
            statusLabel.setText(statusLabelText);
            statusLabel.setForeground(statusWrong);
        }
    }

    @Override
    void undoBtnClickedAction(){
        new HomePageGUI(userId, userType).createGUI();
        frame.setVisible(false);
    }

}
