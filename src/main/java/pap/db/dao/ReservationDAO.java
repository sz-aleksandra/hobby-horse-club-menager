package pap.db.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pap.db.SessionFactoryMaker;
import pap.db.entities.Reservation;

import java.util.List;

public class ReservationDAO {
    SessionFactory factory = SessionFactoryMaker.getFactory();

    public void create(Reservation reservation) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.persist(reservation);
            session.getTransaction().commit();
            System.out.println("[ReservationDAO] Created reservation with id: " + reservation.getReservationId());
        }
    }

    public void createMany(Iterable<Reservation> reservations) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            for (Reservation reservation : reservations)
                session.persist(reservation);
            session.getTransaction().commit();
            for (Reservation reservation : reservations)
                System.out.println("[ReservationDAO] Created reservation with id: " + reservation.getReservationId());
        }
    }

    public void update(Reservation reservation) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.merge(reservation);
            session.getTransaction().commit();
            System.out.println("[ReservationDAO] Updated reservation with id: " + reservation.getReservationId());
        }
    }

    public void updateMany(Iterable<Reservation> reservations) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            for (Reservation reservation : reservations)
                session.merge(reservation);
            session.getTransaction().commit();
            for (Reservation reservation : reservations)
                System.out.println("[ReservationDAO] Updated reservation with id: " + reservation.getReservationId());
        }
    }

    public void delete(Reservation reservation) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.remove(reservation);
            session.getTransaction().commit();
            System.out.println("[ReservationDAO] Deleted reservation with id: " + reservation.getReservationId());
        }
    }

    public void deleteMany(Iterable<Reservation> reservations) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            for (Reservation reservation : reservations)
                session.remove(reservation);
            session.getTransaction().commit();
            for (Reservation reservation : reservations)
                System.out.println("[ReservationDAO] Deleted reservation with id: " + reservation.getReservationId());
        }
    }

    public List<Reservation> findAll() {
        try (Session session = factory.openSession()) {
            return session.createNativeQuery("select * from reservations", Reservation.class).list();
        }
    }

    public Reservation findById(int id) {
        try (Session session = factory.openSession()) {
            return session.find(Reservation.class, id);
        }
    }

    public List<Reservation> findByClientId(int id) {
        try (Session session = factory.openSession()) {
            return session.createNativeQuery("select * from reservations where client_id = " + id, Reservation.class).list();
        }
    }

    public List<Reservation> findByOfferId(int id) {
        try (Session session = factory.openSession()) {
            return session.createNativeQuery("select * from reservations where offer_id = " + id, Reservation.class).list();
        }
    }

    public List<Reservation> customQuery(String query) {
        try (Session session = factory.openSession()) {
            // it is not safe to use this method with user input
            return session.createNativeQuery(query, Reservation.class).list();
        }
    }
}
