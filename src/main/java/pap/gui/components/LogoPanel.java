package pap.gui.components;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class LogoPanel extends JPanel {
    int frameHeight, imgWidth, imgHeight;
    BufferedImage logoImage;
    Image scaledLogoImage;
    JLabel logoImageLabel;

    public LogoPanel(Color logoColor, int frameHeight, int panelWidth, int panelHeight) {
        this.frameHeight = frameHeight;
        this.imgWidth = (int)(panelHeight*5/7*3.6);
        this.imgHeight = (int)(panelHeight*5/7);
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        setBackground(logoColor);
        setPreferredSize(new Dimension(panelWidth, panelHeight));
        setMaximumSize(new Dimension(panelWidth, panelHeight));
        addLogoImage();
    }

    void addLogoImage() {
        try {
            logoImage = ImageIO.read(new File(getClass().getResource("/logo_name_mixed.png").getPath()));
            scaledLogoImage = logoImage.getScaledInstance(imgWidth, imgHeight, Image.SCALE_SMOOTH);
            logoImageLabel = new JLabel(new ImageIcon(scaledLogoImage));
            this.add(Box.createRigidArea(new Dimension(20,0)));
            this.add(logoImageLabel);
            this.add(Box.createHorizontalGlue());
        } catch (Exception e) {
            ;
        }
    }
}