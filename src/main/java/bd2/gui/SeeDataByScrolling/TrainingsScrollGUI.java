package bd2.gui.SeeDataByScrolling;

import bd2.gui.AddDataByForm.AddTrainingGUI;
import bd2.gui.components.ScrollElementButton;
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

public class TrainingsScrollGUI extends DataScrollTemplate {
    @Override
    protected void getElementsData() {
        String url;
        Map<String, Object> data = new HashMap<>();
        if (userType.equals("Rider")) {
            url = base_url + "riders/get_classes_for_rider/";
            data.put("id", userId);
        }
        else {
            url = base_url + "employees/get_by_id/";
            data.put("ids", List.of(userId));

            Pair<Integer, JsonObject> responseInternal = postMethod(url, data);
            if (responseInternal != null) {
                JsonObject rDataInternal = responseInternal.getSecond().get("employees").getAsJsonArray().get(0).getAsJsonObject();
                if (rDataInternal.get("position").getAsJsonObject().get("name").getAsString().equals("Owner")) {
                    url = base_url + "classes/get_all/";
                }
                else {
                    url = base_url + "employees/get_classes_for_employee/";
                    data.put("id", userId);
                }
            }
        }

        Pair<Integer, JsonObject> response = postMethod(url, data);
        if (response != null) {
            JsonObject responseData = response.getSecond();
            this.nrOfElements = responseData.getAsJsonArray("classes").size();
            this.fittingElementsIds = new Integer[this.nrOfElements];
            for (int i = 0; i < responseData.getAsJsonArray("classes").size(); i++) {
                this.fittingElementsIds[i] = responseData.getAsJsonArray("classes").get(i).getAsJsonObject().get("id").getAsInt();
            }
        }
        else {
            this.nrOfElements = 0;
            this.fittingElementsIds = new Integer[]{};
        }
    }

    @Override
    protected HashMap<String, String> getElementData(int elementId) {
        String url = base_url + "classes/get_by_id/";

        Map<String, Object> data = new HashMap<>();
        data.put("ids", List.of(elementId));

        Pair<Integer, JsonObject> response = postMethod(url, data);
        if (response != null) {
            JsonObject responseData = response.getSecond().getAsJsonArray("classes").get(0).getAsJsonObject();
            HashMap<String, String> dataInfo = new HashMap<>();
            dataInfo.put("type", responseData.get("type").getAsString());
            dataInfo.put("date", responseData.get("date").getAsString());
            String trainer = responseData.get("trainer").getAsJsonObject().get("member").getAsJsonObject().get("name").getAsString()
                    + " " + responseData.get("trainer").getAsJsonObject().get("member").getAsJsonObject().get("surname").getAsString();
            dataInfo.put("trainer", trainer);
            dataInfo.put("group", responseData.get("group").getAsJsonObject().get("name").getAsString());
            dataInfo.put("stable", responseData.get("stable").getAsJsonObject().get("name").getAsString());
            return dataInfo;
        }
        else {
            return null;
        }
    }

    @Override
    protected void addInfoToDataInfoPanel(int elementId, JPanel dataInfoPanel) {
        HashMap<String, String> dataInfo = getElementData(elementId);
        addJLabel("Training " + dataInfo.get("type"), Color.BLACK, fontBiggerBold, dataInfoPanel, elementWidth, elementHeight);
        addJLabel("hosted on " + dataInfo.get("date") + " in " + dataInfo.get("stable"), Color.BLACK, fontMiddle, dataInfoPanel, elementWidth, elementHeight);
        addJLabel("by " + dataInfo.get("trainer") + " for group " + dataInfo.get("group"), Color.BLACK, fontMiddle, dataInfoPanel, elementWidth, elementHeight);
    }

    protected void createScrollButtons(int elementId, JPanel dataPanel) {
        int buttonSize = scrollButtonSize;
        int buttonsGapSize = buttonSize / 3;
        dataPanel.add(Box.createRigidArea(new Dimension(buttonsGapSize, 0)));

        // If user is Employee with write permissions, show edit and delete buttons
        if (userType.equals("Employee") && doesEmployeeHaveWritePermissions()) {
            ScrollElementButton editButton = new ScrollElementButton("Edit", buttonSize, buttonSize, secondColor, secondColorDarker, fontButtons, true, elementId);
            editButton.addActionListener(actionEvent -> handleEditData(elementId));
            dataPanel.add(editButton);
            dataPanel.add(Box.createRigidArea(new Dimension(buttonsGapSize, 0)));

            ScrollElementButton removeButton = new ScrollElementButton("Delete", buttonSize, buttonSize, statusWrongLighter, statusWrong, fontButtons, true, elementId);
            removeButton.addActionListener(actionEvent -> handleRemoveData(elementId));
            dataPanel.add(removeButton);
        }
    }

    void switchRegisterButtons(int elementId, JPanel trainingPanel) {
        // Removing button
        trainingPanel.remove(trainingPanel.getComponentCount() - 1);
        // Removing space before the button
        trainingPanel.remove(trainingPanel.getComponentCount() - 1);
        createScrollButtons(elementId, trainingPanel);
        trainingPanel.revalidate();
        trainingPanel.repaint();
    }

    @Override
    protected void handleAddData() {
        new AddTrainingGUI(userId, userType).createGUI();
        frame.setVisible(false);
    }

    @Override
    protected void handleEditData(int elementId) {
        new AddTrainingGUI(userId, userType, elementId).createGUI();
        frame.setVisible(false);
    }

    @Override
    protected void handleRemoveData(int elementId) {
        System.out.println("Removing training with id: " + elementId);
        String[] options = {"No", "Yes"};
        int pickedOption = JOptionPane.showOptionDialog(null, "This action is irreversible. Are you sure you want to continue?",
                "Confirm action", 0, 0, null, options, "No");

        if (pickedOption == 1) {
            List<Integer> errorCodes = new ArrayList<>();
            String url = base_url + "classes/delete/";

            Map<String, Object> data = new HashMap<>();
            data.put("ids", List.of(elementId));

            Pair<Integer, JsonObject> response = postMethod(url, data);

            if (response != null) {
                JsonObject responseData = response.getSecond();
                int response_code = response.getFirst();
                if (response_code != 200) {
                    errorCodes.add(response_code);
                }
            } else {
                errorCodes.add(-1);
            }

            if (errorCodes.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Training deleted successfully.");
                new TrainingsScrollGUI(userId, userType).createGUI();
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

    public TrainingsScrollGUI(int userId, String userType) {
        super(userId, userType, "Trainings");
        elementHeight = frameHeight / 6;
        elementWidth = frameWidth * 3 / 5;
    }

    public static void main(String[] args) {
        new TrainingsScrollGUI(-1, "None").createGUI();
    }
}