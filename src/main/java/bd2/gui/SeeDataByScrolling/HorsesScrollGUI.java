package bd2.gui.SeeDataByScrolling;

import bd2.gui.AddDataByForm.AddHorseGUI;
import bd2.gui.components.ScrollElementButton;
import bd2.logic.ErrorCodes;
import com.google.gson.JsonObject;
import kotlin.Pair;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

import static bd2.DBRequests.base_url;
import static bd2.DBRequests.postMethod;

public class HorsesScrollGUI extends DataScrollTemplate {

	boolean[] doesUserChooseThisHorse = {true, false, false, true, false, true, true};

    @Override
    protected void getElementsData() {
        String url = base_url + "horses/get_all/";

        Pair<Integer, JsonObject> response = postMethod(url, new HashMap<>());
        if (response != null) {
            JsonObject responseData = response.getSecond();
            this.nrOfElements = responseData.getAsJsonArray("horses").size();
            this.fittingElementsIds = new Integer[this.nrOfElements];
            for (int i = 0; i < responseData.getAsJsonArray("horses").size(); i++) {
                this.fittingElementsIds[i] = responseData.getAsJsonArray("horses").get(i).getAsJsonObject().get("id").getAsInt();
            }
        }
        else {
            this.nrOfElements = 0;
            this.fittingElementsIds = new Integer[]{};
        }
    }

    @Override
    protected HashMap<String, String> getElementData(int elementId) {
        String url = base_url + "horses/get_by_id/";

        Map<String, Object> data = new HashMap<>();
        data.put("ids", List.of(elementId));

        Pair<Integer, JsonObject> response = postMethod(url, data);
        if (response != null) {
            JsonObject responseData = response.getSecond().getAsJsonArray("horses").get(0).getAsJsonObject();
            HashMap<String, String> dataInfo = new HashMap<>();
            dataInfo.put("bread", responseData.get("breed").getAsString());
            dataInfo.put("height", responseData.get("height").getAsString());
            dataInfo.put("color", responseData.get("color").getAsString());
            dataInfo.put("eye_color", responseData.getAsJsonObject().get("eye_color").getAsString());
            dataInfo.put("age", responseData.getAsJsonObject().get("age").getAsString());
            dataInfo.put("origin", responseData.getAsJsonObject().get("origin").getAsString());
            dataInfo.put("hairstyle", responseData.getAsJsonObject().get("hairstyle").getAsString());
            return dataInfo;
        }
        else {
            return null;
        }
    }

    @Override
    protected void addInfoToDataInfoPanel (int elementId, JPanel dataInfoPanel) {
        HashMap<String, String> dataInfo = getElementData(elementId);
        addJLabel("Horse - " + dataInfo.get("bread") + " from " + dataInfo.get("origin"), Color.BLACK, fontBiggerBold, dataInfoPanel, elementWidth, elementHeight);
        addJLabel("Age: " + dataInfo.get("age") + " years, Height: " + dataInfo.get("height") + " cm", Color.BLACK, fontMiddle, dataInfoPanel, elementWidth, elementHeight);
        addJLabel("Color: " + dataInfo.get("color") + ", Eye color: " + dataInfo.get("eye_color") + ", Hairstyle: " + dataInfo.get("hairstyle"), Color.BLACK, fontMiddle, dataInfoPanel, elementWidth, elementHeight);
    }

	protected void createScrollButtons(int elementId, JPanel dataPanel) {

        int buttonSize = scrollButtonSize; buttonsGapSize = buttonSize/3;
        dataPanel.add(Box.createRigidArea(new Dimension(buttonsGapSize,0)));

        //[MOCK] normalnie nie byloby tablicy tylko modyfikacja danych Ridera
        int indexOfElementInFittingElements = Arrays.asList(this.fittingElementsIds).indexOf(elementId);

        if (userType.equals("Rider")) {
            if (this.doesUserChooseThisHorse[indexOfElementInFittingElements])
            {
                ScrollElementButton unregisterButton = new ScrollElementButton("Cancel", buttonSize, buttonSize, statusWrongLighter, statusWrong, fontButtons, true, elementId);
                unregisterButton.addActionListener(actionEvent -> {
                    //[MOCK]
                    this.doesUserChooseThisHorse[indexOfElementInFittingElements] = false;
                    JOptionPane.showMessageDialog(frame, "Successfully cancelled trainings.");
                    switchRegisterButtons(elementId, dataPanel);
                });
                dataPanel.add(unregisterButton);
            } else {
                ScrollElementButton registerButton = new ScrollElementButton("Register", buttonSize, buttonSize, secondColor, secondColorDarker, fontButtons, true, elementId);
                registerButton.addActionListener(actionEvent -> {
                    //[MOCK]
                    this.doesUserChooseThisHorse[indexOfElementInFittingElements] = true;
                    JOptionPane.showMessageDialog(frame, "Successfully registered for trainings!");
                    switchRegisterButtons(elementId, dataPanel);
                });
                dataPanel.add(registerButton);
            }
        } else if (userType.equals("Employee") && doesEmployeeHaveWritePermissions()) {
            ScrollElementButton editButton = new ScrollElementButton("Edit", buttonSize, buttonSize, secondColor, secondColorDarker, fontButtons, true, elementId);
            editButton.addActionListener(actionEvent -> handleEditData(elementId));
            dataPanel.add(editButton);
            dataPanel.add(Box.createRigidArea(new Dimension(buttonsGapSize,0)));

            ScrollElementButton removeButton = new ScrollElementButton("Delete", buttonSize, buttonSize, statusWrongLighter, statusWrong, fontButtons, true, elementId);
            removeButton.addActionListener(actionEvent -> handleRemoveData(elementId));
            dataPanel.add(removeButton);
        }
    }

    void switchRegisterButtons(int elementId, JPanel trainingPanel){
        // Removing button
        trainingPanel.remove(trainingPanel.getComponentCount()-1);
        // Removing space before the button
        trainingPanel.remove(trainingPanel.getComponentCount()-1);
        createScrollButtons(elementId, trainingPanel);
        trainingPanel.revalidate(); trainingPanel.repaint();
    }

    @Override
    protected void handleAddData() {
        new AddHorseGUI(userId, userType).createGUI();
        frame.setVisible(false);
    }

    @Override
    protected void handleEditData(int elementId) {
        new AddHorseGUI(userId, userType, elementId).createGUI();
        frame.setVisible(false);
    }

    @Override
    protected void handleRemoveData(int elementId) {
        String[] options = {"No", "Yes"};
        int pickedOption = JOptionPane.showOptionDialog(null, "This action is irreversible. Are you sure you want to continue?",
                "Confirm action", 0, 0, null, options, "No");

        if (pickedOption == 1) {
            List<Integer> errorCodes = new ArrayList<>();
            String url = base_url + "horses/delete/";

            Map<String, Object> data = new HashMap<>();
            data.put("ids", List.of(elementId));

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
                JOptionPane.showMessageDialog(frame, "Horse deleted successfully.");
                new HorsesScrollGUI(userId, userType).createGUI();
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

    public HorsesScrollGUI(int userId, String userType){
        super(userId, userType, "Horses");
    }

    public static void main(String[] args) {
        new HorsesScrollGUI(-1, "None").createGUI();
    }

}
