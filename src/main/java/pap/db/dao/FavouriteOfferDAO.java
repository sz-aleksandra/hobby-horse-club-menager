package pap.db.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pap.db.SessionFactoryMaker;
import pap.db.entities.FavouriteOffer;

import java.util.List;

public class FavouriteOfferDAO {
    SessionFactory factory = SessionFactoryMaker.getFactory();

    public void create(FavouriteOffer favouriteOffer) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.persist(favouriteOffer);
            session.getTransaction().commit();
            System.out.println("[FavouriteOfferDAO] Created favourite offer entry for client: " +
                    favouriteOffer.getClient().getClientId() + " and offer: " + favouriteOffer.getOffer().getOfferId());
        }
    }

    public void createMany(Iterable<FavouriteOffer> favouriteOffersList) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            for (FavouriteOffer favouriteOffer : favouriteOffersList) {
                session.persist(favouriteOffer);
                System.out.println("[FavouriteOfferDAO] Created favourite offer entry for client: " +
                        favouriteOffer.getClient().getClientId() + " and offer: " + favouriteOffer.getOffer().getOfferId());
            }
            session.getTransaction().commit();
        }
    }

    public void delete(FavouriteOffer favouriteOffer) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.remove(favouriteOffer);
            session.getTransaction().commit();
            System.out.println("[FavouriteOfferDAO] Deleted favourite offer entry for client: " +
                    favouriteOffer.getClient().getClientId() + " and offer: " + favouriteOffer.getOffer().getOfferId());
        }
    }

    public void deleteMany(Iterable<FavouriteOffer> favouriteOffersList) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            for (FavouriteOffer favouriteOffer : favouriteOffersList) {
                session.remove(favouriteOffer);
                System.out.println("[FavouriteOfferDAO] Deleted favourite offer entry for client: " +
                        favouriteOffer.getClient().getClientId() + " and offer: " + favouriteOffer.getOffer().getOfferId());
            }
            session.getTransaction().commit();
        }
    }

    public void update(FavouriteOffer favouriteOffer) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.merge(favouriteOffer);
            session.getTransaction().commit();
            System.out.println("[FavouriteOfferDAO] Updated favourite offer entry for client: " +
                    favouriteOffer.getClient().getClientId() + " and offer: " + favouriteOffer.getOffer().getOfferId());
        }
    }

    public void updateMany(Iterable<FavouriteOffer> favouriteOffersList) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            for (FavouriteOffer favouriteOffer : favouriteOffersList) {
                session.merge(favouriteOffer);
                System.out.println("[FavouriteOfferDAO] Updated favourite offer entry for client: " +
                        favouriteOffer.getClient().getClientId() + " and offer: " + favouriteOffer.getOffer().getOfferId());
            }
            session.getTransaction().commit();
        }
    }

    public FavouriteOffer findById(int id) {
        try (Session session = factory.openSession()) {
            return session.get(FavouriteOffer.class, id);
        }
    }

    public List<FavouriteOffer> findAllClientFavourite(int clientId) {
        try (Session session = factory.openSession()) {
            return session.createNativeQuery("FROM favourite_offers WHERE client_id = :clientId", FavouriteOffer.class)
                    .setParameter("clientId", clientId)
                    .list();
        }
    }

    public int findQuantityForOffer(int offerId) {
        try (Session session = factory.openSession()) {
            Long result = (Long) session.createNativeQuery("SELECT COUNT(*) FROM favourite_offers WHERE offer_id = :offerId")
                    .setParameter("offerId", offerId)
                    .uniqueResult();
            return result != null ? result.intValue() : 0;
        }
    }

    public int findNumberOfFavouritesForClient(int clientId) {
        try (Session session = factory.openSession()) {
            Long result = (Long) session.createNativeQuery("SELECT COUNT(*) FROM favourite_offers WHERE client_id = :clientId")
                    .setParameter("clientId", clientId)
                    .uniqueResult();
            return result != null ? result.intValue() : 0;
        }
    }
}
