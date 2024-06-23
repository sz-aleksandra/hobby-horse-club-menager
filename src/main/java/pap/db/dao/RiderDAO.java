package pap.db.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pap.db.SessionFactoryMaker;
import pap.db.entities.Rider;

import java.util.List;

public class RiderDAO {
    SessionFactory factory = SessionFactoryMaker.getFactory();

    public void create(Rider rider) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.persist(rider);
            session.getTransaction().commit();
            System.out.println("[RiderDAO] Created rider with id: " + rider.getRiderId());
        }
    }

    public void delete(Rider rider) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.remove(rider);
            session.getTransaction().commit();
            System.out.println("[RiderDAO] Deleted rider with id: " + rider.getRiderId());
        }
    }

    public void update(Rider rider) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.merge(rider);
            session.getTransaction().commit();
            System.out.println("[RiderDAO] Updated rider with id: " + rider.getRiderId());
        }
    }

    public List<Rider> findAll() {
        try (Session session = factory.openSession()) {
            return session.createNativeQuery("SELECT * FROM riders", Rider.class).list();
        }
    }

    public Rider findById(int id) {
        try (Session session = factory.openSession()) {
            return session.get(Rider.class, id);
        }
    }

    public List<Rider> findRidersByGroupId(int groupId) {
        try (Session session = factory.openSession()) {
            return session.createNativeQuery("SELECT * FROM riders WHERE group_id = :groupId", Rider.class)
                    .setParameter("groupId", groupId)
                    .list();
        }
    }

    public List<Rider> findRidersByHorseId(int horseId) {
        try (Session session = factory.openSession()) {
            return session.createNativeQuery("SELECT * FROM riders WHERE horse_id = :horseId", Rider.class)
                    .setParameter("horseId", horseId)
                    .list();
        }
    }
}
