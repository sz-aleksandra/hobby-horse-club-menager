package pap.logic.get;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pap.db.SessionFactoryMaker;
import pap.db.dao.ReservationDAO;
import pap.db.entities.Hotel;
import pap.db.entities.Offer;
import pap.db.entities.Reservation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class GetReservationHistory {
    private final SessionFactory factory = SessionFactoryMaker.getFactory();
    private final Hotel hotel;
    private final Offer offer;

    public GetReservationHistory(Offer offer) {
        this.offer = offer;
        this.hotel = null;
    }

    public GetReservationHistory(Hotel hotel) {
        this.offer = null;
        this.hotel = hotel;
    }

    public List<Reservation> getAllReservations() {
        if (offer == null) {
            assert hotel != null;
            String query = "select reservations.* from hotels join offers on (hotels.hotel_id = offers.hotel_id) join reservations on offers.offer_id = reservations.offer_id where hotels.hotel_id = '" +
                    hotel.getHotelId() + "'";
            return executeQuery(query);
        }
        return new ReservationDAO().findByOfferId(offer.getOfferId());
    }

    public List <Reservation> getAllPastReservations() {
        String query;
        if (offer == null) {
            assert hotel != null;
            query = "select reservations.* from hotels join offers on (hotels.hotel_id = offers.hotel_id) join reservations on offers.offer_id = reservations.offer_id where hotels.hotel_id = '" +
                    hotel.getHotelId() + "'" + " and lower(reservations.status) = 'finished'";
        } else {
            query = "select * from reservations where LOWER(status) = 'finished' AND offer_id = '" + offer.getOfferId() + "'";
        }
        return executeQuery(query);
    }

    public List <Reservation> getActiveReservations() {
        String query;
        if (offer == null) {
            assert hotel != null;
            query = "select reservations.* from hotels join offers on (hotels.hotel_id = offers.hotel_id) join reservations on offers.offer_id = reservations.offer_id where hotels.hotel_id = '" +
                    hotel.getHotelId() + "'" + " and lower(reservations.status) = 'active'";
        } else {
            query = "select * from reservations where LOWER(status) = 'active' AND offer_id = '" + offer.getOfferId() + "'";
        }
        return executeQuery(query);
    }

    public List <Reservation> getNotPaidReservations() {
        String query;
        if (offer == null) {
            assert hotel != null;
            query = "select reservations.* from hotels join offers on (hotels.hotel_id = offers.hotel_id) join reservations on offers.offer_id = reservations.offer_id where hotels.hotel_id = '" +
                    hotel.getHotelId() + "'" + " and reservations.is_paid = false";
        } else {
            query = "select * from reservations where is_paid = false AND offer_id = '" + offer.getOfferId() + "'";
        }
        return executeQuery(query);
    }

    public List <Reservation> getCancelledReservations() {
        String query;
        if (offer == null) {
            assert hotel != null;
            query = "select reservations.* from hotels join offers on (hotels.hotel_id = offers.hotel_id) join reservations on offers.offer_id = reservations.offer_id where hotels.hotel_id = '" +
                    hotel.getHotelId() + "'" + " and lower(reservations.status) = 'cancelled'";
        } else {
            query = "select * from reservations where LOWER(status) = 'cancelled' AND offer_id = '" + offer.getOfferId() + "'";
        }
        return executeQuery(query);
    }

    public List <Reservation> getReservationsBeforeDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = date.format(formatter);
        String query;
        if (offer == null) {
            assert hotel != null;
            query = "select reservations.* from hotels join offers on (hotels.hotel_id = offers.hotel_id) join reservations on offers.offer_id = reservations.offer_id where hotels.hotel_id = '" +
                    hotel.getHotelId() + "'" + " and reservations.end_date < '" + formattedDate + "'";
        } else {
            query = "select * from reservations where offer_id = '" + offer.getOfferId() + "'" + " and end_date < '" + formattedDate + "'";
        }

        return executeQuery(query);
    }

    public List <Reservation> getReservationsAfterDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = date.format(formatter);
        String query;
        if (offer == null) {
            assert hotel != null;
            query = "select reservations.* from hotels join offers on (hotels.hotel_id = offers.hotel_id) join reservations on offers.offer_id = reservations.offer_id where hotels.hotel_id = '" +
                    hotel.getHotelId() + "'" + " and and reservations.start_date > '" + formattedDate + "'";
        } else {
            query = "select * from reservations where offer_id = '" + offer.getOfferId() + "'" + " and start_date > '" + formattedDate + "'";
        }
        return executeQuery(query);
    }

    public List <Reservation> getReservationsAbovePrice(Integer price) {
        String query;
        if (offer == null) {
            assert hotel != null;
            query = "select reservations.* from hotels join offers on (hotels.hotel_id = offers.hotel_id) join reservations on offers.offer_id = reservations.offer_id where hotels.hotel_id = '" +
                    hotel.getHotelId() + "'" + " and reservations.paid_amount > '" + price + "'";
        } else {
            query = "select * from reservations where offer_id = '" + offer.getOfferId() + "'" + " and paid_amount > '" + price + "'";
        }
        return executeQuery(query);
    }

    private List <Reservation> executeQuery(String query) {
        List <Reservation> reservations;
        try (Session session = factory.openSession()) {
            reservations = session.createNativeQuery(query, Reservation.class).list();
        }
        return reservations;
    }
}
