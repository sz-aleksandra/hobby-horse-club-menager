package pap.gui.components;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.HashMap;

public class HotelPanel extends JPanel{
    int hotelWidth, hotelHeight;
    Color bgColor; Font fontBigger, fontMiddle, fontMiddleBold;

    public HotelPanel(Color bgColor, Font fontBigger, Font fontBiggerBold, Font fontMiddle, Font fontMiddleBold, int hotelWidth, int hotelHeight,
                                 HashMap<String, String> hotelInfo) {

        this.hotelWidth = hotelWidth; this.hotelHeight = hotelHeight;
        this.bgColor = bgColor; this.fontBigger = fontBigger; this.fontMiddle = fontMiddle; this.fontMiddleBold = fontMiddleBold;

        setBackground(bgColor);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setPreferredSize(new Dimension(hotelWidth, hotelHeight));
        setMaximumSize(new Dimension(hotelWidth, hotelHeight));

//        this.add(Box.createRigidArea(new Dimension(0, 10)));
        JPanel namePanel = new JPanel();
        namePanel.setBackground(bgColor);
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.LINE_AXIS));
        namePanel.setPreferredSize(new Dimension(hotelWidth, hotelHeight/3));
        namePanel.setMaximumSize(new Dimension(hotelWidth, hotelHeight/3));
        this.add(namePanel);
        addJLabel(hotelInfo.get("name"), Color.BLACK, fontBiggerBold, namePanel);
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(bgColor);
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.LINE_AXIS));
        bottomPanel.setPreferredSize(new Dimension(hotelWidth, hotelHeight*2/3));
        bottomPanel.setMaximumSize(new Dimension(hotelWidth, hotelHeight*2/3));
        this.add(bottomPanel);

        JPanel generalInfoPanel = new JPanel();
        generalInfoPanel.setBackground(bgColor);
        generalInfoPanel.setLayout(new BoxLayout(generalInfoPanel, BoxLayout.PAGE_AXIS));
        generalInfoPanel.setPreferredSize(new Dimension(hotelWidth/3, hotelHeight*2/3));
        generalInfoPanel.setMaximumSize(new Dimension(hotelWidth/3, hotelHeight*2/3));
        JPanel contactInfoPanel = new JPanel();
        contactInfoPanel.setBackground(bgColor);
        contactInfoPanel.setLayout(new BoxLayout(contactInfoPanel, BoxLayout.PAGE_AXIS));
        contactInfoPanel.setPreferredSize(new Dimension(hotelWidth*2/3, hotelHeight*2/3));
        contactInfoPanel.setMaximumSize(new Dimension(hotelWidth*2/3, hotelHeight*2/3));
        bottomPanel.add(generalInfoPanel); bottomPanel.add(contactInfoPanel);

        addJLabel("No offers: " + hotelInfo.get("offer_no"), Color.BLACK, fontMiddle, generalInfoPanel);
        addJLabel("Country: " + hotelInfo.get("country"), Color.BLACK, fontMiddle, generalInfoPanel);
        addJLabel("City: " + hotelInfo.get("city"), Color.BLACK, fontMiddle, generalInfoPanel);

        addJLabel("Website: " + hotelInfo.get("website"), Color.BLACK, fontMiddle, contactInfoPanel);
        addJLabel("Email: " + hotelInfo.get("email"), Color.BLACK, fontMiddle, contactInfoPanel);
        addJLabel("Phone number: " + hotelInfo.get("phone_number"), Color.BLACK, fontMiddle, contactInfoPanel);
        addJLabel("Bank account number: " + hotelInfo.get("bank_account_nr"), Color.BLACK, fontMiddle, contactInfoPanel);

//        bottomPanel.add(Box.createRigidArea(new Dimension(10,0)));
    }

    void addJLabel(String text, Color color, Font font, JPanel panel) {
        JLabel textLabel = new JLabel(text, JLabel.LEFT);
        textLabel.setPreferredSize(new Dimension(hotelWidth, hotelHeight));
        textLabel.setMaximumSize(new Dimension(hotelWidth, hotelHeight));
        textLabel.setFont(font);
        textLabel.setForeground(color);
        panel.add(textLabel);
    }

}

