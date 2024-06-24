package bd2.gui.AddDataByForm;

import bd2.gui.SeeDataByScrolling.EmployeesScrollGUI;
import bd2.gui.SignUpLogIn.EmployeeFormGUI;
import com.google.gson.JsonObject;
import kotlin.Pair;

import java.util.HashMap;

import static bd2.DBRequests.base_url;

import java.util.HashMap;

public class AddEmployeeGUI extends EmployeeFormGUI {

    @Override
    protected void undoBtnClickedAction(){
        new EmployeesScrollGUI(userId, userType).createGUI();
        frame.setVisible(false);
    }

    public AddEmployeeGUI(int userId, String userType) {
        super(userId, userType);
    }

    public AddEmployeeGUI(int userId, String userType, int editedElementId) {
        super(userId, userType);
        this.editedElementId = editedElementId;
        this.editMode = true;
    }

    @Override
    protected void createCustomGUI() {
        super.createCustomGUI();
        if (this.editMode) {
            populateFieldValues(getEmployeeDataFromDB(this.editedElementId));
        }
    }

    // [MOCK]
    HashMap<String, String> getEmployeeDataFromDB(int elementId) {
        HashMap<String, String> myMap = new HashMap<String, String>() {{
            put("Name", "Adam");
            put("Surname", "Kowalski");
        }};
        return myMap;
    }

    public static void main(String[] args) {
        new AddEmployeeGUI(-1, "None").createGUI();
    }
}
