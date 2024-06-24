package bd2.gui.SignUpLogIn;

import java.time.Year;
import java.util.HashMap;

import bd2.logic.addEmployee;
import kotlin.Pair;

public class EmployeeFormGUI extends RegisterUserFormTemplate {

    public EmployeeFormGUI(int userId, String userType) {
        super(userId, userType);
        pageName = "Create Employee Account";
    }

    @Override
    protected String[] getFieldLabels() {
        String[] fieldLabels = {"Username", "Password", "Name", "Surname", "Date of birth", "Email", "Phone number", "Country", "City", "Street", "Street number", "Postal Code", "Licence", "Position", "Salary (PLN)", "Date employed"};
        return fieldLabels;
    }

    @Override
    protected String[] getFieldTypes() {
        String[] fieldTypes = {"text", "password", "text", "text", "comboBoxDate", "text", "text", "text", "text", "text", "text", "text", "comboBoxString", "comboBoxString", "text", "comboBoxDate"};
        return fieldTypes;
    }

    @Override
    protected Object[] getFieldParameters() {

        Integer[] days = new Integer[31];
        for (int i=0; i < days.length; i++) {
            days[i] = i+1;
        }
        Integer[] months = new Integer[12];
        for (int i=0; i < months.length; i++) {
            months[i] = i+1;
        }

        int baseYear = Year.now().getValue();
        Integer[] years = new Integer[110];
        for (int i=0; i < years.length; i++) {
            years[i] = baseYear-i;
        }

        String[][] positions = new String[][]{{"Trainer", "Senior Trainer","Instructor","Senior Instructor","Director of Training","Veterinarian","Caretaker", "Administrator","Coordinator","Technician","IT Specialist","Marketer","HR"}};

        Object[] fieldParameters = {15, 15, 15, 15, new Integer[][]{days, months, years}, 20, 10, 15, 15, 6, 15, 6, new String[][]{{"No Coaching Licence", "Coaching Licence Level 1", "Coaching Licence Level 2", "Coaching Licence Level 3", "Coaching Licence Level 4", "Coaching Licence Level 5"}}, positions, 10, new Integer[][]{days, months, years}};

        return fieldParameters;
    }

    @Override
    protected Pair<Integer, String> createUser(HashMap<String, String> textFieldsValues) {
		return addEmployee.add(textFieldsValues);
    }

    public static void main(String[] args) {
        new EmployeeFormGUI(-1, "None").createGUI();
    }
}
