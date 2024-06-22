package pap.gui.DetailsView;


import pap.gui.BaseGUI;
import pap.gui.HomePageGUI;
import pap.gui.components.*;
import pap.logic.guiAction.OfferDetails;
import pap.logic.reservation.ReservationFunctionality;
import pap.logic.validators.ReservationValidator;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;


public class ChangeReservationDateGUI extends BaseGUI {

    JPanel mainPanel;
    int offerId;

    public ChangeReservationDateGUI(int userId, String userType, Integer offerId) {
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
        ChangeReservationPanel changeReservationPanel = new ChangeReservationPanel(neutralGray, fontBigger, fontBiggerBold, fontMiddle,
                fontMiddleBold, fontSmaller, fontSmallerBold, frameWidth, frameHeight - logoPanelHeight - footerHeight - gap - gap2*2, offerInfo, userId, frame, userType, offerId);
        mainPanel.add(changeReservationPanel);


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

        RoundedButtonDefault changeButton = new RoundedButtonDefault("Change Reservation", frameWidth/5, frameHeight/10, false, false);
        changeButton.addActionListener(e-> changeReservationClickedAction(changeReservationPanel));
        footerPanel.add(changeButton);
        footerPanel.add(Box.createRigidArea(new Dimension(undoButtonSize/2, 0)));

        mainPanel.add(Box.createRigidArea(new Dimension(0,gap2)));
        mainPanel.add(footerPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0,gap2)));
    }

    void changeReservationClickedAction(ChangeReservationPanel panel){
        ReservationFunctionality reservationFunctionality = new ReservationFunctionality(panel.reservationId);
        reservationFunctionality.changeStatus("In progress.");
        ReservationValidator reservationValidator = new ReservationValidator(panel.getStartDate(), panel.getEndDate(), panel.getPickedCreditCard(), offerId);
        List <Integer> errors = reservationValidator.validate();
        if (errors.isEmpty()){
            showConfirmationDialog("Reservation changed successfully!");
            reservationFunctionality.changeStatus("Active");
            reservationFunctionality.changeDates(panel.startDate, panel.endDate);
            System.out.println("Changed Reservation!");
            frame.setVisible(false);
            new HomePageGUI(userId, userType).createGUI();
        }
        else {
            new ErrorWindow(errors);
            reservationFunctionality.changeStatus("Active");
        }
    }
    void undoBtnClickedAction() {
        //new OfferDetailsGUI(userId, userType, offerId).createGUI();
        frame.setVisible(false);
    }

    void changeReservationClickedAction(){
        return;
    }

    private void showConfirmationDialog(String message) {
        JOptionPane.showMessageDialog(null, message, "Reservation Confirmation", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        new ChangeReservationDateGUI(-1, "None", 1).createGUI();
    }
}


