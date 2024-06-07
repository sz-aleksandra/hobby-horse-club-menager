package pap.gui.components;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class LogOutButton extends JButton {
    public LogOutButton(int buttonWidth, int buttonHeight, int imgWidth, int imgHeight) {
        setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        setMaximumSize(new Dimension(buttonWidth, buttonHeight));
        try {
            setContentAreaFilled(false); // Make the button transparent
            Image baseImg = ImageIO.read(new File(getClass().getResource("/logout_img_64.png").getPath()));
            baseImg = baseImg.getScaledInstance(imgWidth, imgHeight, Image.SCALE_SMOOTH);
            setIcon(new ImageIcon(baseImg));
        } catch (Exception ex) {
            setContentAreaFilled(true);
        }
    }
}