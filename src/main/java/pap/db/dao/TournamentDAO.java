package pap.db.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pap.db.SessionFactoryMaker;
import pap.db.entities.Tournament;

import java.util.List;
import java.util.Optional;

public class TournamentDAO {
    private final SessionFactory factory = SessionFactoryMaker.getFactory();

    public void create(Tournament tournament) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.persist(tournament);
            session.getTransaction().commit();
            System.out.println("[TournamentDAO] Created tournament with id: " + tournament.getTournamentId());
        }
    }

    public void createMany(Iterable<Tournament> tournaments) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            for (Tournament tournament : tournaments) {
                session.persist(tournament);
                System.out.println("[TournamentDAO] Created tournament with id: " + tournament.getTournamentId());
            }
            session.getTransaction().commit();
        }
    }

    public void update(Tournament tournament) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.merge(tournament);
            session.getTransaction().commit();
            System.out.println("[TournamentDAO] Updated tournament with id: " + tournament.getTournamentId());
        }
    }

    public void updateMany(Iterable<Tournament> tournaments) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            for (Tournament tournament : tournaments) {
                session.merge(tournament);
                System.out.println("[TournamentDAO] Updated tournament with id: " + tournament.getTournamentId());
            }
            session.getTransaction().commit();
        }
    }

    public void delete(Tournament tournament) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.remove(tournament);
            session.getTransaction().commit();
            System.out.println("[TournamentDAO] Deleted tournament with id: " + tournament.getTournamentId());
        }
    }

    public void deleteMany(Iterable<Tournament> tournaments) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            for (Tournament tournament : tournaments) {
                session.remove(tournament);
                System.out.println("[TournamentDAO] Deleted tournament with id: " + tournament.getTournamentId());
            }
            session.getTransaction().commit();
        }
    }

    public List<Tournament> findAll() {
        try (Session session = factory.openSession()) {
            return session.createNativeQuery("SELECT * FROM tournaments", Tournament.class).list();
        }
    }

    public Optional<Tournament> findById(int id) {
        try (Session session = factory.openSession()) {
            return Optional.ofNullable(session.get(Tournament.class, id));
        }
    }

    public List<Tournament> findByName(String name) {
        try (Session session = factory.openSession()) {
            return session.createNativeQuery("SELECT * FROM tournaments WHERE name LIKE :name", Tournament.class)
                    .setParameter("name", "%" + name + "%")
                    .list();
        }
    }

    public List<Tournament> findByDateRange(String startDate, String endDate) {
        try (Session session = factory.openSession()) {
            return session.createNativeQuery("SELECT * FROM tournaments WHERE date >= :startDate AND date <= :endDate", Tournament.class)
                    .setParameter("startDate", startDate)
                    .setParameter("endDate", endDate)
                    .list();
        }
    }
}
