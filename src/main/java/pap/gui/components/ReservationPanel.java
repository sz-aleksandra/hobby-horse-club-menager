package pap.gui.components;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class ReservationPanel extends OfferReservationPanel {

    public ReservationPanel(Color bgColor, Font fontBigger, Font fontMiddle, Font fontMiddleBold, int offerWidth, int offerHeight,
                     String topPanelText, HashMap<String, String> detailsInfo, Image image) {
        super(bgColor, fontBigger, fontMiddle, fontMiddleBold, offerWidth, offerHeight, topPanelText, detailsInfo, image);
    }

    @Override
    void populateDetailsPanel(JPanel detailsPanel, HashMap<String, String> detailsInfo) {
        addJLabel(detailsInfo.get("name"), Color.BLACK, fontMiddle, detailsPanel);
        addJLabel(detailsInfo.get("people") + " people", Color.BLACK, fontMiddle, detailsPanel);
        addJLabel(detailsInfo.get("paid_amount"), Color.BLACK, fontMiddleBold, detailsPanel);
        Color statusColor; String statusText = detailsInfo.get("status");
        if (statusText.equals("Cancelled")) {
            statusColor = Color.decode("#9E2A2B");
        } else if (statusText.equals("Active")) {
            statusColor = Color.decode("#B57008");
        } else if (statusText.equals("Finished")) {
            statusColor = Color.decode("#2F750F");
        } else {
            statusColor = Color.BLACK;
        }
        addJLabel(statusText, statusColor, fontMiddleBold, detailsPanel);

    }
}
