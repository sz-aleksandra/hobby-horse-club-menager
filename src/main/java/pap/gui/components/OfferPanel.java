package pap.gui.components;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class OfferPanel extends OfferReservationPanel {

    public OfferPanel(Color bgColor, Font fontBigger, Font fontMiddle, Font fontMiddleBold, int offerWidth, int offerHeight,
                      String topPanelText, HashMap<String, String> detailsInfo, Image offerImg) {
        super(bgColor, fontBigger, fontMiddle, fontMiddleBold, offerWidth, offerHeight, topPanelText, detailsInfo, offerImg);
    }

    @Override
    void populateDetailsPanel(JPanel detailsPanel, HashMap<String, String> detailsInfo) {
        addJLabel("Room type: " + detailsInfo.get("room_type"), Color.BLACK, fontMiddle, detailsPanel);
        addJLabel("Rooms: " + detailsInfo.get("rooms"), Color.BLACK, fontMiddle, detailsPanel);
        addJLabel("Bathrooms: " + detailsInfo.get("bathrooms"), Color.BLACK, fontMiddle, detailsPanel);
        addJLabel(detailsInfo.get("price"), Color.decode("#9E2A2B"), fontMiddleBold, detailsPanel);
    }
}
