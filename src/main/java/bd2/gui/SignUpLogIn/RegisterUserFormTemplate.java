package bd2.gui.SignUpLogIn;

import kotlin.Pair;

import javax.swing.*;

import java.util.HashMap;

abstract public class RegisterUserFormTemplate extends FormGUITemplate {

    public RegisterUserFormTemplate(int userId, String userType) {
        super(userId, userType);
    }

    protected abstract Pair<Integer, String> createUser(HashMap<String, String> textFieldsValues);

    @Override
    protected void setFinishFormButtonText() {
        finishFormButtonText = "Register";
    }

    @Override
    protected void finishFormButtonClicked() {
        HashMap<String, String> formFieldsValues = getFieldValues();
        Pair<Integer, String> response = createUser(formFieldsValues);
        if (response.getFirst() == 200 || response.getFirst() == 201) {
            JOptionPane.showMessageDialog(frame, "Success! Created user!");
            undoBtnClickedAction();
        }
        else {
            JOptionPane.showMessageDialog(frame, "Error! " + response.getSecond());
        }
    }

    @Override
    protected void undoBtnClickedAction(){
        new ChooseAccountTypeGUI(-1, "None").createGUI();
        frame.setVisible(false);
    }

}
