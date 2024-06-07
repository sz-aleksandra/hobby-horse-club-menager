package pap.logic.archiving;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pap.db.SessionFactoryMaker;
import pap.db.dao.HotelDAO;
import pap.db.dao.OfferDAO;
import pap.db.entities.Hotel;
import pap.db.entities.Offer;
import pap.db.entities.Reservation;

import java.util.List;

public class ArchiveHotel {
    private final Hotel hotel;
    SessionFactory factory = SessionFactoryMaker.getFactory();
    public ArchiveHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public boolean archive() {
        try (Session session = factory.openSession()) {
            String query = "select reservations.* from hotels join offers on hotels.hotel_id = offers.hotel_id join reservations on offers.offer_id = reservations.offer_id where hotels.hotel_id = '" + hotel.getHotelId() + "'";
            List<Reservation> reservationList = session.createNativeQuery(query, Reservation.class).list();
            if (!reservationList.isEmpty()) {
                return false;
            }
        }

        hotel.setActive(false);
        new HotelDAO().update(hotel);
        List<Offer> offers = new OfferDAO().findByHotelId(hotel.getHotelId());
        for (var offer : offers) {
            offer.setActive(false);
            new OfferDAO().update(offer);
        }
        return true;
    }
}
