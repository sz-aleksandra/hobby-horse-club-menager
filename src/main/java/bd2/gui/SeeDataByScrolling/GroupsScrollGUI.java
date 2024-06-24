package bd2.gui.SeeDataByScrolling;

import bd2.gui.AddDataByForm.AddGroupGUI;
import bd2.gui.components.ScrollElementButton;
import bd2.logic.getInfoById;
import kotlin.Pair;

import javax.swing.*;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.awt.*;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import static bd2.DBRequests.base_url;
import static bd2.DBRequests.postMethod;

public class GroupsScrollGUI extends DataScrollTemplate {
	boolean[] doesUserChooseThisGroup;
	int groupId = 0;
	
    @Override
    protected void getElementsData() {
		try {
			HttpResponse<String> response = getInfoById.getInfo(userId, "riders/get_by_id/");
				
			groupId = JsonParser.parseString(response.body()).getAsJsonObject()
					.getAsJsonArray("riders").get(0).getAsJsonObject().getAsJsonObject("group").get("id").getAsInt();
		} catch (Exception e) {
			e.printStackTrace();
		}
      
		String url = base_url + "groups/get_all/";

        Pair<Integer, JsonObject> response = postMethod(url, new HashMap<>());
        if (response != null) {
            JsonObject responseData = response.getSecond();
            this.nrOfElements = responseData.getAsJsonArray("groups").size();
            this.fittingElementsIds = new Integer[this.nrOfElements];
            for (int i = 0; i < responseData.getAsJsonArray("groups").size(); i++) {
                this.fittingElementsIds[i] = responseData.getAsJsonArray("groups").get(i).getAsJsonObject().get("id").getAsInt();
            }
        }
        else {
            this.nrOfElements = 0;
            this.fittingElementsIds = new Integer[]{};
        }

		doesUserChooseThisGroup = new boolean[this.fittingElementsIds.length];
		if (groupId != 0) {
			doesUserChooseThisGroup[groupId - 1] = true;
		}
    }

    // [MOCK]
    @Override
    protected HashMap<String, String> getElementData(int elementId) {
		String url = base_url + "groups/get_by_id/";

        Map<String, Object> data = new HashMap<>();
        data.put("ids", List.of(elementId));

        Pair<Integer, JsonObject> response = postMethod(url, data);
        if (response != null) {
            JsonObject responseData = response.getSecond().getAsJsonArray("groups").get(0).getAsJsonObject();
            HashMap<String, String> dataInfo = new HashMap<>();
            dataInfo.put("name", responseData.get("name").getAsString());
            dataInfo.put("max_group_members", responseData.get("max_group_members").getAsString());
            return dataInfo;
        }
        else {
            return null;
        }
    }

    @Override
    protected void addInfoToDataInfoPanel (int elementId, JPanel dataInfoPanel) {
        HashMap<String, String> dataInfo = getElementData(elementId);
        addJLabel("Group " + dataInfo.get("name"), Color.BLACK, fontBiggerBold, dataInfoPanel, elementWidth, elementHeight);
        //addJLabel("Group members: " + dataInfo.get("group_members"), Color.BLACK, fontMiddle, dataInfoPanel, elementWidth, elementHeight);
        addJLabel("Max group members: " + dataInfo.get("max_group_members"), Color.BLACK, fontMiddle, dataInfoPanel, elementWidth, elementHeight);
    }

    protected void createScrollButtons(int elementId, JPanel dataPanel) {

        int buttonSize = scrollButtonSize; buttonsGapSize = buttonSize/3;
        dataPanel.add(Box.createRigidArea(new Dimension(buttonsGapSize,0)));

        //[MOCK] normalnie nie byloby tablicy tylko modyfikacja danych Ridera
        int indexOfElementInFittingElements = Arrays.asList(this.fittingElementsIds).indexOf(elementId);

        if (userType.equals("Rider")) {
            if (this.doesUserChooseThisGroup[indexOfElementInFittingElements])
            {
                ScrollElementButton unregisterButton = new ScrollElementButton("Cancel", buttonSize, buttonSize, statusWrongLighter, statusWrong, fontButtons, true, elementId);
                unregisterButton.addActionListener(actionEvent -> {
                    //[MOCK]
                    this.doesUserChooseThisGroup[indexOfElementInFittingElements] = false;
                    JOptionPane.showMessageDialog(frame, "Successfully cancelled trainings.");
                    switchRegisterButtons(elementId, dataPanel);
                });
                dataPanel.add(unregisterButton);
            } else {
                ScrollElementButton registerButton = new ScrollElementButton("Register", buttonSize, buttonSize, secondColor, secondColorDarker, fontButtons, true, elementId);
                registerButton.addActionListener(actionEvent -> {
                    //[MOCK]
                    this.doesUserChooseThisGroup[indexOfElementInFittingElements] = true;
                    JOptionPane.showMessageDialog(frame, "Successfully registered for trainings!");
                    switchRegisterButtons(elementId, dataPanel);
                });
                dataPanel.add(registerButton);
            }
        } else if (userType.equals("Employee") && doesEmployeeHaveWritePermissions()){
            ScrollElementButton editButton = new ScrollElementButton("Edit", buttonSize, buttonSize, secondColor, secondColorDarker, fontButtons, true, elementId);
            editButton.addActionListener(actionEvent -> handleEditData(elementId));
            dataPanel.add(editButton);
            dataPanel.add(Box.createRigidArea(new Dimension(buttonsGapSize,0)));

            ScrollElementButton removeButton = new ScrollElementButton("Delete", buttonSize, buttonSize, statusWrongLighter, statusWrong, fontButtons, true, elementId);
            editButton.addActionListener(actionEvent -> handleEditData(elementId));
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
        new AddGroupGUI(userId, userType).createGUI();
        frame.setVisible(false);
    }

    @Override
    protected void handleEditData(int elementId) {
        new AddGroupGUI(userId, userType, elementId).createGUI();
        frame.setVisible(false);
    }

    @Override
    protected void handleRemoveData(int elementId) {

    }

    public GroupsScrollGUI(int userId, String userType){
        super(userId, userType, "Groups");
        elementHeight = frameHeight/6;
        elementWidth = frameWidth*3/5;
    }

    public static void main(String[] args) {
        new GroupsScrollGUI(-1, "None").createGUI();
    }

}
