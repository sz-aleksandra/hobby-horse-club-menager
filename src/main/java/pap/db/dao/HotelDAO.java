package pap.db.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pap.db.SessionFactoryMaker;
import pap.db.entities.Hotel;

import java.util.List;

public class HotelDAO {
    SessionFactory factory = SessionFactoryMaker.getFactory();

    public void create(Hotel hotel) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.persist(hotel);
            session.getTransaction().commit();
            System.out.println("[HotelDAO] Created hotel with id: " + hotel.getHotelId());
        }
    }

    public void createWithNewAddress(Hotel hotel) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.persist(hotel.getAddress());
            session.persist(hotel);
            session.getTransaction().commit();
            System.out.println("[HotelDAO] Created address with id: " + hotel.getAddress().getAddressId());
            System.out.println("[HotelDAO] Created hotel with id: " + hotel.getHotelId());
        }
    }

    public void createMany(Iterable<Hotel> hotels) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            for (Hotel hotel : hotels)
                session.persist(hotel);
            session.getTransaction().commit();
            for (Hotel hotel : hotels)
                System.out.println("[HotelDAO] Created hotel with id: " + hotel.getHotelId());
        }
    }

    public void update(Hotel hotel) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.merge(hotel);
            session.getTransaction().commit();
            System.out.println("[HotelDAO] Updated hotel with id: " + hotel.getHotelId());
        }
    }

    public void updateMany(Iterable<Hotel> hotels) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            for (Hotel hotel : hotels)
                session.merge(hotel);
            session.getTransaction().commit();
            for (Hotel hotel : hotels)
                System.out.println("[HotelDAO] Updated hotel with id: " + hotel.getHotelId());
        }
    }

    public void delete(Hotel hotel) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.remove(hotel);
            session.getTransaction().commit();
            System.out.println("[HotelDAO] Deleted hotel with id: " + hotel.getHotelId());
        }
    }

    public void deleteMany(Iterable<Hotel> hotels) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            for (Hotel hotel : hotels)
                session.remove(hotel);
            session.getTransaction().commit();
            for (Hotel hotel : hotels)
                System.out.println("[HotelDAO] Deleted hotel with id: " + hotel.getHotelId());
        }
    }

    public Hotel findById(int id) {
        try (Session session = factory.openSession()) {
            return session.find(Hotel.class, id);
        }
    }

    public Hotel findByNameAndOwnerId(String name, Integer id) {
        try (Session session = factory.openSession()) {
            return session.createNativeQuery("SELECT * FROM hotels WHERE name = :name AND owner_id = :ownerId", Hotel.class)
                    .setParameter("name", name)
                    .setParameter("owner_id", id)
                    .uniqueResult();
        }
    }

    public List<Hotel> findByOwnerId (int id) {
        try (Session session = factory.openSession()) {
            return session.createNativeQuery("select * from hotels where owner_id = " + id, Hotel.class).list();
        }
    }

    public List<Hotel> customQuery(String query) {
        try (Session session = factory.openSession()) {
            // it is not safe to use this method with user input
            return session.createNativeQuery(query, Hotel.class).list();
        }
    }
}
