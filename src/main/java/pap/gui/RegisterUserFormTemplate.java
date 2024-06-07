package pap.gui;

import pap.logic.ErrorCodes;

import javax.swing.*;
import java.util.HashMap;
import java.util.List;

abstract public class RegisterUserFormTemplate extends FormGUITemplate {

    public RegisterUserFormTemplate(int userId, String userType) {
        super(userId, userType);
    }

    abstract List <Integer> validateCredentials(HashMap<String, String> textFieldsValues);

    abstract void createUser(HashMap<String, String> textFieldsValues);

    @Override
    void setFinishFormButtonText() {
        finishFormButtonText = "Register";
    }

    @Override
    void finishFormButtonClicked(){
        // Get values
        HashMap<String, String> formFieldsValues = getFieldValues();
        // Validate values
        List<Integer> errorCodes = validateCredentials(formFieldsValues);
        // Create user and open Home Page
        if (errorCodes.isEmpty()) {
            createUser(formFieldsValues);
            JOptionPane.showMessageDialog(frame, "Success! Created user!");
            new LogInGUI(-1, "None").createGUI();
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
        new ChooseAccountTypeGUI(-1, "None").createGUI();
        frame.setVisible(false);
    }

}
