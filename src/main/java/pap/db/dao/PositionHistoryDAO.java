package pap.db.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pap.db.SessionFactoryMaker;
import pap.db.entities.PositionHistory;

import java.util.List;
import java.util.Optional;

public class PositionHistoryDAO {
    SessionFactory factory = SessionFactoryMaker.getFactory();

    public void create(PositionHistory positionHistory) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.persist(positionHistory);
            session.getTransaction().commit();
            System.out.println("[PositionHistoryDAO] Created position history with id: " + positionHistory.getId());
        }
    }

    public void createMany(Iterable<PositionHistory> positionHistories) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            for (PositionHistory positionHistory : positionHistories) {
                session.persist(positionHistory);
                System.out.println("[PositionHistoryDAO] Created position history with id: " + positionHistory.getId());
            }
            session.getTransaction().commit();
        }
    }

    public void update(PositionHistory positionHistory) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.merge(positionHistory);
            session.getTransaction().commit();
            System.out.println("[PositionHistoryDAO] Updated position history with id: " + positionHistory.getId());
        }
    }

    public void updateMany(Iterable<PositionHistory> positionHistories) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            for (PositionHistory positionHistory : positionHistories) {
                session.merge(positionHistory);
                System.out.println("[PositionHistoryDAO] Updated position history with id: " + positionHistory.getId());
            }
            session.getTransaction().commit();
        }
    }

    public void delete(PositionHistory positionHistory) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.remove(positionHistory);
            session.getTransaction().commit();
            System.out.println("[PositionHistoryDAO] Deleted position history with id: " + positionHistory.getId());
        }
    }

    public void deleteMany(Iterable<PositionHistory> positionHistories) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            for (PositionHistory positionHistory : positionHistories) {
                session.remove(positionHistory);
                System.out.println("[PositionHistoryDAO] Deleted position history with id: " + positionHistory.getId());
            }
            session.getTransaction().commit();
        }
    }

    public List<PositionHistory> findAll() {
        try (Session session = factory.openSession()) {
            return session.createNativeQuery("SELECT * FROM position_history", PositionHistory.class).list();
        }
    }

    public Optional<PositionHistory> findById(int id) {
        try (Session session = factory.openSession()) {
            return Optional.ofNullable(session.get(PositionHistory.class, id));
        }
    }

    public List<PositionHistory> findByMemberId(int memberId) {
        try (Session session = factory.openSession()) {
            return session.createNativeQuery("SELECT * FROM position_history WHERE member_id = :memberId", PositionHistory.class)
                    .setParameter("memberId", memberId)
                    .list();
        }
    }

    public List<PositionHistory> findByDateRange(String startDate, String endDate) {
        try (Session session = factory.openSession()) {
            return session.createNativeQuery("SELECT * FROM position_history WHERE start_date >= :startDate AND (end_date <= :endDate OR end_date IS NULL)", PositionHistory.class)
                    .setParameter("startDate", startDate)
                    .setParameter("endDate", endDate)
                    .list();
        }
    }
}
