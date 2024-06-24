package bd2.gui.AddDataByForm;

import bd2.gui.SeeDataByScrolling.EmployeesScrollGUI;
import bd2.gui.SignUpLogIn.EmployeeFormGUI;
import bd2.logic.getInfoById;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.http.HttpResponse;
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

    // @TODO: fix default values
    HashMap<String, String> getEmployeeDataFromDB(int elementId) {
        HashMap<String, String> employeeMap = new HashMap<>();

        try {
            HttpResponse<String> response = getInfoById.getInfo(elementId, "employees/get_by_id/");
            JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();

            JsonArray jsonRidersArray = jsonObject.getAsJsonArray("employees");
            JsonObject rider = jsonRidersArray.get(0).getAsJsonObject();

            JsonObject member = rider.get("member").getAsJsonObject();
            employeeMap.put("Username", member.get("username").getAsString());
            employeeMap.put("Name", member.get("name").getAsString());
            employeeMap.put("Surname", member.get("surname").getAsString());
            employeeMap.put("Date of birth", member.get("date_of_birth").getAsString());
            employeeMap.put("Email", member.get("email").getAsString());
            employeeMap.put("Phone number", member.get("phone_number").getAsString());
            JsonObject address = member.get("address").getAsJsonObject();
            employeeMap.put("Country", address.get("country").getAsString());
            employeeMap.put("City", address.get("city").getAsString());
            employeeMap.put("Street", address.get("street").getAsString());
            employeeMap.put("Street number", address.get("street_no").getAsString());
            employeeMap.put("Postal Code", address.get("postal_code").getAsString());
            employeeMap.put("Licence", rider.get("licence").getAsJsonObject().get("licence_level").getAsString());
            employeeMap.put("Position", rider.get("position").getAsJsonObject().get("name").getAsString());
            employeeMap.put("Salary (PLN)", rider.get("salary").getAsString());
            employeeMap.put("Date employed", rider.get("date_employed").getAsString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employeeMap;
    }

    public static void main(String[] args) {
        new AddEmployeeGUI(-1, "None").createGUI();
    }
}
