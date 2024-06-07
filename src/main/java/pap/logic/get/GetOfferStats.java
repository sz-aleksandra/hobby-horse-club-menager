package pap.logic.get;

import org.hibernate.SessionFactory;
import pap.db.SessionFactoryMaker;
import pap.db.dao.RatingDAO;
import pap.db.entities.Offer;

public class GetOfferStats {
    private SessionFactory factory = SessionFactoryMaker.getFactory();
    private final Offer offer;
    public GetOfferStats(Offer offer) {
        this.offer = offer;
    }

    public Float getAverageRating() {
        return new RatingDAO().getAverageRatingForOffer(offer.getOfferId());
    }

    public Integer getNumberOfRatingsForOffer() {
        return new RatingDAO().getNumberOfRatingsForOffer(offer.getOfferId());
    }
}
