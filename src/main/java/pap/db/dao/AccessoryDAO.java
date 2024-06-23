package pap.db.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pap.db.SessionFactoryMaker;
import pap.db.entities.Accessory;

import java.util.List;

public class AccessoryDAO {
    SessionFactory factory = SessionFactoryMaker.getFactory();

    public void create(Accessory accessory) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.persist(accessory);
            session.getTransaction().commit();
            System.out.println("[AccessoryDAO] Created accessory with id: " + accessory.getAccessoryId());
        }
    }

    public void delete(Accessory accessory) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.remove(accessory);
            session.getTransaction().commit();
            System.out.println("[AccessoryDAO] Deleted accessory with id: " + accessory.getAccessoryId());
        }
    }

    public void update(Accessory accessory) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.merge(accessory);
            session.getTransaction().commit();
            System.out.println("[AccessoryDAO] Updated accessory with id: " + accessory.getAccessoryId());
        }
    }

    public List<Accessory> findAll() {
        try (Session session = factory.openSession()) {
            return session.createNativeQuery("SELECT * FROM accessories", Accessory.class).list();
        }
    }

    public Accessory findById(int id) {
        try (Session session = factory.openSession()) {
            return session.get(Accessory.class, id);
        }
    }
}
