package bd2.gui.AddDataByForm;

import bd2.gui.SeeDataByScrolling.RidersScrollGUI;
import bd2.gui.SignUpLogIn.RiderFormGUI;

public class AddRiderGUI extends RiderFormGUI {

    @Override
    protected void undoBtnClickedAction(){
        new RidersScrollGUI(userId, userType).createGUI();
        frame.setVisible(false);
    }

    public AddRiderGUI(int userId, String userType) {
        super(userId, userType);
    }

    public static void main(String[] args) {
        new AddRiderGUI(-1, "None").createGUI();
    }
}
