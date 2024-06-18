package pap.db.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pap.db.SessionFactoryMaker;
import pap.db.entities.TournamentParticipant;

import java.util.List;
import java.util.Optional;

public class TournamentParticipantDAO {
    private final SessionFactory factory = SessionFactoryMaker.getFactory();

    public void create(TournamentParticipant tournamentParticipant) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.persist(tournamentParticipant);
            session.getTransaction().commit();
            System.out.println("[TournamentParticipantDAO] Created tournament participant with id: " + tournamentParticipant.getId());
        }
    }

    public void createMany(Iterable<TournamentParticipant> tournamentParticipants) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            for (TournamentParticipant participant : tournamentParticipants) {
                session.persist(participant);
                System.out.println("[TournamentParticipantDAO] Created tournament participant with id: " + participant.getId());
            }
            session.getTransaction().commit();
        }
    }

    public void update(TournamentParticipant tournamentParticipant) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.merge(tournamentParticipant);
            session.getTransaction().commit();
            System.out.println("[TournamentParticipantDAO] Updated tournament participant with id: " + tournamentParticipant.getId());
        }
    }

    public void updateMany(Iterable<TournamentParticipant> tournamentParticipants) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            for (TournamentParticipant participant : tournamentParticipants) {
                session.merge(participant);
                System.out.println("[TournamentParticipantDAO] Updated tournament participant with id: " + participant.getId());
            }
            session.getTransaction().commit();
        }
    }

    public void delete(TournamentParticipant tournamentParticipant) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.remove(tournamentParticipant);
            session.getTransaction().commit();
            System.out.println("[TournamentParticipantDAO] Deleted tournament participant with id: " + tournamentParticipant.getId());
        }
    }

    public void deleteMany(Iterable<TournamentParticipant> tournamentParticipants) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            for (TournamentParticipant participant : tournamentParticipants) {
                session.remove(participant);
                System.out.println("[TournamentParticipantDAO] Deleted tournament participant with id: " + participant.getId());
            }
            session.getTransaction().commit();
        }
    }

    public List<TournamentParticipant> findAll() {
        try (Session session = factory.openSession()) {
            return session.createNativeQuery("SELECT * FROM tournament_participants", TournamentParticipant.class).list();
        }
    }

    public Optional<TournamentParticipant> findById(int id) {
        try (Session session = factory.openSession()) {
            return Optional.ofNullable(session.get(TournamentParticipant.class, id));
        }
    }

    public List<TournamentParticipant> findByMemberId(int memberId) {
        try (Session session = factory.openSession()) {
            return session.createNativeQuery("SELECT * FROM tournament_participants WHERE member_id = :memberId", TournamentParticipant.class)
                    .setParameter("memberId", memberId)
                    .list();
        }
    }

    public List<TournamentParticipant> findByTournamentId(int tournamentId) {
        try (Session session = factory.openSession()) {
            return session.createNativeQuery("SELECT * FROM tournament_participants WHERE tournament_id = :tournamentId", TournamentParticipant.class)
                    .setParameter("tournamentId", tournamentId)
                    .list();
        }
    }
}
