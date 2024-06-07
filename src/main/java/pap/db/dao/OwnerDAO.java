package pap.db.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pap.db.SessionFactoryMaker;
import pap.db.entities.Owner;

import java.util.List;

public class OwnerDAO {
    SessionFactory factory = SessionFactoryMaker.getFactory();

    public void create(Owner owner) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.persist(owner);
            session.getTransaction().commit();
            System.out.println("[OwnerDAO] Created owner with id: " + owner.getOwnerId());
        }
    }

    public void createWithNewAddress(Owner owner) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.persist(owner.getAddress());
            session.persist(owner);
            session.getTransaction().commit();
            System.out.println("[OwnerDAO] Created address with id: " + owner.getAddress().getAddressId());
            System.out.println("[OwnerDAO] Created owner with id: " + owner.getOwnerId());
        }
    }

    public void createMany(Iterable<Owner> owners) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            for (Owner owner : owners)
                session.persist(owner);
            session.getTransaction().commit();
            for (Owner owner : owners)
                System.out.println("[OwnerDAO] Created owner with id: " + owner.getOwnerId());
        }
    }

    public void update(Owner owner) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.merge(owner);
            session.getTransaction().commit();
            System.out.println("[OwnerDAO] Updated owner with id: " + owner.getOwnerId());
        }
    }

    public void updateMany(Iterable<Owner> owners) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            for (Owner owner : owners)
                session.merge(owner);
            session.getTransaction().commit();
            for (Owner owner : owners)
                System.out.println("[OwnerDAO] Updated owner with id: " + owner.getOwnerId());
        }
    }

    public void delete(Owner owner) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.remove(owner);
            session.getTransaction().commit();
            System.out.println("[OwnerDAO] Deleted owner with id: " + owner.getOwnerId());
        }
    }

    public void deleteMany(Iterable<Owner> owners) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            for (Owner owner : owners)
                session.remove(owner);
            session.getTransaction().commit();
            for (Owner owner : owners)
                System.out.println("[OwnerDAO] Deleted owner with id: " + owner.getOwnerId());
        }
    }

    public List<Owner> findAll() {
        try (Session session = factory.openSession()) {
            return session.createNativeQuery("select * from owners", Owner.class).list();
        }
    }

    public Owner findById(int id) {
        try (Session session = factory.openSession()) {
            return session.find(Owner.class, id);
        }
    }

    public Owner findByUsername(String username) {
        try (Session session = factory.openSession()) {
            return session.createNativeQuery("select * from owners where username = :username", Owner.class)
                    .setParameter("username", username)
                    .getSingleResult();
        }
    }

    public Owner findByEmail(String email) {
        try (Session session = factory.openSession()) {
            return session.createNativeQuery("select * from owners where email = :email", Owner.class)
                    .setParameter("email", email)
                    .getSingleResult();
        }
    }

    public Owner customQuery(String query) {
        try (Session session = factory.openSession()) {
            // it is not safe to use this method with user input
            return session.createNativeQuery(query, Owner.class).getSingleResult();
        }
    }
}
