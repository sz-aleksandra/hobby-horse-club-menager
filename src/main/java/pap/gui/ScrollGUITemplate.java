package pap.gui;

import pap.gui.components.FiltersPanel;
import pap.gui.components.LogoPanel;
import pap.gui.components.UndoPanel;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public abstract class ScrollGUITemplate extends BaseGUI{
    JPanel mainPanel, scrollPanel;
    JScrollPane scrollPanelEnabler;

    int nrOfElements;
    Integer[] fittingElementsIds;
    int elementHeight = frameHeight/4;
    int elementWidth = frameWidth/3;
    int scrollButtonSize = frameHeight/7;
    String pageName = "";

    abstract void getElementsData(); //called in constructor
    abstract HashMap<String, String> getElementData(int elementId);
    abstract JPanel createScrollElement(int elementId);
    abstract void createScrollButtons(int elementId, JPanel elementPanel);

    void createCustomGUI() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        mainPanel.setBackground(bgColor);
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);

        int logoPanelHeight = frameHeight/10;
        int filtersPanelHeight = 0;
        int scrollPanelEnablerHeight = frameHeight - logoPanelHeight - filtersPanelHeight;

        LogoPanel logoPanel = new LogoPanel(logoColor, frameHeight, frameWidth, logoPanelHeight);
        mainPanel.add(logoPanel);

        scrollPanel = new JPanel();
        scrollPanel.setBackground(bgColor);
        scrollPanel.setLayout(new BoxLayout(scrollPanel, BoxLayout.PAGE_AXIS));
        scrollPanelEnabler = new JScrollPane(scrollPanel);
        scrollPanelEnabler.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPanelEnabler.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanelEnabler.setPreferredSize(new Dimension(frameWidth, scrollPanelEnablerHeight));
        scrollPanelEnabler.setMaximumSize(new Dimension(frameWidth, scrollPanelEnablerHeight));
        scrollPanelEnabler.getVerticalScrollBar().setUnitIncrement(10);

        mainPanel.add(scrollPanelEnabler);

        for (int i = 0; i < nrOfElements; i++) {
            JPanel elementPanel = createScrollElement(fittingElementsIds[i]);
            createScrollButtons(fittingElementsIds[i], elementPanel);

            scrollPanel.add(elementPanel);
            scrollPanel.add(Box.createRigidArea(new Dimension(0,30)));
        }
        UndoPanel undoPanel = new UndoPanel(frameWidth, frameHeight/20, bgColor, e->undoBtnClickedAction(), pageName, fontMiddle);
        mainPanel.add(undoPanel);
    }

    public void createGUI(){
        super.createBaseGUI();
        createCustomGUI();
        frame.setVisible(true);
    }

    void undoBtnClickedAction(){
        new HomePageGUI(userId, userType).createGUI();
        frame.setVisible(false);
    }

    void addJLabel(String text, Color color, Font font, JPanel panel, int width, int height) {
        JLabel textLabel = new JLabel(text, JLabel.LEFT);
        textLabel.setPreferredSize(new Dimension(width, height));
        textLabel.setMaximumSize(new Dimension(width, height));
        textLabel.setFont(font);
        textLabel.setForeground(color);
        panel.add(textLabel);
    }

    public ScrollGUITemplate(int userId, String userType){
        super(userId, userType);
        getElementsData();
    }
}
