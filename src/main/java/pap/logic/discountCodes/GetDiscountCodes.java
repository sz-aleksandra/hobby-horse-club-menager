package pap.logic.discountCodes;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pap.db.SessionFactoryMaker;
import pap.db.dao.DiscountDAO;
import pap.db.entities.*;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.util.List;


public class GetDiscountCodes {
    SessionFactory factory = SessionFactoryMaker.getFactory();
    public Discount findByCode(String code) {
        return new DiscountDAO().findByCode(code);
    }

    public List<Discount> getDiscountsForOffer(Offer offer) {
        List <Discount> discounts;
        String query = "SELECT discounts.* FROM discounts JOIN offers ON discounts.hotel_id = offers.hotel_id WHERE offers.offer_id = :offer_id AND discounts.is_active = true" +
                " union SELECT * FROM discounts where hotel_id = 0 AND is_active = true";
        try (Session session = factory.openSession()) {
            discounts = session.createNativeQuery(query, Discount.class)
                    .setParameter("offer_id", offer.getOfferId())
                    .list();
        }
        return discounts;
    }

    public List <Discount> getDiscountsForHotel(Hotel hotel) {
        List <Discount> discounts;
        String query = "SELECT * FROM discounts WHERE hotel_id IN (:hotel_id, 0) AND is_active = true";
        try (Session session = factory.openSession()) {
            discounts = session.createNativeQuery(query, Discount.class)
                    .setParameter("hotel_id", hotel.getHotelId())
                    .list();
        }
        return discounts;
    }
}
