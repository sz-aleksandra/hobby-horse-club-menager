package pap.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;


public abstract class BaseGUI {
    protected JFrame frame;
    protected JMenuBar mb;
    protected JMenu FileMenu, EditMenu, HelpMenu;
    protected JMenuItem NewMenuItem, UndoMenuItem, ContactMenuItem;
    protected int frameWidth = 1080; protected int frameHeight = 720;
    protected Color bgColor = Color.decode("#fff2e1");
    protected Color logoColor = Color.decode("#584040");
    protected Color secondColor = Color.decode("#36c199"), secondColorDarker = Color.decode("#34866f");
    protected Color neutralBlue = Color.decode("#F4DEBF"), neutralGray = Color.decode("#C89B88");
    protected Color statusNeutral = Color.decode("#7a7373");
    protected Color statusWrong = Color.decode("#952123"), statusWrongLighter = Color.decode("#a84b4c");
    protected Font fontBigger, fontMiddle, fontSmaller, fontButtons, fontBiggerBold, fontMiddleBold, fontSmallerBold;
    protected int userId = -1; protected String userType = "None";

    void createFrame(){
        frame = new JFrame("Reservation System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(frameWidth, frameHeight);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            SwingUtilities.updateComponentTreeUI(frame);
        } catch (Exception e) {
            ;
        }
    }

    void createMenus(){
        mb = new JMenuBar();
        FileMenu = new JMenu("File");
        EditMenu = new JMenu("Edit");
        HelpMenu = new JMenu("Help");
        mb.add(FileMenu); mb.add(EditMenu); mb.add(HelpMenu);
        NewMenuItem = new JMenuItem("New");
        UndoMenuItem = new JMenuItem("Undo");
        ContactMenuItem = new JMenuItem("Contact us");
        FileMenu.add(NewMenuItem); EditMenu.add(UndoMenuItem); HelpMenu.add(ContactMenuItem);
        frame.getContentPane().add(BorderLayout.PAGE_START, mb);
    }

    void setFonts(){
        try {
            File fontFile = new File(getClass().getResource("/Montserrat-Regular.ttf").getPath());
            Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            fontBigger = font.deriveFont(20f);
            fontMiddle = font.deriveFont(18f);
            fontSmaller = font.deriveFont(16f);
            fontFile = new File(getClass().getResource("/Montserrat-Bold.ttf").getPath());
            font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            fontButtons = font.deriveFont(12f);
            fontBiggerBold = font.deriveFont(20f);
            fontMiddleBold = font.deriveFont(18f);
            fontSmallerBold = font.deriveFont(16f);

        } catch (java.awt.FontFormatException | java.io.IOException ex) {
            fontBigger = new JLabel().getFont().deriveFont(20f);
            fontMiddle = new JLabel().getFont().deriveFont(18f);
            fontSmaller = new JLabel().getFont().deriveFont(16f);
            fontButtons = new JLabel().getFont().deriveFont(Font.BOLD, 12f);
        }
    }

    void createBaseGUI(){
        setFonts();
        createFrame();
        createMenus();
    }

    protected abstract void createCustomGUI();

    public void createGUI(){
        createBaseGUI();
        createCustomGUI();
        frame.setVisible(true);
    };

    public BaseGUI(int userId, String userType){
        this.userId = userId;
        this.userType = userType;
    }

    public static void main(String[] args) {
    }
}
