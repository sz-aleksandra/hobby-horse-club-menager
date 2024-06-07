package pap.logic.update;

import pap.db.dao.OfferDAO;
import pap.db.entities.Offer;

public class UpdateOffer {
    private final Offer offer;

    public UpdateOffer(Offer offer) {
        this.offer = offer;
    }

    public void update() {
        new OfferDAO().update(offer);
    }
}
