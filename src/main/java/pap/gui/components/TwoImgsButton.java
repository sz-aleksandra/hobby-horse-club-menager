package pap.gui.components;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class TwoImgsButton extends JButton {

    Image baseImg, secondImg;
    public String state;
    boolean imgUploadSuccess = false;

    public TwoImgsButton(int buttonWidth, int buttonHeight, int imgWidth, int imgHeight, String baseImgPath, String secondImgPath) {
        setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        setMaximumSize(new Dimension(buttonWidth, buttonHeight));
        try {
            setContentAreaFilled(false); // Make the button transparent
            Image baseImg = ImageIO.read(new File(getClass().getResource(baseImgPath).getPath()));
            baseImg = baseImg.getScaledInstance(imgWidth, imgHeight, Image.SCALE_SMOOTH);
            Image secondImg = ImageIO.read(new File(getClass().getResource(secondImgPath).getPath()));
            secondImg = secondImg.getScaledInstance(imgWidth, imgHeight, Image.SCALE_SMOOTH);
            setIcon(new ImageIcon(baseImg));
            this.baseImg = baseImg;
            this.secondImg = secondImg;
            imgUploadSuccess = true;
        } catch (Exception ex) {
            setContentAreaFilled(true);
        }
        state = "base_state";
    }

    public void changeState() {
        if (state.equals("base_state")) {
            if (imgUploadSuccess){
                setIcon(new ImageIcon(secondImg));
            }
            state = "second_state";
        } else {
            if (imgUploadSuccess){
                setIcon(new ImageIcon(baseImg));
            }
            state = "base_state";
        }
    }
}