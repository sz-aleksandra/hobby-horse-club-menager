package pap.gui.components;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class OwnersOfferPanel extends JPanel{
    int offerWidth, offerHeight;
    Color bgColor; Font fontBigger, fontMiddle, fontMiddleBold;

    public OwnersOfferPanel(Color bgColor, Font fontBigger, Font fontBiggerBold, Font fontMiddle, Font fontMiddleBold, int offerWidth, int offerHeight,
                            HashMap<String, String> offerInfo) {

        this.offerWidth = offerWidth; this.offerHeight = offerHeight;
        this.bgColor = bgColor; this.fontBigger = fontBigger; this.fontMiddle = fontMiddle; this.fontMiddleBold = fontMiddleBold;

        setBackground(bgColor);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setPreferredSize(new Dimension(offerWidth, offerHeight));
        setMaximumSize(new Dimension(offerWidth, offerHeight));

//        this.add(Box.createRigidArea(new Dimension(0, 10)));
        JPanel namePanel = new JPanel();
        namePanel.setBackground(bgColor);
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.LINE_AXIS));
        namePanel.setPreferredSize(new Dimension(offerWidth, offerHeight/3));
        namePanel.setMaximumSize(new Dimension(offerWidth, offerHeight/3));
        this.add(namePanel);
        addJLabel(offerInfo.get("name"), Color.BLACK, fontBiggerBold, namePanel);
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(bgColor);
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.LINE_AXIS));
        bottomPanel.setPreferredSize(new Dimension(offerWidth, offerHeight*2/3));
        bottomPanel.setMaximumSize(new Dimension(offerWidth, offerHeight*2/3));
        this.add(bottomPanel);

        JPanel generalInfoPanel = new JPanel();
        generalInfoPanel.setBackground(bgColor);
        generalInfoPanel.setLayout(new BoxLayout(generalInfoPanel, BoxLayout.PAGE_AXIS));
        generalInfoPanel.setPreferredSize(new Dimension(offerWidth, offerHeight*2/3));
        generalInfoPanel.setMaximumSize(new Dimension(offerWidth, offerHeight*2/3));
        bottomPanel.add(generalInfoPanel);

        addJLabel("Hotel: " + offerInfo.get("hotel"), Color.BLACK, fontMiddle, generalInfoPanel);
        addJLabel("Date added: " + offerInfo.get("add_date"), Color.BLACK, fontMiddle, generalInfoPanel);
        addJLabel("Price per night: " + offerInfo.get("price"), Color.BLACK, fontMiddle, generalInfoPanel);
        addJLabel("No of finalised reservations: " + offerInfo.get("res_no"), Color.BLACK, fontMiddle, generalInfoPanel);
        addJLabel("Total income: " + offerInfo.get("total_income"), Color.BLACK, fontMiddle, generalInfoPanel);

//        bottomPanel.add(Box.createRigidArea(new Dimension(10,0)));
    }

    void addJLabel(String text, Color color, Font font, JPanel panel) {
        JLabel textLabel = new JLabel(text, JLabel.LEFT);
        textLabel.setPreferredSize(new Dimension(offerWidth, offerHeight));
        textLabel.setMaximumSize(new Dimension(offerWidth, offerHeight));
        textLabel.setFont(font);
        textLabel.setForeground(color);
        panel.add(textLabel);
    }

}

