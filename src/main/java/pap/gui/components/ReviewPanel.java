package pap.gui.components;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.HashMap;

public class ReviewPanel extends JPanel{
    int reviewWidth, reviewHeight;
    Color bgColor; Font fontBigger, fontMiddle, fontMiddleBold;

    public ReviewPanel(Color bgColor, Font fontBigger, Font fontBiggerBold, Font fontMiddle, Font fontMiddleBold, Font fontSmaller, int reviewWidth, int reviewHeight,
                       HashMap<String, String> reviewInfo) {

        this.reviewWidth = reviewWidth; this.reviewHeight = reviewHeight;
        this.bgColor = bgColor; this.fontBigger = fontBigger; this.fontMiddle = fontMiddle; this.fontMiddleBold = fontMiddleBold;

        setBackground(bgColor);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setPreferredSize(new Dimension(reviewWidth, reviewHeight));
        setMaximumSize(new Dimension(reviewWidth, reviewHeight));

        JPanel namePanel = new JPanel();
        namePanel.setBackground(bgColor);
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.LINE_AXIS));
        namePanel.setPreferredSize(new Dimension(reviewWidth, reviewHeight/3));
        namePanel.setMaximumSize(new Dimension(reviewWidth, reviewHeight/3));
        this.add(namePanel);
        addJLabel(reviewInfo.get("offer_name") + " by " + reviewInfo.get("hotel_name"), Color.BLACK, fontMiddle, namePanel);

        JPanel reviewConcisePanel = new JPanel();
        reviewConcisePanel.setBackground(bgColor);
        reviewConcisePanel.setLayout(new BoxLayout(reviewConcisePanel, BoxLayout.LINE_AXIS));
        reviewConcisePanel.setPreferredSize(new Dimension(reviewWidth, reviewHeight/3));
        reviewConcisePanel.setMaximumSize(new Dimension(reviewWidth, reviewHeight/3));
        this.add(reviewConcisePanel);
        try {
            Image ratingImg = ImageIO.read(new File(getClass().getResource("/" + Integer.parseInt(reviewInfo.get("rating")) + "-star.png").getPath()));
            ratingImg = ratingImg.getScaledInstance((reviewWidth/4)*3/4, (reviewHeight/3)*3/4, Image.SCALE_SMOOTH);
            ImageIcon ratingImgIcon = new ImageIcon(ratingImg);
            JLabel ratingImgLabel = new JLabel();
            ratingImgLabel.setIcon(ratingImgIcon);
            reviewConcisePanel.add(ratingImgLabel);
            reviewConcisePanel.add(Box.createRigidArea(new Dimension(15, 0)));
        } catch (Exception e) {
            addJLabel(reviewInfo.get("rating") + "/5 stars", Color.BLACK, fontMiddle, reviewConcisePanel);
        }
        addJLabel("given by " + reviewInfo.get("user_name"), Color.BLACK, fontMiddle, reviewConcisePanel);

        JPanel reviewDetailsPanel = new JPanel();
        reviewDetailsPanel.setBackground(bgColor);
        reviewDetailsPanel.setLayout(new BoxLayout(reviewDetailsPanel, BoxLayout.LINE_AXIS));
        reviewDetailsPanel.setPreferredSize(new Dimension(reviewWidth, reviewHeight/3));
        reviewDetailsPanel.setMaximumSize(new Dimension(reviewWidth, reviewHeight/3));
        this.add(reviewDetailsPanel);
        addJLabel(reviewInfo.get("comment"), Color.BLACK, fontSmaller, reviewDetailsPanel);

    }

    void addJLabel(String text, Color color, Font font, JPanel panel) {
        JLabel textLabel = new JLabel(text, JLabel.LEFT);
        textLabel.setPreferredSize(new Dimension(reviewWidth, reviewHeight));
        textLabel.setMaximumSize(new Dimension(reviewWidth, reviewHeight));
        textLabel.setFont(font);
        textLabel.setForeground(color);
        panel.add(textLabel);
    }

}

