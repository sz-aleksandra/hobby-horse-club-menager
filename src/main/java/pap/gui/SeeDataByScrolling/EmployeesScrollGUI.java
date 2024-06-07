package pap.gui.SeeDataByScrolling;

import pap.gui.HomePageGUI;
import pap.gui.components.ScrollElementButton;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class EmployeesScrollGUI extends ScrollGUITemplate {

    //[MOCK]
    protected void getElementsData() {
        this.fittingElementsIds = new Integer[]{1,2,3,4,5,6,7,8,9};
        this.nrOfElements = fittingElementsIds.length;
    }

    // [MOCK]
    protected HashMap<String, String> getElementData(int elementId) {
        HashMap<String, String> employeeInfo = new HashMap<String, String>();
        employeeInfo.put("name", "Adam");
        employeeInfo.put("surname", "Kaczka");
        employeeInfo.put("position", "Trainer");
        employeeInfo.put("salary", "3000");
        employeeInfo.put("date_employed", "2020-01-15");
        employeeInfo.put("username", "akaczka");
        employeeInfo.put("date_of_birth", "1985-04-12");
        employeeInfo.put("email", "adam.kaczka@email.com");
        employeeInfo.put("phone_number", "+48000123456");
        employeeInfo.put("address", "ul. Marszałkowska 10, Warszawa 00-001, Polska");

        return employeeInfo;
    }

    protected JPanel createScrollElement(int elementId) {

        HashMap<String, String> employeeInfo = getElementData(elementId);

        JPanel employeePanel = new JPanel();
        employeePanel.setBackground(neutralBlue);
        employeePanel.setLayout(new BoxLayout(employeePanel, BoxLayout.LINE_AXIS));
        employeePanel.setPreferredSize(new Dimension(frameWidth, elementHeight));
        employeePanel.setMaximumSize(new Dimension(frameWidth, elementHeight));
        employeePanel.add(Box.createRigidArea(new Dimension(frameWidth/20,0)));

        JPanel employeeInfoPanel = new JPanel();
        employeeInfoPanel.setBackground(neutralGray);
        employeeInfoPanel.setLayout(new BoxLayout(employeeInfoPanel, BoxLayout.PAGE_AXIS));
        employeeInfoPanel.setPreferredSize(new Dimension(elementWidth, elementHeight));
        employeeInfoPanel.setMaximumSize(new Dimension(elementWidth, elementHeight));
        addJLabel(employeeInfo.get("name") + " " + employeeInfo.get("surname") + " (" + employeeInfo.get("position") + ")", Color.BLACK, fontBiggerBold, employeeInfoPanel, elementWidth, elementHeight);
        addJLabel("Salary: " + employeeInfo.get("salary") + ", Employed at: " + employeeInfo.get("date_employed") + ", System username: " + employeeInfo.get("username"), Color.BLACK, fontSmaller, employeeInfoPanel, elementWidth, elementHeight);
        addJLabel("Date of birth: " + employeeInfo.get("date_of_birth") + ", Contact: " + employeeInfo.get("email") + ", " + employeeInfo.get("phone_number"), Color.BLACK, fontSmaller, employeeInfoPanel, elementWidth, elementHeight);
        addJLabel(employeeInfo.get("address"), Color.BLACK, fontSmaller, employeeInfoPanel, elementWidth, elementHeight);
        employeePanel.add(employeeInfoPanel);

        return employeePanel;
    }

    protected void createScrollButtons(int elementId, JPanel employeePanel) {

        int buttonSize = scrollButtonSize; int gapSize = buttonSize/3;
        employeePanel.add(Box.createRigidArea(new Dimension(gapSize,0)));

        ScrollElementButton editButton = new ScrollElementButton("Edit", buttonSize, buttonSize, secondColor, secondColorDarker, fontButtons, true, elementId);
        editButton.addActionListener(actionEvent -> {
            //
        });
        employeePanel.add(editButton);

    }

    @Override
    protected void undoBtnClickedAction(){
        new HomePageGUI(userId, userType).createGUI();
        frame.setVisible(false);
    }

    public EmployeesScrollGUI(int userId, String userType){
        super(userId, userType);
        elementHeight = frameHeight/6;
        elementWidth = frameWidth*7/10;
        pageName = "Employees";
    }

    public static void main(String[] args) {
        new EmployeesScrollGUI(-1, "None").createGUI();
    }

}
