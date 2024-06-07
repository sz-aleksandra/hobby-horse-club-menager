package pap.gui.AddDataByForm;

import pap.gui.HomePageGUI;
import pap.gui.SignUpLogIn.FormGUITemplate;
import pap.logic.ErrorCodes;

import javax.swing.*;
import java.util.HashMap;
import java.util.List;

abstract public class AddOwnerPropertyFormTemplate extends FormGUITemplate {

    public AddOwnerPropertyFormTemplate(int userId, String userType) {
        super(userId, userType);
    }

    protected abstract List <Integer> validatePropertyData(HashMap<String, String> textFieldsValues);

    protected abstract void addProperty(HashMap<String, String> textFieldsValues);


    @Override
    protected void finishFormButtonClicked(){
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
    protected void undoBtnClickedAction(){
        new HomePageGUI(userId, userType).createGUI();
        frame.setVisible(false);
    }

}
