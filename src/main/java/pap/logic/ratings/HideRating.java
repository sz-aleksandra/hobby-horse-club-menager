package pap.logic.ratings;

import pap.db.dao.RatingDAO;
import pap.db.entities.Rating;


public class HideRating {
    private final Rating rating;

    public HideRating(Rating rating) {
        this.rating = rating;
    }

    public void hide() {
        rating.setHidden(true);
        new RatingDAO().update(rating);
    }
}
