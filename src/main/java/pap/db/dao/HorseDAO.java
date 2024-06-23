package pap.db.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pap.db.SessionFactoryMaker;
import pap.db.entities.Horse;

import java.util.List;

public class HorseDAO {
    SessionFactory factory = SessionFactoryMaker.getFactory();

    public void create(Horse horse) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.persist(horse);
            session.getTransaction().commit();
            System.out.println("[HorseDAO] Created horse with id: " + horse.getHorseId());
        }
    }

    public void delete(Horse horse) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.remove(horse);
            session.getTransaction().commit();
            System.out.println("[HorseDAO] Deleted horse with id: " + horse.getHorseId());
        }
    }

    public void update(Horse horse) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.merge(horse);
            session.getTransaction().commit();
            System.out.println("[HorseDAO] Updated horse with id: " + horse.getHorseId());
        }
    }

    public List<Horse> findAll() {
        try (Session session = factory.openSession()) {
            return session.createNativeQuery("SELECT * FROM horses", Horse.class).list();
        }
    }

    public Horse findById(int id) {
        try (Session session = factory.openSession()) {
            return session.get(Horse.class, id);
        }
    }

    public List<Horse> findHorsesByOwnerId(int ownerId) {
        try (Session session = factory.openSession()) {
            return session.createNativeQuery("SELECT * FROM horses WHERE owner_id = :ownerId", Horse.class)
                    .setParameter("ownerId", ownerId)
                    .list();
        }
    }

    public List<Horse> findHorsesByStableId(int stableId) {
        try (Session session = factory.openSession()) {
            return session.createNativeQuery("SELECT * FROM horses WHERE stable_id = :stableId", Horse.class)
                    .setParameter("stableId", stableId)
                    .list();
        }
    }
}
