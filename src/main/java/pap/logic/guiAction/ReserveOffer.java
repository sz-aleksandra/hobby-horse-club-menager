package pap.logic.guiAction;

import pap.db.entities.Client;
import pap.db.entities.Offer;
import pap.logic.SearchOffers;
import java.util.HashMap;
import java.util.List;
import pap.logic.reservation.NewReservation;
import java.time.LocalDate;
import pap.db.dao.OfferDAO;
import pap.db.dao.ClientDAO;
import pap.db.entities.Offer;

import java.time.LocalDate;

public class ReserveOffer {
    LocalDate startDate, endDate;
    Offer offer;
    int userId;
    public ReserveOffer(LocalDate startDate, LocalDate endDate, Integer offerId, Integer userId ){
        this.startDate = startDate;
        this.endDate = endDate;
        this.userId = userId;
        this.offer = new OfferDAO().findById(offerId);
        reserve();
    }

    private void reserve() {
        new NewReservation(startDate, endDate, "-", offer.getPrice(), true, new ClientDAO().findById(userId), offer).insertIntoDatabase();
    }
}
