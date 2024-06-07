package pap.gui;

import pap.gui.components.LogoPanel;
import pap.gui.components.UndoButton;
import javax.swing.*;
import java.awt.*;

public class LicenceGUI extends BaseGUI {

    JPanel mainPanel;

    void createCustomGUI() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        mainPanel.setBackground(bgColor);
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);

        int logoPanelHeight = frameHeight / 10; int footerHeight = frameHeight/10;
        int gap = frameHeight/30; int gap2 = frameHeight/60;
        LogoPanel logoPanel = new LogoPanel(logoColor, frameHeight, Integer.MAX_VALUE, logoPanelHeight);
        mainPanel.add(logoPanel);

        mainPanel.add(Box.createRigidArea(new Dimension(0, gap)));

        JPanel licenceInfoPanel = new JPanel();
        licenceInfoPanel.setLayout(new BoxLayout(licenceInfoPanel, BoxLayout.PAGE_AXIS));
        licenceInfoPanel.setBackground(bgColor);
        licenceInfoPanel.setPreferredSize(new Dimension(frameWidth, frameHeight - logoPanelHeight - footerHeight - gap - gap2*2));
        licenceInfoPanel.setMaximumSize(new Dimension(frameWidth, frameHeight - logoPanelHeight - footerHeight - gap - gap2*2));

        licenceInfoPanel.add(Box.createVerticalGlue());
        boolean hasLicence = true; //[MOCK]
        if (hasLicence) {
            //[MOCK]:
            JLabel licenceNumber = new JLabel("Licence number: " + "R5I90032EA", JLabel.LEFT);
            licenceNumber.setFont(fontBiggerBold);
            JLabel licenceHolder = new JLabel("Issued for: " + "Ola" + " " + "Nowak", JLabel.LEFT);
            licenceHolder.setFont(fontBiggerBold);
            JLabel licenceInfo = new JLabel("*This licence allows you to lawfully ride hobby horses.", JLabel.LEFT);
            licenceInfo.setFont(fontBigger);
            licenceInfoPanel.add(licenceNumber); licenceInfoPanel.add(licenceHolder); licenceInfoPanel.add(licenceInfo);
        } else {
            JLabel licenceAbsence = new JLabel("You currently do not have a licence.", JLabel.LEFT);
            licenceAbsence.setFont(fontBigger);
            licenceInfoPanel.add(licenceAbsence);
        }
        licenceInfoPanel.add(Box.createVerticalGlue());
        mainPanel.add(licenceInfoPanel);

        JPanel footerPanel = new JPanel();
        footerPanel.setLayout(new BoxLayout(footerPanel, BoxLayout.LINE_AXIS));
        footerPanel.setBackground(bgColor);
        footerPanel.setPreferredSize(new Dimension(frameWidth, footerHeight));
        footerPanel.setMaximumSize(new Dimension(frameWidth, footerHeight));

        int undoButtonSize = footerHeight/2;
        footerPanel.add(Box.createRigidArea(new Dimension(undoButtonSize/2, 0)));
        UndoButton undoButton = new UndoButton(undoButtonSize, undoButtonSize, undoButtonSize, undoButtonSize);
        undoButton.addActionListener(e->undoBtnClickedAction());
        footerPanel.add(undoButton);
        footerPanel.add(Box.createHorizontalGlue());

        mainPanel.add(Box.createRigidArea(new Dimension(0,gap2)));
        mainPanel.add(footerPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0,gap2)));
    }

    void undoBtnClickedAction(){
        new HomePageGUI(userId, userType).createGUI();
        frame.setVisible(false);
    }

    void createGUI(){
        super.createBaseGUI();
        createCustomGUI();
        frame.setVisible(true);
    }

    public LicenceGUI(int userId, String userType){
        super(userId, userType);
    }

    public static void main(String[] args) {
        new LicenceGUI(-1, "None").createGUI();
    }
}
