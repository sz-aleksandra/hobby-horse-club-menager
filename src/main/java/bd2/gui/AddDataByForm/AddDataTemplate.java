package bd2.gui.AddDataByForm;

import bd2.gui.SignUpLogIn.FormGUITemplate;
import javax.swing.*;
import java.util.HashMap;
import java.util.List;

abstract public class AddDataTemplate extends FormGUITemplate {

    protected abstract List <Integer> validateInput(HashMap<String, String> textFieldsValues);

    protected abstract void addToDB(HashMap<String, String> textFieldsValues);

    @Override
    protected void setFinishFormButtonText() {
        finishFormButtonText = "Add";
    }

    @Override
    protected void finishFormButtonClicked(){

        HashMap<String, String> formFieldsValues = getFieldValues();
        List<Integer> errorCodes = validateInput(formFieldsValues);
        if (errorCodes.isEmpty()) {
            addToDB(formFieldsValues);
            JOptionPane.showMessageDialog(frame, "Success! Added to database.");
            undoBtnClickedAction();
        }
        else {
            JOptionPane.showMessageDialog(frame, "There are errors in your input.");
        }
    }

    public AddDataTemplate(int userId, String userType, String pageName) {
        super(userId, userType);
        this.pageName = pageName;
    }

}
