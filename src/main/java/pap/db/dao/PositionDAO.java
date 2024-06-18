package pap.db.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pap.db.SessionFactoryMaker;
import pap.db.entities.Position;

import java.util.List;

public class PositionDAO {
    SessionFactory factory = SessionFactoryMaker.getFactory();

    public void create(Position position) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.persist(position);
            session.getTransaction().commit();
            System.out.println("[PositionDAO] Created position with id: " + position.getPositionId());
        }
    }

    public void delete(Position position) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.remove(position);
            session.getTransaction().commit();
            System.out.println("[PositionDAO] Deleted position with id: " + position.getPositionId());
        }
    }

    public void update(Position position) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.merge(position);
            session.getTransaction().commit();
            System.out.println("[PositionDAO] Updated position with id: " + position.getPositionId());
        }
    }

    public List<Position> findAll() {
        try (Session session = factory.openSession()) {
            return session.createNativeQuery("SELECT * FROM positions", Position.class).list();
        }
    }

    public Position findById(int id) {
        try (Session session = factory.openSession()) {
            return session.get(Position.class, id);
        }
    }
}
