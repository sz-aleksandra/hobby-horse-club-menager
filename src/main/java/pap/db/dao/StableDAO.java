package pap.db.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pap.db.SessionFactoryMaker;
import pap.db.entities.Stable;

import java.util.List;

public class StableDAO {
    SessionFactory factory = SessionFactoryMaker.getFactory();

    public void create(Stable stable) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.persist(stable);
            session.getTransaction().commit();
            System.out.println("[StableDAO] Created stable with id: " + stable.getStableId());
        }
    }

    public void delete(Stable stable) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.remove(stable);
            session.getTransaction().commit();
            System.out.println("[StableDAO] Deleted stable with id: " + stable.getStableId());
        }
    }

    public void update(Stable stable) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.merge(stable);
            session.getTransaction().commit();
            System.out.println("[StableDAO] Updated stable with id: " + stable.getStableId());
        }
    }

    public List<Stable> findAll() {
        try (Session session = factory.openSession()) {
            return session.createNativeQuery("SELECT * FROM stables", Stable.class).list();
        }
    }

    public Stable findById(int id) {
        try (Session session = factory.openSession()) {
            return session.get(Stable.class, id);
        }
    }
}
