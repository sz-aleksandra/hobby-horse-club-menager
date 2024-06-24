package bd2.gui.DetailsView;

import bd2.gui.BaseGUI;
import bd2.gui.HomePageGUI;
import bd2.gui.components.LogoPanel;
import bd2.gui.components.UndoButton;
import bd2.logic.getInfoById;
import bd2.logic.getMemberInfo;

import javax.swing.*;

import com.google.gson.JsonParser;

import java.awt.*;
import java.net.http.HttpResponse;

public class LicenceGUI extends BaseGUI {

    JPanel mainPanel;

    protected void createCustomGUI() {
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

		String name = "";
		String surname = "";
		String licenceLevel = "";

		try {
            HttpResponse<String> response = getInfoById.getInfo(userId, "members/get_by_id/");
			
			String memberName = JsonParser.parseString(response.body()).getAsJsonObject()
					   .getAsJsonArray("members").get(0).getAsJsonObject().get("name").getAsString();
			String memberSurname = JsonParser.parseString(response.body()).getAsJsonObject()
					   .getAsJsonArray("members").get(0).getAsJsonObject().get("surname").getAsString();
			String memberLicence = JsonParser.parseString(response.body()).getAsJsonObject()
					   .getAsJsonArray("members").get(0).getAsJsonObject().getAsJsonObject("licence").get("licence_level").getAsString();
			name = memberName;
			surname = memberSurname;
			licenceLevel = memberLicence;
		} catch (Exception e) {
			e.printStackTrace();
		}

        if ((!licenceLevel.equals("No licence")) && (!licenceLevel.equals("No Coaching Licence"))) {
            JLabel licenceNumber = new JLabel(licenceLevel, JLabel.LEFT);
            licenceNumber.setFont(fontBiggerBold);
            JLabel licenceHolder = new JLabel("Issued for: " + name + " " + surname, JLabel.LEFT);
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

    public LicenceGUI(int userId, String userType){
        super(userId, userType);
    }

    public static void main(String[] args) {
        new LicenceGUI(-1, "None").createGUI();
    }
}
