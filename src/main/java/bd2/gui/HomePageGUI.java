package bd2.gui;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.google.gson.JsonParser;

import java.awt.*;
import java.io.File;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import bd2.gui.DetailsView.LicenceGUI;
import bd2.gui.SeeDataByScrolling.*;
import bd2.gui.SignUpLogIn.LogInGUI;
import bd2.gui.components.LogOutButton;
import bd2.gui.components.LogoPanel;
import bd2.gui.components.TextIconButton;
import bd2.logic.getMemberInfo;
import bd2.logic.ErrorCodes;
import bd2.logic.deactivateAccount;

// TODO: pomysl - dodanie wszytskich modyfikacji danych to moze byc 1. stworzenie formularza 2. przejscie po wszystkich polach tak jak w getFieldsValues TYLKO nie getowanie a ustawinie. I moze to doslownie bedzie szybkie.
// TODO: filtry?

public class HomePageGUI extends BaseGUI {

    MenuButton seeTrainingsButton, seeTournamentsButton, seeHorsesButton,
            seeStablesButton, seeLicenceButton, deactivateAccountButton, seeAccessoriesButton,
            seeRidersButton, seeGroupsButton, seePositionsButton, seeEmployeesButton;
    JPanel mainPanel, buttonsPanel, buttonsRowsPanel, infoPanel;
    LogoPanel logoPanel;
    JLabel welcomeLabel;
    LogOutButton logOutButton;
    int menuButtonWidth = (this.userType.equals("Rider")) ? frameWidth*4/20 : frameWidth*3/20;
    int menuButtonHeight = frameHeight*4/40;
    int menuButtonGap = menuButtonWidth/3;

    public HomePageGUI(int userId, String userType) {
        super(userId, userType);
    }

    protected void createCustomGUI(){
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        mainPanel.setBackground(bgColor);
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);

        logoPanel = new LogoPanel(logoColor, frameHeight,Integer.MAX_VALUE, frameHeight/5);
        mainPanel.add(logoPanel);
        mainPanel.add(Box.createVerticalGlue());

        infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.LINE_AXIS));
        infoPanel.setBackground(bgColor);

        String name = "Default"; String userImgPath = ""; String positionName = "Default";
        if (userType.equals("Rider")) {
			try {
            	HttpResponse<String> response = getMemberInfo.getInfo(userId, true);
				String memberName = JsonParser.parseString(response.body()).getAsJsonObject()
						   .getAsJsonArray("riders").get(0).getAsJsonObject()
						   .getAsJsonObject("member").get("name").getAsString();
				name = memberName;
			} catch (Exception e) {
				e.printStackTrace();
			}
            userImgPath = "/student.png";
        } else if (userType.equals("Employee")) {
            try {
            	HttpResponse<String> response = getMemberInfo.getInfo(userId, false);
				positionName = JsonParser.parseString(response.body()).getAsJsonObject()
							.getAsJsonArray("employees").get(0).getAsJsonObject()
							.getAsJsonObject("position").get("name").getAsString();
				String memberName = JsonParser.parseString(response.body()).getAsJsonObject()
						   .getAsJsonArray("employees").get(0).getAsJsonObject()
						   .getAsJsonObject("member").get("name").getAsString();
				name = memberName + " (" + positionName+ ")";
			} catch (Exception e) {
				e.printStackTrace();
			}
            userImgPath = "/business.png";
        } else {
            name = "Member";
        }
        welcomeLabel = new JLabel("Hello " + name + "!", JLabel.CENTER);
        welcomeLabel.setFont(fontMiddle);
        infoPanel.add(Box.createRigidArea(new Dimension(menuButtonGap*3/2,0))); infoPanel.add(welcomeLabel);

        if (!userImgPath.isEmpty()) {
            try {
                Image userImg = ImageIO.read(new File(getClass().getResource(userImgPath).getPath()));
                userImg = userImg.getScaledInstance(frameHeight/10, frameHeight/10, Image.SCALE_SMOOTH);
                ImageIcon userImgIcon = new ImageIcon(userImg);
                JLabel userImgLabel = new JLabel();
                userImgLabel.setIcon(userImgIcon);
                infoPanel.add(Box.createRigidArea(new Dimension(menuButtonGap/2,0))); infoPanel.add(userImgLabel);
            } catch (Exception e) {
                ;
            }
        }
        infoPanel.add(Box.createHorizontalGlue());

        logOutButton = new LogOutButton(frameHeight/20, frameHeight/20, frameHeight/20, frameHeight/20);
        logOutButton.addActionListener(e -> logOutBtnClickedAction());
        infoPanel.add(logOutButton); infoPanel.add(Box.createRigidArea(new Dimension(frameHeight/40,0)));
        mainPanel.add(infoPanel);
        mainPanel.add(Box.createVerticalGlue());

        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.LINE_AXIS));
        buttonsPanel.setBackground(bgColor);
        buttonsRowsPanel = new JPanel();
        buttonsRowsPanel.setLayout(new BoxLayout(buttonsRowsPanel, BoxLayout.PAGE_AXIS));
        buttonsRowsPanel.setBackground(bgColor);
