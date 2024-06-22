package pap.gui.SignUpLogIn;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import pap.gui.BaseGUI;
import pap.gui.HomePageGUI;
import pap.gui.components.LogoPanel;
import pap.gui.components.RoundedButtonDefault;
import pap.logic.ErrorCodes;


public class LogInGUI extends BaseGUI {

    JPanel mainPanel, centerPanel, loginPanel, createAccountPanel;
    LogoPanel logoPanel;
    JLabel usernameLabel, createAccountLabel, passwordLabel, statusLabel;
    JTextField usernameInputField;
    JPasswordField passwordInputField;
    RoundedButtonDefault logInRiderButton, logInEmployeeButton, createAccountButton;

    public LogInGUI(int userId, String userType) {
        super(userId, userType);
    }

    protected void createCustomGUI(){
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        mainPanel.setBackground(bgColor);
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);

        int btnHeight = frameHeight/10;
        int loginPanelWidth = frameWidth/3;
        int loginPanelHeight = frameHeight - frameHeight/5 - 2*btnHeight;

        logoPanel = new LogoPanel(logoColor, frameHeight,Integer.MAX_VALUE, frameHeight/5);
        mainPanel.add(logoPanel);
//        mainPanel.add(Box.createRigidArea(new Dimension(0,frameHeight*3/20)));

        centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.LINE_AXIS));
        centerPanel.setPreferredSize(new Dimension(frameWidth, loginPanelHeight));
        centerPanel.setMaximumSize(new Dimension(frameWidth, loginPanelHeight));
        centerPanel.setBackground(bgColor);
        mainPanel.add(centerPanel);
        loginPanel = new JPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.PAGE_AXIS));
        loginPanel.setPreferredSize(new Dimension(loginPanelWidth, loginPanelHeight));
        loginPanel.setMaximumSize(new Dimension(loginPanelWidth, loginPanelHeight));
        loginPanel.setBackground(bgColor);
        loginPanel.add(Box.createVerticalGlue());

        usernameLabel = new JLabel("Username:", JLabel.CENTER);
        usernameLabel.setFont(fontMiddle);
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.LINE_AXIS));
        textPanel.setBackground(bgColor);
        textPanel.add(Box.createHorizontalGlue()); textPanel.add(usernameLabel); textPanel.add(Box.createHorizontalGlue());
        loginPanel.add(textPanel);
        loginPanel.add(Box.createRigidArea(new Dimension(0,10)));

        usernameInputField = new JTextField();
        usernameInputField.setFont(fontMiddle);
        usernameInputField.setPreferredSize(new Dimension(frameWidth, 30));
        usernameInputField.setMaximumSize(new Dimension(frameWidth, 30));
        loginPanel.add(usernameInputField);
        loginPanel.add(Box.createRigidArea(new Dimension(0,20)));

        passwordLabel = new JLabel("Password:", JLabel.CENTER);
        passwordLabel.setFont(fontMiddle);
        JPanel textPanel2 = new JPanel();
        textPanel2.setLayout(new BoxLayout(textPanel2, BoxLayout.LINE_AXIS));
        textPanel2.setBackground(bgColor);
        textPanel2.add(Box.createHorizontalGlue()); textPanel2.add(passwordLabel); textPanel2.add(Box.createHorizontalGlue());
        loginPanel.add(textPanel2);
        loginPanel.add(Box.createRigidArea(new Dimension(0,10)));

        passwordInputField = new JPasswordField();
        passwordInputField.setEchoChar('•');
        passwordInputField.setFont(fontMiddle);
        passwordInputField.setPreferredSize(new Dimension(frameWidth, 30));
        passwordInputField.setMaximumSize(new Dimension(frameWidth, 30));
        loginPanel.add(passwordInputField);
        loginPanel.add(Box.createRigidArea(new Dimension(0,20)));

        centerPanel.add(Box.createHorizontalGlue()); centerPanel.add(loginPanel); centerPanel.add(Box.createHorizontalGlue());

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.LINE_AXIS));
        buttonsPanel.setPreferredSize(new Dimension(loginPanelWidth, btnHeight));
        buttonsPanel.setMaximumSize(new Dimension(loginPanelWidth, btnHeight));
        buttonsPanel.setBackground(bgColor);
        loginPanel.add(buttonsPanel);

        logInRiderButton = new RoundedButtonDefault("Log in as Rider", frameWidth*3/20, btnHeight, false, false);
        logInRiderButton.addActionListener(e->logInRiderClickedAction());
        buttonsPanel.add(logInRiderButton);
        buttonsPanel.add(Box.createRigidArea(new Dimension(20,0)));
        logInEmployeeButton = new RoundedButtonDefault("Log in as Employee", frameWidth*3/20, btnHeight, false, false);
        logInEmployeeButton.addActionListener(e->logInEmployeeClickedAction());
        buttonsPanel.add(logInEmployeeButton);

        statusLabel = new JLabel("<html>Insert your data</html>", JLabel.CENTER);
        statusLabel.setFont(fontSmaller);
        statusLabel.setForeground(statusNeutral);
        JPanel textPanel3 = new JPanel();
        textPanel3.setLayout(new BoxLayout(textPanel3, BoxLayout.LINE_AXIS));
        textPanel3.setBackground(bgColor);
        textPanel3.add(Box.createHorizontalGlue());
        textPanel3.add(statusLabel);
        textPanel3.add(Box.createHorizontalGlue());
        loginPanel.add(Box.createRigidArea(new Dimension(0,10)));
        loginPanel.add(textPanel3);
        loginPanel.add(Box.createVerticalGlue());

        createAccountPanel = new JPanel();
        createAccountPanel.setLayout(new BoxLayout(createAccountPanel, BoxLayout.LINE_AXIS));
        createAccountPanel.setBackground(bgColor);
        createAccountPanel.add(Box.createHorizontalGlue());
        createAccountLabel = new JLabel("I don't have an account...", JLabel.CENTER);
        createAccountLabel.setFont(fontMiddle);
        createAccountPanel.add(createAccountLabel);
        createAccountPanel.add(Box.createRigidArea(new Dimension(10,0)));
        createAccountButton = new RoundedButtonDefault("Create account", frameWidth*3/20, btnHeight, false, false);
        createAccountButton.addActionListener(e->createAccountBtnClickedAction());
        createAccountPanel.add(createAccountButton);
        createAccountPanel.add(Box.createRigidArea(new Dimension(10,0)));
        mainPanel.add(createAccountPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0,10)));

    }

    void logInRiderClickedAction() {
        String usernameText = usernameInputField.getText();
        String passwordText = passwordInputField.getText();

        String statusLabelText = "<html>Please wait...</html>";
        statusLabel.setText(statusLabelText);
        statusLabel.setForeground(statusNeutral);
        statusLabel.paintImmediately(statusLabel.getVisibleRect());

        // [MOCK], logging in as default Rider
        /*ClientLogin ul = new ClientLogin(usernameText, passwordText);
        Client user = ul.getUserAccount();
        List<Integer> errorCodesUser = ul.getErrorCodes();*/
        List<Integer> errorCodesUser = new ArrayList<>();

        // Successful log in
        if (errorCodesUser.isEmpty()) {
            //[MOCK]
            //new HomePageGUI(user.getClientId(), "Rider").createGUI();
            new HomePageGUI(1, "Rider").createGUI();
            frame.setVisible(false);
        } else {
            statusLabelText = "<html>";
            statusLabelText = statusLabelText + ErrorCodes.getErrorDescription(errorCodesUser.get(0));
            statusLabelText = statusLabelText + "</html>";
            statusLabel.setText(statusLabelText);
            statusLabel.setForeground(statusWrong);
            statusLabel.paintImmediately(statusLabel.getVisibleRect());
        }
    }

    void logInEmployeeClickedAction() {
        String usernameText = usernameInputField.getText();
        String passwordText = passwordInputField.getText();

        String statusLabelText = "<html>Please wait...</html>";
        statusLabel.setText(statusLabelText);
        statusLabel.setForeground(statusNeutral);
        statusLabel.paintImmediately(statusLabel.getVisibleRect());

        // [MOCK], logging in as default Rider
        /*OwnerLogin ol = new OwnerLogin(usernameText, passwordText);
        Owner owner = ol.getOwnerAccount();
        List<Integer> errorCodesEmployee = ol.getErrorCodes();*/
        List<Integer> errorCodesEmployee = new ArrayList<>();

        if (errorCodesEmployee.isEmpty()) {
            // new HomePageGUI(owner.getOwnerId(), "Employee").createGUI();
            //[MOCK]
            if (usernameText.equals("1")) {
                new HomePageGUI(1, "Employee").createGUI();
            } else if (usernameText.equals("2")) {
                new HomePageGUI(2, "Employee").createGUI();
            } else {
                new HomePageGUI(3, "Employee").createGUI();
            }

            frame.setVisible(false);
        } else {
            statusLabelText = "<html>";
            statusLabelText = statusLabelText + ErrorCodes.getErrorDescription(errorCodesEmployee.get(0));
            statusLabelText = statusLabelText + "</html>";
            statusLabel.setText(statusLabelText);
            statusLabel.setForeground(statusWrong);
            statusLabel.paintImmediately(statusLabel.getVisibleRect());
        }
    }

    void createAccountBtnClickedAction() {
        new ChooseAccountTypeGUI(-1, "None").createGUI();
        frame.setVisible(false);
    }

    public static void main(String[] args) {
        new LogInGUI(-1, "None").createGUI();
    }
}
