package pap.logic.ratings;

import pap.db.dao.RatingDAO;
import pap.db.entities.Rating;
import pap.db.entities.Offer;
import pap.db.entities.Client;

import java.time.LocalDate;

public class AddRating {
    private final Rating rating;

    public AddRating(Offer offer, Client client, Integer rating, String comment, LocalDate date) {
        this.rating = new Rating();
        this.rating.setOffer(offer);
        this.rating.setClient(client);
        this.rating.setRating(rating);
        this.rating.setComment(comment);
        this.rating.setAddDate(date);
        this.rating.setHidden(false);
    }

    public void insertIntoDatabase() {
        new RatingDAO().create(rating);
    }
}
