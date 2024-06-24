package bd2.gui.AddDataByForm;

import bd2.gui.SignUpLogIn.FormGUITemplate;
import kotlin.Pair;

import javax.swing.*;
import java.util.HashMap;

abstract public class AddDataTemplate extends FormGUITemplate {

    protected abstract Pair<Integer, String> addToDB(HashMap<String, String> textFieldsValues);

    @Override
    protected void setFinishFormButtonText() {
        finishFormButtonText = "Add";
    }

    @Override
    protected void finishFormButtonClicked(){
        HashMap<String, String> formFieldsValues = getFieldValues();
        Pair<Integer, String> response = addToDB(formFieldsValues);
        if (response.getFirst() == 200 || response.getFirst() == 201) {
            JOptionPane.showMessageDialog(frame, "Success! Added to database.");
            undoBtnClickedAction();
        }
        else {
            JOptionPane.showMessageDialog(frame, "Error! " + response.getSecond());
        }
    }

    public AddDataTemplate(int userId, String userType, String pageName) {
        super(userId, userType);
        this.pageName = pageName;
    }

}
