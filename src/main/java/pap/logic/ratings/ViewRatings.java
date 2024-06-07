package pap.logic.ratings;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pap.db.SessionFactoryMaker;
import pap.db.entities.Hotel;
import pap.db.entities.Offer;
import pap.db.entities.Rating;

import java.util.List;

public class ViewRatings {
    private final SessionFactory factory = SessionFactoryMaker.getFactory();
    private final Hotel hotel;
    private final Offer offer;
    private final boolean ownerView; // if true shows also hidden ratings

    public ViewRatings(boolean ownerView) {
        this.hotel = null;
        this.offer = null;
        this.ownerView = ownerView;
    }

    public ViewRatings(Offer offer, boolean ownerView) {
        this.offer = offer;
        this.hotel = null;
        this.ownerView = ownerView;
    }

    public ViewRatings(Hotel hotel, boolean ownerView) {
        this.hotel = hotel;
        this.offer = null;
        this.ownerView = ownerView;
    }

    public List <Rating> getAllRatings() {
        List <Rating> ratings;
        String query = prepareGetAllQuery();
        try (Session session = factory.openSession()) {
            ratings = session.createNativeQuery(query, Rating.class).list();
        }
        return ratings;
    }
    public List<Rating> getAllRatingsOrderByNewest() {
        List <Rating> ratings;
        String query = prepareGetAllQuery();
        query += " order by date desc";
        try (Session session = factory.openSession()) {
            ratings = session.createNativeQuery(query, Rating.class).list();
        }
        return ratings;
    }

    public List <Rating> getAllRatingsOrderByRating() {
        List <Rating> ratings;
        String query = prepareGetAllQuery();
        query += " order by rating desc";
        try (Session session = factory.openSession()) {
            ratings = session.createNativeQuery(query, Rating.class).list();
        }
        return ratings;
    }

    public List <Rating> getAllRatingsWithRating(int rating) {
        assert rating >= 1 && rating <= 5;
        List <Rating> ratings;
        String query;
        if (offer != null) {
            query = "select * from ratings where offer_id = '" + offer.getOfferId() + "' and rating = '" + rating + "'";
        } else if (hotel != null) {
            query = "select ratings.* from hotels join offers on (hotels.hotel_id = offers.hotel_id) join ratings on (offers.offer_id = ratings.offer_id) where hotels.hotel_id = '" + hotel.getHotelId() + "' and rating = '" + rating + "'";
        } else {
            query = "select * from ratings where rating = '" + rating + "'";
        }
        try (Session session = factory.openSession()) {
            ratings = session.createNativeQuery(query, Rating.class).list();
        }
        return ratings;
    }

    private String prepareGetAllQuery() {
        String query;
        if (offer != null) {
            query = "select * from ratings where offer_id = '" + offer.getOfferId() + "'";
        } else if (hotel != null) {
            query = "select ratings.* from hotels join offers on (hotels.hotel_id = offers.hotel_id) join ratings on (offers.offer_id = ratings.offer_id) where hotels.hotel_id = '" + hotel.getHotelId() + "'";
        } else {
            query = "select * from ratings";
        }
        if (!ownerView) {
            query += " and is_hidden = false";
        }
        return query;
    }
}
