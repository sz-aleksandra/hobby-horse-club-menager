package pap.gui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class UndoPanel extends JPanel {
    public UndoPanel(int frameWidth, int btnHeight, Color bgColor, ActionListener actionListener, String pageName, Font fontMiddle) {
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        setBackground(bgColor);
        setPreferredSize(new Dimension(frameWidth, btnHeight));
        setMaximumSize(new Dimension(frameWidth, btnHeight));
        UndoButton undoBtn = new UndoButton(btnHeight, btnHeight, btnHeight, btnHeight);
        undoBtn.addActionListener(actionListener);
        add(Box.createRigidArea(new Dimension(btnHeight/2, 0)));
        add(undoBtn);

        JLabel pageNameLabel = new JLabel(pageName, JLabel.CENTER);
        pageNameLabel.setFont(fontMiddle);
        add(Box.createHorizontalGlue()); add(pageNameLabel); add(Box.createRigidArea(new Dimension(20, 0)));
    }
}
