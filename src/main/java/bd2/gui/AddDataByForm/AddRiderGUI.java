package bd2.gui.AddDataByForm;

import bd2.gui.SeeDataByScrolling.RidersScrollGUI;
import bd2.gui.SignUpLogIn.RiderFormGUI;

import java.time.LocalDate;
import java.util.HashMap;

public class AddRiderGUI extends RiderFormGUI {

    @Override
    protected void undoBtnClickedAction(){
        new RidersScrollGUI(userId, userType).createGUI();
        frame.setVisible(false);
    }

    public AddRiderGUI(int userId, String userType) {
        super(userId, userType);
    }

    public AddRiderGUI(int userId, String userType, int editedElementId) {
        super(userId, userType);
        this.editedElementId = editedElementId;
        this.editMode = true;
    }

    @Override
    protected void createCustomGUI() {
        super.createCustomGUI();
        if (this.editMode) {
            populateFieldValues(getRiderDataFromDB(this.editedElementId));
        }
    }

    // [MOCK]
    HashMap<String, String> getRiderDataFromDB(int elementId) {
        HashMap<String, String> myMap = new HashMap<String, String>() {{
            put("Name", "Kasia");
            put("Surname", "Konik");
            put("Date of birth", "2003-11-02");
        }};
        return myMap;
    }

    public static void main(String[] args) {
        new AddRiderGUI(-1, "None").createGUI();
    }
}
