package pap.gui.SeeDataByScrolling;

import pap.gui.components.RoundedButton;
import pap.gui.components.ScrollElementButton;

import javax.swing.*;
import java.awt.*;

public abstract class DataScrollTemplate extends ScrollGUITemplate {

    // Usage: couple addJLabel methods
    protected abstract void addInfoToDataInfoPanel(int elementId, JPanel dataInfoPanel);
    protected abstract void handleAddData();
    protected abstract void handleEditData();
    protected abstract void handleRemoveData();
    protected int buttonsGapSize;

    protected JPanel createScrollElement(int elementId) {

        JPanel dataPanel = new JPanel();
        dataPanel.setBackground(neutralBlue);
        dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.LINE_AXIS));
        dataPanel.setPreferredSize(new Dimension(frameWidth, elementHeight));
        dataPanel.setMaximumSize(new Dimension(frameWidth, elementHeight));
        dataPanel.add(Box.createRigidArea(new Dimension(frameWidth/20,0)));

        JPanel dataInfoPanel = new JPanel();
        dataInfoPanel.setBackground(neutralGray);
        dataInfoPanel.setLayout(new BoxLayout(dataInfoPanel, BoxLayout.PAGE_AXIS));
        dataInfoPanel.setPreferredSize(new Dimension(elementWidth, elementHeight));
        dataInfoPanel.setMaximumSize(new Dimension(elementWidth, elementHeight));
        addInfoToDataInfoPanel(elementId, dataInfoPanel);
        dataPanel.add(dataInfoPanel);

        return dataPanel;
    }

    protected void createScrollButtons(int elementId, JPanel dataPanel) {

        int buttonSize = scrollButtonSize; this.buttonsGapSize = buttonSize/3;
        dataPanel.add(Box.createRigidArea(new Dimension(buttonsGapSize,0)));

        ScrollElementButton editButton = new ScrollElementButton("Edit", buttonSize, buttonSize, secondColor, secondColorDarker, fontButtons, true, elementId);
        editButton.addActionListener(actionEvent -> {
            handleEditData();
        });
        dataPanel.add(editButton);
        dataPanel.add(Box.createRigidArea(new Dimension(buttonsGapSize,0)));

        ScrollElementButton removeButton = new ScrollElementButton("Delete", buttonSize, buttonSize, statusWrongLighter, statusWrong, fontButtons, true, elementId);
        editButton.addActionListener(actionEvent -> {
            handleRemoveData();
        });
        dataPanel.add(removeButton);
    }

    protected void createCustomGUI() {
        super.createCustomGUI();
        RoundedButton addButton = new RoundedButton("Add", frameWidth/10, 80, Color.decode("#ebb33b"), Color.decode("#c7911c"), fontButtons, false);
        addButton.addActionListener(e->handleAddData());
        logoPanel.add(addButton); logoPanel.add(Box.createRigidArea(new Dimension(buttonsGapSize,0)));
    }

    public DataScrollTemplate(int userId, String userType, String pageName){
        super(userId, userType);
        this.elementHeight = frameHeight/6;
        this.elementWidth = frameWidth*6/10;
        this.pageName = pageName;
    }

}
