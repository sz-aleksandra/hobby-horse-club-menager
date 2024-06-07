package pap.gui.components;

import javax.swing.*;
import java.awt.*;

public class FiltersPanel extends JPanel {

    TwoImgsButton ShowHideFilterButton;
    JScrollPane otherPanel;
    JFrame frame;
    int frameHeight, frameWidth, otherPanelHeight, panelWidth, panelHeight;

    public FiltersPanel(Color panelColor, int frameWidth, int frameHeight, int panelWidth, int panelHeight, JScrollPane otherPanel, int otherPanelHeight, JFrame frame) {

        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        setBackground(panelColor);
        setPreferredSize(new Dimension(panelWidth, panelHeight));
        setMaximumSize(new Dimension(panelWidth, panelHeight));

        this.add(Box.createRigidArea(new Dimension(frameHeight/20,0)));

        ShowHideFilterButton = new TwoImgsButton(panelHeight, panelHeight, panelHeight*2, panelHeight, "/show_more_128.png", "/show_less_128.png");
        ShowHideFilterButton.addActionListener(e->showHideFiltersClicked());
        this.add(ShowHideFilterButton);

        this.otherPanel = otherPanel;
        this.frameHeight = frameHeight; this.frameWidth = frameWidth;
        this.otherPanelHeight = otherPanelHeight;
        this.panelHeight = panelHeight; this.panelWidth = panelWidth;
        this.frame = frame;

    }

    void showHideFiltersClicked() {
        // Showing filters
        if (ShowHideFilterButton.state.equals("base_state")) {
            ShowHideFilterButton.changeState();
            changePanelSizes(panelWidth, panelHeight + 100, frameWidth, otherPanelHeight - 100);
        } else {
            // Hiding filters
            ShowHideFilterButton.changeState();
            changePanelSizes(panelWidth, panelHeight, frameWidth, otherPanelHeight);
        }
    }

    void changePanelSizes(int filterPanelWidth, int filterPanelHeight, int otherPanelWidth, int otherPanelHeight) {
        this.setPreferredSize(new Dimension(filterPanelWidth, filterPanelHeight));
        this.setMaximumSize(new Dimension(filterPanelWidth, filterPanelHeight));
        otherPanel.setPreferredSize(new Dimension(otherPanelWidth, otherPanelHeight));
        otherPanel.setMaximumSize(new Dimension(otherPanelWidth, otherPanelHeight));
        frame.revalidate();
        frame.repaint();
        this.revalidate();
        this.repaint();
    }

}