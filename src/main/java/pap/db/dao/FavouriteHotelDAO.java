package pap.db.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pap.db.SessionFactoryMaker;
import pap.db.entities.FavouriteHotel;

import java.util.List;

public class FavouriteHotelDAO {
    SessionFactory factory = SessionFactoryMaker.getFactory();

    public void create(FavouriteHotel FavouriteHotel) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.persist(FavouriteHotel);
            session.getTransaction().commit();
            System.out.println("[FavouriteHotelDAO] Created favourite hotels entry for client: " + FavouriteHotel.getClient().getClientId() +
                    " and hotel: " + FavouriteHotel.getHotel().getHotelId());
        }
    }

    public void createMany(Iterable<FavouriteHotel> FavouriteHotelList) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            for (FavouriteHotel FavouriteHotel : FavouriteHotelList) {
                session.persist(FavouriteHotel);
                System.out.println("[FavouriteHotelDAO] Created favourite hotels entry for client: " +
                        FavouriteHotel.getClient().getClientId() + " and hotel: " + FavouriteHotel.getHotel().getHotelId());
            }
            session.getTransaction().commit();
        }
    }

    public void delete(FavouriteHotel FavouriteHotel) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.remove(FavouriteHotel);
            session.getTransaction().commit();
            System.out.println("[FavouriteHotelDAO] Deleted favourite hotels entry for client: " +
                    FavouriteHotel.getClient().getClientId() + " and hotel: " + FavouriteHotel.getHotel().getHotelId());
        }
    }

    public void deleteMany(Iterable<FavouriteHotel> FavouriteHotelList) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            for (FavouriteHotel FavouriteHotel : FavouriteHotelList) {
                session.remove(FavouriteHotel);
                System.out.println("[FavouriteHotelDAO] Deleted favourite hotels entry for client: " +
                        FavouriteHotel.getClient().getClientId() + " and hotel: " + FavouriteHotel.getHotel().getHotelId());
            }
            session.getTransaction().commit();
        }
    }

    public void update(FavouriteHotel FavouriteHotel) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.merge(FavouriteHotel);
            session.getTransaction().commit();
            System.out.println("[FavouriteHotelDAO] Updated favourite hotels entry for client: " +
                    FavouriteHotel.getClient().getClientId() + " and hotel: " + FavouriteHotel.getHotel().getHotelId());
        }
    }

    public void updateMany(Iterable<FavouriteHotel> FavouriteHotelList) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            for (FavouriteHotel FavouriteHotel : FavouriteHotelList) {
                session.merge(FavouriteHotel);
                System.out.println("[FavouriteHotelDAO] Updated favourite hotels entry for client: " +
                        FavouriteHotel.getClient().getClientId() + " and hotel: " + FavouriteHotel.getHotel().getHotelId());
            }
            session.getTransaction().commit();
        }
    }

    public FavouriteHotel findById(int id) {
        try (Session session = factory.openSession()) {
            return session.get(FavouriteHotel.class, id);
        }
    }

    public List<FavouriteHotel> findAllClientFavourite(int clientId) {
        try (Session session = factory.openSession()) {
            return session.createNativeQuery("SELECT * FROM favourite_hotels WHERE client_id = :clientId", FavouriteHotel.class)
                    .setParameter("clientId", clientId)
                    .list();
        }
    }

    public int findQuantityForHotel(int hotelId) {
        try (Session session = factory.openSession()) {
            Long result = (Long) session.createNativeQuery("SELECT COUNT(*) FROM favourite_hotels WHERE hotel_id = :hotelId")
                    .setParameter("hotelId", hotelId)
                    .uniqueResult();
            return result != null ? result.intValue() : 0;
        }
    }

    public int findNumberOfFavouritesForClient(int clientId) {
        try (Session session = factory.openSession()) {
            Long result = (Long) session.createNativeQuery("SELECT COUNT(*) FROM favourite_hotels WHERE client_id = :clientId")
                    .setParameter("clientId", clientId)
                    .uniqueResult();
            return result != null ? result.intValue() : 0;
        }
    }
}
