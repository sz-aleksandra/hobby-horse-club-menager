package pap.db.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pap.db.SessionFactoryMaker;
import pap.db.entities.Class;

import java.util.List;

public class ClassDAO {
    SessionFactory factory = SessionFactoryMaker.getFactory();

    public void create(Class classEntity) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.persist(classEntity);
            session.getTransaction().commit();
            System.out.println("[ClassDAO] Created class with id: " + classEntity.getClassId());
        }
    }

    public void delete(Class classEntity) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.remove(classEntity);
            session.getTransaction().commit();
            System.out.println("[ClassDAO] Deleted class with id: " + classEntity.getClassId());
        }
    }

    public void update(Class classEntity) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.merge(classEntity);
            session.getTransaction().commit();
            System.out.println("[ClassDAO] Updated class with id: " + classEntity.getClassId());
        }
    }

    public List<Class> findAll() {
        try (Session session = factory.openSession()) {
            return session.createNativeQuery("SELECT * FROM classes", Class.class).list();
        }
    }

    public Class findById(int id) {
        try (Session session = factory.openSession()) {
            return session.get(Class.class, id);
        }
    }

    public List<Class> findClassesByTrainerId(int trainerId) {
        try (Session session = factory.openSession()) {
            return session.createNativeQuery("SELECT * FROM classes WHERE trainer_id = :trainerId", Class.class)
                    .setParameter("trainerId", trainerId)
                    .list();
        }
    }

    public List<Class> findClassesByDateRange(Date startDate, Date endDate) {
        try (Session session = factory.openSession()) {
            return session.createNativeQuery("SELECT * FROM classes WHERE date BETWEEN :startDate AND :endDate", Class.class)
                    .setParameter("startDate", startDate)
                    .setParameter("endDate", endDate)
                    .list();
        }
    }
}
