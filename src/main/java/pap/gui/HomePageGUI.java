package pap.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import pap.gui.DetailsView.LicenceGUI;
import pap.gui.SeeDataByScrolling.EmployeesScrollGUI;
import pap.gui.SeeDataByScrolling.TournamentsScrollGUI;
import pap.gui.SeeDataByScrolling.TrainingsScrollGUI;
import pap.gui.SignUpLogIn.LogInGUI;
import pap.gui.components.LogOutButton;
import pap.gui.components.LogoPanel;
import pap.gui.components.TextIconButton;
import pap.logic.DeactivateAccount;
import pap.logic.ErrorCodes;


public class HomePageGUI extends BaseGUI {

    MenuButton seeTrainingsButton, seeTournamentsButton, seeHorsesButton,
            seeStablesButton, seeLicenceButton, deactivateAccountButton, seeDataButton, addDataButton;
    JPanel mainPanel, buttonsPanel, buttonsRowsPanel, infoPanel;
    LogoPanel logoPanel;
    JLabel welcomeLabel;
    LogOutButton logOutButton;
    int menuButtonWidth = frameWidth*4/20; int menuButtonHeight = frameHeight*4/40;
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

        String name; String userImgPath = "";
        if (userType.equals("Rider")) {
            //ClientDAO cd = new ClientDAO();
            //name = cd.findById(userId).getName();
            name = "Ola"; //[MOCK]
            userImgPath = "/student.png";
        } else if (userType.equals("Employee")) {
            //OwnerDAO od = new OwnerDAO();
            //name = od.findById(userId).getCompanyName();
            name = "Bartek (Trainer)"; //[MOCK]
            userImgPath = "/business.png";
        } else {
            name = "User";
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
            seeTrainingsButton = new MenuButton("See trainings", "/icons/search_offers.png");
            seeTrainingsButton.addActionListener(e->seeTrainingsAction());
            seeTournamentsButton = new MenuButton("See tournamets", "/icons/reservations.png");
            seeTournamentsButton.addActionListener(e->seeTournamentsAction());
            buttonsRow1.add(seeTrainingsButton); buttonsRow1.add(Box.createRigidArea(new Dimension(menuButtonGap,0)));
            buttonsRow1.add(seeTournamentsButton); buttonsRow1.add(Box.createHorizontalGlue());

            JPanel buttonsRow2 = new JPanel();
            buttonsRow2.setLayout(new BoxLayout(buttonsRow2, BoxLayout.LINE_AXIS));
            buttonsRow2.setBackground(bgColor);
            seeLicenceButton = new MenuButton("See your licence", "/icons/payment.png");
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

            seeDataButton = new MenuButton("See, edit, remove data", "/icons/search_offers.png");
            seeDataButton.addActionListener(e->seeDataAction());
            addDataButton = new MenuButton("Add data", "/icons/search_offers.png");
            addDataButton.addActionListener(e->addDataAction());
            deactivateAccountButton = new MenuButton("Deactivate account", "/icons/deactivate.png");
            deactivateAccountButton.fillColor = statusWrongLighter; deactivateAccountButton.hoverColor = statusWrong;
            deactivateAccountButton.addActionListener(e->deactivateAccountAction());
            buttonsRow1.add(seeDataButton); buttonsRow1.add(Box.createRigidArea(new Dimension(menuButtonGap,0)));
            buttonsRow1.add(addDataButton); buttonsRow1.add(Box.createRigidArea(new Dimension(menuButtonGap,0)));
            buttonsRow1.add(deactivateAccountButton); buttonsRow1.add(Box.createHorizontalGlue());

            buttonsRowsPanel.add(buttonsRow1);
            buttonsRowsPanel.add(Box.createVerticalGlue());
        }

        mainPanel.add(buttonsPanel);
        mainPanel.add(Box.createVerticalGlue());
    }

    void seeTrainingsAction() {
        new TrainingsScrollGUI(userId, userType).createGUI();
        frame.setVisible(false);
    }

    void seeTournamentsAction() {
        new TournamentsScrollGUI(userId, userType).createGUI();
        frame.setVisible(false);
    }

    void seeHorsesAction() {
        //new ClientReservationHistoryGUI(userId, userType).createGUI();
        //frame.setVisible(false); [MOCK] [EXTRA]
    }

    void seeStablesAction() {
        //new ClientReservationHistoryGUI(userId, userType).createGUI();
        //frame.setVisible(false); [MOCK] [EXTRA]
    }

    void seeLicenceAction() {
        new LicenceGUI(userId, userType).createGUI();
        frame.setVisible(false);
    }

    void seeDataAction() {
        System.out.println("See trainings, See tournaments, See employees, See riders, See horses, See accessories, See positions, See position history, See stables, See addresses, See groups");
        new EmployeesScrollGUI(userId, userType).createGUI();
        frame.setVisible(false);
    }

    void addDataAction() {
        System.out.println("Add trainings, Add tournaments, Add employees, Add riders, Add horses, Add accessories, Add positions, Add stables, Add groups");
    }

    void deactivateAccountAction() {

        String[] options = {"No", "Yes"};
        int pickedOption = JOptionPane.showOptionDialog(null, "This action is irreversible. Are you sure you want to continue?",
                "Confirm action", 0, 0, null, options, "No");

        if (pickedOption == 1) {
            List<Integer> errorCodes = new ArrayList<>();
            if (userType.equals("Rider")){
                errorCodes = DeactivateAccount.deactivateClientAccount(userId); //[MOCK], zmienic na deaktywacje Ridera
            } else if (userType.equals("Employee")){
                errorCodes = DeactivateAccount.deactivateOwnerAccount(userId); // [MOCK], zmienic na deaktywacje Employee
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
