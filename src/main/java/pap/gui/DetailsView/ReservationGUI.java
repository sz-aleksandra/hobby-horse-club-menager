package pap.gui.DetailsView;


import pap.gui.BaseGUI;
import pap.gui.HomePageGUI;
import pap.gui.components.*;
import pap.logic.guiAction.OfferDetails;
import pap.logic.guiAction.ReserveOffer;
import pap.logic.validators.ReservationValidator;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;

public class ReservationGUI extends BaseGUI {

    JPanel mainPanel;
    int offerId;

    public ReservationGUI(int userId, String userType, Integer offerId) {
        super(userId, userType);
        this.offerId = offerId;
    }

    protected void createCustomGUI() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        mainPanel.setBackground(bgColor);
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);

        int logoPanelHeight = frameHeight / 10; int footerHeight = frameHeight/10;
        int gap = frameHeight/30; int gap2 = frameHeight/60;
        LogoPanel logoPanel = new LogoPanel(logoColor, frameHeight, frameWidth, logoPanelHeight);
        mainPanel.add(logoPanel);

        mainPanel.add(Box.createRigidArea(new Dimension(0, gap)));
        OfferDetails offerDetails = new OfferDetails();
        HashMap<String, String> offerInfo = offerDetails.getOfferInfo(offerId);
        // Should be info passed to this class's constructor - hashmap<String,String>, which will be later passed to payment view
        HashMap<String, String> reservationInfo = new HashMap<>();
        MakeReservationPanel reservationPanel = new MakeReservationPanel(neutralGray, fontBigger, fontBiggerBold, fontMiddle,
                fontMiddleBold, fontSmaller, fontSmallerBold, frameWidth, frameHeight - logoPanelHeight - footerHeight - gap - gap2*2,
                offerInfo, reservationInfo, userId, frame, userType, offerId);
        mainPanel.add(reservationPanel);

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

        RoundedButtonDefault reserveButton = new RoundedButtonDefault("Complete Reservation", frameWidth/5, frameHeight/10, false, false);
        reserveButton.addActionListener(e-> makeReservationClickedAction(reservationPanel));
        footerPanel.add(reserveButton);
        footerPanel.add(Box.createRigidArea(new Dimension(undoButtonSize/2, 0)));

        mainPanel.add(Box.createRigidArea(new Dimension(0,gap2)));
        mainPanel.add(footerPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0,gap2)));
    }

    void undoBtnClickedAction() {
        //new OfferDetailsGUI(userId, userType, offerId).createGUI();
        frame.setVisible(false);
    }

    void makeReservationClickedAction(MakeReservationPanel panel){
        ReservationValidator reservationValidator = new ReservationValidator(panel.getStartDate(), panel.getEndDate(), panel.getPickedCreditCard(), offerId);
        List <Integer> errors = reservationValidator.validate();
        if (errors.isEmpty()){
            showConfirmationDialog("Reservation made successfully!");
            new ReserveOffer(panel.getStartDate(), panel.getEndDate(), offerId, userId);
            System.out.println("Made Reservation!");
            frame.setVisible(false);
            new HomePageGUI(userId, userType).createGUI();
        }
        else {
            new ErrorWindow(errors);
        }
    }

    private void showConfirmationDialog(String message) {
        JOptionPane.showMessageDialog(null, message, "Reservation Confirmation", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        new ReservationGUI(-1, "None", 1).createGUI();
    }
}
