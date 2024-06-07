package pap.gui.components;

import java.awt.*;

public class ScrollElementButton extends RoundedButton {
    public int elementId;

    public ScrollElementButton(String text, int preferredWidth, int preferredHeight, Color fillColor, Color hoverColor, Font font, boolean squareShaped, int elementId) {
        super("<html><b>"+text+"</b></html>", preferredWidth, preferredHeight, fillColor, hoverColor, font, squareShaped);
        this.elementId = elementId;
    }
}
