package bd2.gui.SeeDataByScrolling;

import bd2.gui.AddDataByForm.AddEmployeeGUI;
import bd2.logic.ErrorCodes;
import com.google.gson.JsonObject;
import kotlin.Pair;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static bd2.DBRequests.base_url;
import static bd2.DBRequests.postMethod;

public class EmployeesScrollGUI extends DataScrollTemplate {

    @Override
    protected void getElementsData() {
        String url = base_url + "employees/get_active_employees/";

        Pair<Integer, JsonObject> response = postMethod(url, new HashMap<>());
        if (response != null) {
            JsonObject responseData = response.getSecond();
            this.nrOfElements = responseData.getAsJsonArray("employees").size();
            this.fittingElementsIds = new Integer[this.nrOfElements];
            for (int i = 0; i < responseData.getAsJsonArray("employees").size(); i++) {
                this.fittingElementsIds[i] = responseData.getAsJsonArray("employees").get(i).getAsJsonObject().get("id").getAsInt();
            }
        }
        else {
            this.nrOfElements = 0;
            this.fittingElementsIds = new Integer[]{};
        }
    }

    @Override
    protected HashMap<String, String> getElementData(int elementId) {
        String url = base_url + "employees/get_by_id/";

        Map<String, Object> data = new HashMap<>();
        data.put("ids", List.of(elementId));

        Pair<Integer, JsonObject> response = postMethod(url, data);
        if (response != null) {
            JsonObject responseData = response.getSecond().getAsJsonArray("employees").get(0).getAsJsonObject();
            HashMap<String, String> dataInfo = new HashMap<>();
            JsonObject member = responseData.get("member").getAsJsonObject();
            dataInfo.put("name", member.get("name").getAsString());
            dataInfo.put("surname", member.get("surname").getAsString());
            JsonObject position = responseData.get("position").getAsJsonObject();
            dataInfo.put("position", position.get("name").getAsString());
            dataInfo.put("salary", responseData.get("salary").getAsString());
            dataInfo.put("date_employed", responseData.get("date_employed").getAsString());
            dataInfo.put("username", member.get("username").getAsString());
            dataInfo.put("date_of_birth", member.get("date_of_birth").getAsString());
            dataInfo.put("email", member.get("email").getAsString());
            dataInfo.put("phone_number", member.get("phone_number").getAsString());
            JsonObject address = member.get("address").getAsJsonObject();
            dataInfo.put("address", "ul. " + address.get("street").getAsString() + " " + address.get("street_no").getAsString() + ", " + address.get("city").getAsString() + " " + address.get("postal_code").getAsString() + ", " + address.get("country").getAsString());
            return dataInfo;
        }
        else {
            return null;
        }
    }

    @Override
    protected void addInfoToDataInfoPanel (int elementId, JPanel dataInfoPanel) {
        HashMap<String, String> dataInfo = getElementData(elementId);
        addJLabel(dataInfo.get("name") + " " + dataInfo.get("surname") + " (" + dataInfo.get("position") + ")", Color.BLACK, fontBiggerBold, dataInfoPanel, elementWidth, elementHeight);
        addJLabel("Salary: " + dataInfo.get("salary") + ", Employed at: " + dataInfo.get("date_employed") + ", System username: " + dataInfo.get("username"), Color.BLACK, fontSmaller, dataInfoPanel, elementWidth, elementHeight);
        addJLabel("Date of birth: " + dataInfo.get("date_of_birth") + ", Contact: " + dataInfo.get("email") + ", " + dataInfo.get("phone_number"), Color.BLACK, fontSmaller, dataInfoPanel, elementWidth, elementHeight);
        addJLabel(dataInfo.get("address"), Color.BLACK, fontSmaller, dataInfoPanel, elementWidth, elementHeight);
    }

    @Override
    protected void handleAddData() {
        new AddEmployeeGUI(userId, userType).createGUI();
        frame.setVisible(false);
    }

    @Override
    protected void handleEditData(int elementId) {
        new AddEmployeeGUI(userId, userType, elementId).createGUI();
        frame.setVisible(false);
    }

    @Override
    protected void handleRemoveData(int elementId) {
        String[] options = {"No", "Yes"};
        int pickedOption = JOptionPane.showOptionDialog(null, "This action is irreversible. Are you sure you want to continue?",
                "Confirm action", 0, 0, null, options, "No");

        if (pickedOption == 1) {
            List<Integer> errorCodes = new ArrayList<>();
            String url = base_url + "employees/deactivate_account/";

            Map<String, Object> data = new HashMap<>();
            data.put("id", elementId);

            Pair<Integer, JsonObject> response = postMethod(url, data);

            if (response != null) {
                JsonObject responseData = response.getSecond();
                int response_code = response.getFirst();
                if (response_code != 200) {
                    errorCodes.add(response_code);
                }
            }
            else {
                errorCodes.add(-1);
            }

            if (errorCodes.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Employee deleted successfully.");
                new EmployeesScrollGUI(userId, userType).createGUI();
                frame.setVisible(false);
            } else {
                StringBuilder errorText = new StringBuilder();
                for (Integer errorCode : errorCodes) {
                    errorText.append(ErrorCodes.getErrorDescription(errorCode)).append(" ");
                }

                JOptionPane.showMessageDialog(frame, errorText.toString());
            }
        }
    }

    public EmployeesScrollGUI(int userId, String userType){
        super(userId, userType, "Employees");
    }

    public static void main(String[] args) {
        new EmployeesScrollGUI(-1, "None").createGUI();
    }

}
