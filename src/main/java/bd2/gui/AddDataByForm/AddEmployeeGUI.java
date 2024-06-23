package bd2.gui.AddDataByForm;

import bd2.gui.SeeDataByScrolling.EmployeesScrollGUI;
import bd2.gui.SignUpLogIn.EmployeeFormGUI;

public class AddEmployeeGUI extends EmployeeFormGUI {

    @Override
    protected void undoBtnClickedAction(){
        new EmployeesScrollGUI(userId, userType).createGUI();
        frame.setVisible(false);
    }

    public AddEmployeeGUI(int userId, String userType) {
        super(userId, userType);
    }

    public static void main(String[] args) {
        new AddEmployeeGUI(-1, "None").createGUI();
    }
}
