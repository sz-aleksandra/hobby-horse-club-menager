package pap.gui.components;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class TextIconButton extends RoundedButton {
    public TextIconButton(String text, int preferredWidth, int preferredHeight, Color fillColor, Color hoverColor, Font font, boolean squareShaped, String imgPath, int imgSize) {
        super("<html><b>" + text + "</b></html>", preferredWidth, preferredHeight, fillColor, hoverColor, font, squareShaped);
        try {
            Image img = ImageIO.read(new File(getClass().getResource(imgPath).getPath()));
            img = img.getScaledInstance(imgSize, imgSize, Image.SCALE_SMOOTH);
            setIcon(new ImageIcon(img));
        } catch (Exception e) {
            ;
        }
//        setMargin(new Insets(1,1,1,1));
    }

//    @Override
//    public void doLayout()
//    {
//        super.doLayout();
//        int gap = 10;
//        gap = Math.max(gap, UIManager.getInt("Button.iconTextGap"));
//        setIconTextGap(gap);
//    }
}