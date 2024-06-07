package pap.gui.components;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.HashMap;

public abstract class OfferReservationPanel extends JPanel {

    int offerWidth, offerHeight;
    Color bgColor; Font fontBigger, fontMiddle, fontMiddleBold;

    public OfferReservationPanel(Color bgColor, Font fontBigger, Font fontMiddle, Font fontMiddleBold, int offerWidth, int offerHeight,
                                 String topPanelText, HashMap<String, String> detailsInfo, Image offerImg) {

        this.offerWidth = offerWidth; this.offerHeight = offerHeight;
        this.bgColor = bgColor; this.fontBigger = fontBigger; this.fontMiddle = fontMiddle; this.fontMiddleBold = fontMiddleBold;
        int bottomPanelHeight = offerHeight*3/4; int offerImgWidth = offerWidth/2;

        setBackground(bgColor);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setPreferredSize(new Dimension(offerWidth, offerHeight));
        setMaximumSize(new Dimension(offerWidth, offerHeight));

        JPanel topPanel = new JPanel();
        topPanel.setBackground(bgColor);
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.LINE_AXIS));
        topPanel.setPreferredSize(new Dimension(offerWidth, offerHeight - bottomPanelHeight));
        topPanel.setMaximumSize(new Dimension(offerWidth, offerHeight - bottomPanelHeight));
        topPanel.add(Box.createRigidArea(new Dimension(10,0)));
        JLabel topPanelTextLabel = new JLabel(topPanelText, JLabel.LEFT);
        topPanelTextLabel.setFont(fontBigger);
        topPanelTextLabel.setPreferredSize(new Dimension(offerWidth, offerHeight - bottomPanelHeight-10));
        topPanelTextLabel.setMaximumSize(new Dimension(offerWidth, offerHeight - bottomPanelHeight-10));
        topPanel.add(topPanelTextLabel);
        add(topPanel);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(bgColor);
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.LINE_AXIS));
        bottomPanel.setPreferredSize(new Dimension(offerWidth, bottomPanelHeight));
        bottomPanel.setMaximumSize(new Dimension(offerWidth, bottomPanelHeight));

        try {
//            int currHeight = offerImg.getHeight(); int currWidth = offerImg.getHeight();
//            int goalHeight = bottomPanelHeight; int goalWidth = (currWidth*goalHeight)/currHeight;
//            offerImg = (BufferedImage) offerImg.getScaledInstance(goalWidth, goalHeight, Image.SCALE_SMOOTH);
            offerImg = offerImg.getScaledInstance(offerImgWidth, bottomPanelHeight, Image.SCALE_SMOOTH);
            ImageIcon offerImgIcon = new ImageIcon(offerImg);
            JLabel offerImgLabel = new JLabel();
            offerImgLabel.setIcon(offerImgIcon);
            bottomPanel.add(offerImgLabel);
        } catch (Exception e) {
            JPanel imgPanel = new JPanel();
            imgPanel.setBackground(Color.RED);
            imgPanel.setPreferredSize(new Dimension(offerImgWidth, bottomPanelHeight));
            imgPanel.setMaximumSize(new Dimension(offerImgWidth, bottomPanelHeight));
            bottomPanel.add(imgPanel);
        }

        bottomPanel.add(Box.createRigidArea(new Dimension(10,0)));
        JPanel detailsPanel = new JPanel();
        detailsPanel.setBackground(bgColor);
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.PAGE_AXIS));
        detailsPanel.setPreferredSize(new Dimension(offerWidth - offerImgWidth, bottomPanelHeight));
        detailsPanel.setMaximumSize(new Dimension(offerWidth - offerImgWidth, bottomPanelHeight));
        populateDetailsPanel(detailsPanel, detailsInfo);
        bottomPanel.add(detailsPanel);
        add(bottomPanel);
    }

    void addJLabel(String text, Color color, Font font, JPanel panel) {
        JLabel textLabel = new JLabel(text, JLabel.LEFT);
        textLabel.setPreferredSize(new Dimension(offerWidth, offerHeight));
        textLabel.setMaximumSize(new Dimension(offerWidth, offerHeight));
        textLabel.setFont(font);
        textLabel.setForeground(color);
        panel.add(textLabel);
    }

    abstract void populateDetailsPanel(JPanel detailsPanel, HashMap<String, String> detailsInfo);

}
