package pap.db.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pap.db.SessionFactoryMaker;
import pap.db.entities.Penalty;

import java.util.List;

public class PenaltyDAO {
    SessionFactory factory = SessionFactoryMaker.getFactory();

    public void create(Penalty penalty) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.persist(penalty);
            session.getTransaction().commit();
            System.out.println("[PenaltyDAO] Created penalty with id: " + penalty.getPenaltyId());
        }
    }

    public void createMany(Iterable<Penalty> penaltiesList) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            for (Penalty penalty : penaltiesList) {
                session.persist(penalty);
                System.out.println("[PenaltyDAO] Created penalty with id: " + penalty.getPenaltyId());
            }
            session.getTransaction().commit();
        }
    }

    public void delete(Penalty penalty) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.remove(penalty);
            session.getTransaction().commit();
            System.out.println("[PenaltyDAO] Deleted penalty with id: " + penalty.getPenaltyId());
        }
    }

    public void deleteMany(Iterable<Penalty> penaltiesList) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            for (Penalty penalty : penaltiesList) {
                session.remove(penalty);
                System.out.println("[PenaltyDAO] Deleted penalty with id: " + penalty.getPenaltyId());
            }
            session.getTransaction().commit();
        }
    }

    public void update(Penalty penalty) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.merge(penalty);
            session.getTransaction().commit();
            System.out.println("[PenaltyDAO] Updated penalty with id: " + penalty.getPenaltyId());
        }
    }

    public void updateMany(Iterable<Penalty> penaltiesList) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            for (Penalty penalty : penaltiesList) {
                session.merge(penalty);
                System.out.println("[PenaltyDAO] Updated penalty with id: " + penalty.getPenaltyId());
            }
            session.getTransaction().commit();
        }
    }

    public Penalty findById(int id) {
        try (Session session = factory.openSession()) {
            return session.get(Penalty.class, id);
        }
    }

    public List<Penalty> findByReservationId(int reservationId) {
        try (Session session = factory.openSession()) {
            return session.createNativeQuery("FROM Penalty WHERE reservation_id = :reservationId", Penalty.class)
                    .setParameter("reservationId", reservationId)
                    .list();
        }
    }

    public double getAmountForReservationId(int reservationId) {
        try (Session session = factory.openSession()) {
            return (double) session.createNativeQuery("SELECT amount FROM Penalty WHERE reservation_id = :reservationId")
                    .setParameter("reservationId", reservationId)
                    .uniqueResult();
        }
    }

    public String getStatusById(int penaltyId) {
        try (Session session = factory.openSession()) {
            return (String) session.createNativeQuery("SELECT is_active FROM penalties WHERE penalty_id = :penaltyId")
                    .setParameter("penaltyId", penaltyId)
                    .uniqueResult();
        }
    }
}
