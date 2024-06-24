package bd2.gui.SeeDataByScrolling;

import bd2.gui.AddDataByForm.AddEmployeeGUI;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class EmployeesScrollGUI extends DataScrollTemplate {

    //[MOCK]
    @Override
    protected void getElementsData() {
        this.fittingElementsIds = new Integer[]{1,2,3,4,5,6,7,8,9,10,11,12};
        this.nrOfElements = fittingElementsIds.length;
    }

    // [MOCK]
    @Override
    protected HashMap<String, String> getElementData(int elementId) {
        HashMap<String, String> dataInfo = new HashMap<>();
        dataInfo.put("name", "Adam");
        dataInfo.put("surname", "Kaczka");
        dataInfo.put("position", "Trainer");
        dataInfo.put("salary", "3000");
        dataInfo.put("date_employed", "2020-01-15");
        dataInfo.put("username", "akaczka");
        dataInfo.put("date_of_birth", "1985-04-12");
        dataInfo.put("email", "adam.kaczka@email.com");
        dataInfo.put("phone_number", "+48000123456");
        dataInfo.put("address", "ul. Marszałkowska 10, Warszawa 00-001, Polska");

        return dataInfo;
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

    }

    @Override
    protected void handleRemoveData(int elementId) {

    }

    public EmployeesScrollGUI(int userId, String userType){
        super(userId, userType, "Employees");
    }

    public static void main(String[] args) {
        new EmployeesScrollGUI(-1, "None").createGUI();
    }

}
