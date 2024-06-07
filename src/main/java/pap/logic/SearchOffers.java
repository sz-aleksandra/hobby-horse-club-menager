package pap.logic;
import java.util.*;
import pap.db.entities.*;
import pap.db.dao.*;


public class SearchOffers {
    public static List <Offer> getAllOffers() {
        return new OfferDAO().findAll();
    }

    public static Offer getOfferById(int id) {
        return new OfferDAO().findById(id);
    }

    public static List <Offer> getOfferByHotelId(int id) {
        return new OfferDAO().findByHotelId(id);
    }
}
