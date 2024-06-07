package pap.gui.components;

import javax.swing.*;
import java.awt.*;

public class RoundedButton extends JButton {
    public Color fillColor, hoverColor;
    public int preferredWidth, preferredHeight;
    boolean squareShaped;

    public RoundedButton(String text, int preferredWidth, int preferredHeight, Color fillColor, Color hoverColor, Font font, boolean squareShaped){//, String hexBorderColor) {
        super(text);
        this.setFont(font);
        this.fillColor = fillColor;
        this.hoverColor = hoverColor;
        this.squareShaped = squareShaped;
        setContentAreaFilled(false); // Make the button transparent
        setFocusPainted(false); // Remove the focus border
        setBorderPainted(false); // Make border transparent
        setPreferredSize(new Dimension(preferredWidth, preferredHeight));
        setMaximumSize(new Dimension(preferredWidth, preferredHeight));
        this.preferredWidth = preferredWidth;
        this.preferredHeight = preferredHeight;
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Draw the rounded button
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (getModel().isArmed() || getModel().isRollover()) {
            g2.setColor(hoverColor);
        } else {
            g2.setColor(fillColor);
        }
        if (squareShaped) {
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), preferredWidth/2, preferredHeight/2);
        } else {
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), preferredWidth/2, preferredHeight);
        }
        super.paintComponent(g);
    }
}