//        buttonsPanel.add(Box.createHorizontalGlue());
        buttonsPanel.add(Box.createRigidArea(new Dimension(menuButtonGap,0)));
        buttonsPanel.add(buttonsRowsPanel);

        if (userType.equals("Rider")){
            JPanel buttonsRow1 = new JPanel();
            buttonsRow1.setLayout(new BoxLayout(buttonsRow1, BoxLayout.LINE_AXIS));
            buttonsRow1.setBackground(bgColor);
            seeGroupsButton = new MenuButton("See groups", "/icons/training.png");
            seeGroupsButton.addActionListener(e->seeGroupAction());
            seeTrainingsButton = new MenuButton("See trainings", "/icons/training.png");
            seeTrainingsButton.addActionListener(e->seeTrainingsAction());
            seeTournamentsButton = new MenuButton("See tournamets", "/icons/tournament.png");
            seeTournamentsButton.addActionListener(e->seeTournamentsAction());
            buttonsRow1.add(seeGroupsButton); buttonsRow1.add(Box.createRigidArea(new Dimension(menuButtonGap,0)));
            buttonsRow1.add(seeTrainingsButton); buttonsRow1.add(Box.createHorizontalGlue());
            buttonsRow1.add(seeTournamentsButton); buttonsRow1.add(Box.createHorizontalGlue());

            JPanel buttonsRow2 = new JPanel();
            buttonsRow2.setLayout(new BoxLayout(buttonsRow2, BoxLayout.LINE_AXIS));
            buttonsRow2.setBackground(bgColor);
            seeLicenceButton = new MenuButton("See your licence", "/icons/licence.png");
            seeLicenceButton.addActionListener(e->seeLicenceAction());
            deactivateAccountButton = new MenuButton("Deactivate account", "/icons/deactivate.png");
            deactivateAccountButton.fillColor = statusWrongLighter;
            deactivateAccountButton.hoverColor = statusWrong;
            deactivateAccountButton.addActionListener(e->deactivateAccountAction());
            buttonsRow2.add(seeLicenceButton); buttonsRow2.add(Box.createRigidArea(new Dimension(menuButtonGap,0)));
            buttonsRow2.add(deactivateAccountButton); buttonsRow2.add(Box.createHorizontalGlue());

            buttonsRowsPanel.add(buttonsRow1); buttonsRowsPanel.add(Box.createVerticalGlue());
            buttonsRowsPanel.add(buttonsRow2); buttonsRowsPanel.add(Box.createVerticalGlue());
        } else if (userType.equals("Employee")) {
            JPanel buttonsRow1 = new JPanel();
            buttonsRow1.setLayout(new BoxLayout(buttonsRow1, BoxLayout.LINE_AXIS));
            buttonsRow1.setBackground(bgColor);
            seeEmployeesButton = new MenuButton("Employees", "/icons/employee.png");
            seeEmployeesButton.addActionListener(e->seeEmployeesAction());
            seePositionsButton = new MenuButton("Positions", "/icons/position.png");
            seePositionsButton.addActionListener(e->seePositionsAction());
            seeStablesButton = new MenuButton("Stables", "/icons/stable.png");
            seeStablesButton.addActionListener(e->seeStablesAction());

            if (doesEmployeeHaveReadOrWritePermissions("Employees", positionName)){
                buttonsRow1.add(seeEmployeesButton); buttonsRow1.add(Box.createRigidArea(new Dimension(menuButtonGap,0)));
            }
            if (doesEmployeeHaveReadOrWritePermissions("Positions", positionName)){
                buttonsRow1.add(seePositionsButton); buttonsRow1.add(Box.createRigidArea(new Dimension(menuButtonGap,0)));
            }
            if (doesEmployeeHaveReadOrWritePermissions("Stables", positionName)){
                buttonsRow1.add(seeStablesButton); buttonsRow1.add(Box.createRigidArea(new Dimension(menuButtonGap,0)));
            }
            buttonsRow1.add(Box.createHorizontalGlue());

            JPanel buttonsRow2 = new JPanel();
            buttonsRow2.setLayout(new BoxLayout(buttonsRow2, BoxLayout.LINE_AXIS));
            buttonsRow2.setBackground(bgColor);
            seeHorsesButton = new MenuButton("Horses", "/icons/horse.png");
            seeHorsesButton.addActionListener(e->seeHorsesAction());
            seeAccessoriesButton = new MenuButton("Accessories", "/icons/accessory.png");
            seeAccessoriesButton.addActionListener(e->seeAccessoriesAction());

            if (doesEmployeeHaveReadOrWritePermissions("Horses", positionName)){
                buttonsRow2.add(seeHorsesButton); buttonsRow2.add(Box.createRigidArea(new Dimension(menuButtonGap,0)));
            }
            if (doesEmployeeHaveReadOrWritePermissions("Accessories", positionName)){
                buttonsRow2.add(seeAccessoriesButton); buttonsRow2.add(Box.createRigidArea(new Dimension(menuButtonGap,0)));
            }
            buttonsRow2.add(Box.createHorizontalGlue());

            JPanel buttonsRow3 = new JPanel();
            buttonsRow3.setLayout(new BoxLayout(buttonsRow3, BoxLayout.LINE_AXIS));
            buttonsRow3.setBackground(bgColor);
            seeRidersButton = new MenuButton("Riders", "/icons/rider.png");
            seeRidersButton.addActionListener(e->seeRidersAction());
            seeGroupsButton = new MenuButton("Groups", "/icons/group.png");
            seeGroupsButton.addActionListener(e->seeGroupAction());
            seeTrainingsButton = new MenuButton("Trainings", "/icons/training.png");
            seeTrainingsButton.addActionListener(e->seeTrainingsAction());
            seeTournamentsButton = new MenuButton("Tournaments", "/icons/tournament.png");
            seeTournamentsButton.addActionListener(e->seeTournamentsAction());

            if (doesEmployeeHaveReadOrWritePermissions("Riders", positionName)){
                buttonsRow3.add(seeRidersButton); buttonsRow3.add(Box.createRigidArea(new Dimension(menuButtonGap,0)));
            }
            if (doesEmployeeHaveReadOrWritePermissions("Groups", positionName)){
                buttonsRow3.add(seeGroupsButton); buttonsRow3.add(Box.createRigidArea(new Dimension(menuButtonGap,0)));
            }
            if (doesEmployeeHaveReadOrWritePermissions("Trainings", positionName)){
                buttonsRow3.add(seeTrainingsButton); buttonsRow3.add(Box.createRigidArea(new Dimension(menuButtonGap,0)));
            }
            if (doesEmployeeHaveReadOrWritePermissions("Tournaments", positionName)){
                buttonsRow3.add(seeTournamentsButton); buttonsRow3.add(Box.createRigidArea(new Dimension(menuButtonGap,0)));
            }
            buttonsRow3.add(Box.createHorizontalGlue());
            
            buttonsRowsPanel.add(buttonsRow1); buttonsRowsPanel.add(Box.createVerticalGlue());
            buttonsRowsPanel.add(buttonsRow2); buttonsRowsPanel.add(Box.createVerticalGlue());
            buttonsRowsPanel.add(buttonsRow3); buttonsRowsPanel.add(Box.createVerticalGlue());
        }

        mainPanel.add(buttonsPanel);
        mainPanel.add(Box.createVerticalGlue());
    }

    void seeRidersAction() {
        new RidersScrollGUI(userId, userType).createGUI();
        frame.setVisible(false);
    }

    void seeAccessoriesAction() {
        new AccessoriesScrollGUI(userId, userType).createGUI();
        frame.setVisible(false);
    }

    void seeHorsesAction() {
        new HorsesScrollGUI(userId, userType).createGUI();
        frame.setVisible(false);
    }

    void seeStablesAction() {
        new StablesScrollGUI(userId, userType).createGUI();
        frame.setVisible(false);
    }

    void seePositionsAction() {
        new PositionsScrollGUI(userId, userType).createGUI();
        frame.setVisible(false);
    }

    void seeEmployeesAction() {
        new EmployeesScrollGUI(userId, userType).createGUI();
        frame.setVisible(false);
    }

	void seeGroupAction() {
        new GroupScrollGUI(userId, userType).createGUI();
        frame.setVisible(false);
    }

    void seeTrainingsAction() {
        new TrainingsScrollGUI(userId, userType).createGUI();
        frame.setVisible(false);
    }

    void seeTournamentsAction() {
        new TournamentsScrollGUI(userId, userType).createGUI();
        frame.setVisible(false);
    }

    void seeLicenceAction() {
        new LicenceGUI(userId, userType).createGUI();
        frame.setVisible(false);
    }

    void deactivateAccountAction() {

        String[] options = {"No", "Yes"};
        int pickedOption = JOptionPane.showOptionDialog(null, "This action is irreversible. Are you sure you want to continue?",
                "Confirm action", 0, 0, null, options, "No");

        if (pickedOption == 1) {
            List<Integer> errorCodes = new ArrayList<>();
            if (userType.equals("Rider")){
				try {
					deactivateAccount.deactivate(userId, true);
				} catch (Exception e) {
					e.printStackTrace();
				}
            } else if (userType.equals("Employee")){
				try {
					deactivateAccount.deactivate(userId, true);
				} catch (Exception e) {
					e.printStackTrace();
				}
            } else {
                errorCodes.add(-1);
            }

            if (errorCodes.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Account deactivated");
                new LogInGUI(-1, "None").createGUI();
                frame.setVisible(false);
            } else {
                String errorText = "";
                for (Integer errorCode : errorCodes) {
                    errorText = errorText + ErrorCodes.getErrorDescription(errorCode) + " ";
                }

                JOptionPane.showMessageDialog(frame, errorText);
            }
        }
    }

    void logOutBtnClickedAction(){
        new LogInGUI(-1, "None").createGUI();
        frame.setVisible(false);
    }

    protected boolean doesEmployeeHaveReadOrWritePermissions(String pageName, String positionName){
        String accessType = new TableAccessChecker().getAccessType(pageName, positionName);
        return accessType.equals("Read") || accessType.equals("Write");
    }

    class MenuButton extends TextIconButton {
        public MenuButton(String text, String imgPath){
            super(text, menuButtonWidth, menuButtonHeight, secondColor, secondColorDarker, fontButtons, false, imgPath, menuButtonHeight*2/3);
        }
    }

    public static void main(String[] args) {
//        new HomePageGUI(-1, "None").createGUI();
        new HomePageGUI(1, "Employee").createGUI();
//        new HomePageGUI(8, "Rider").createGUI();
    }
}
