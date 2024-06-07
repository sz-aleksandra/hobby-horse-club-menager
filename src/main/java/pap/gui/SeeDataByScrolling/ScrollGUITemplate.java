package pap.gui.SeeDataByScrolling;

import pap.gui.BaseGUI;
import pap.gui.HomePageGUI;
import pap.gui.components.*;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public abstract class ScrollGUITemplate extends BaseGUI {
    protected JPanel mainPanel, scrollPanel;
    protected JScrollPane scrollPanelEnabler;
    protected LogoPanel logoPanel;

    protected int nrOfElements;
    protected Integer[] fittingElementsIds;
    protected int elementHeight = frameHeight/4;
    protected int elementWidth = frameWidth/3;
    protected int scrollButtonSize = frameHeight/7;
    protected String pageName = "";

    protected abstract void getElementsData(); //called in constructor
    protected abstract HashMap<String, String> getElementData(int elementId);
    protected abstract JPanel createScrollElement(int elementId);
    protected abstract void createScrollButtons(int elementId, JPanel elementPanel);

    protected void createCustomGUI() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        mainPanel.setBackground(bgColor);
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);

        int logoPanelHeight = frameHeight/10;
        int filtersPanelHeight = 0;
        int scrollPanelEnablerHeight = frameHeight - logoPanelHeight - filtersPanelHeight;

        logoPanel = new LogoPanel(logoColor, frameHeight, frameWidth, logoPanelHeight);
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

    protected void undoBtnClickedAction(){
        new HomePageGUI(userId, userType).createGUI();
        frame.setVisible(false);
    }

    protected void addJLabel(String text, Color color, Font font, JPanel panel, int width, int height) {
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
