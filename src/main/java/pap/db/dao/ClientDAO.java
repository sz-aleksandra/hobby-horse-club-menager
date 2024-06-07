package pap.db.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pap.db.SessionFactoryMaker;
import pap.db.entities.Client;

import java.util.List;

public class ClientDAO {
    SessionFactory factory = SessionFactoryMaker.getFactory();

    public void create(Client client) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.persist(client);
            session.getTransaction().commit();
            System.out.println("[ClientDAO] Created client with id: " + client.getClientId());
        }
    }

    public void createWithNewAddress(Client client) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.persist(client.getAddress());
            session.persist(client);
            session.getTransaction().commit();
            System.out.println("[ClientDAO] Created address with id: " + client.getAddress().getAddressId());
            System.out.println("[ClientDAO] Created client with id: " + client.getClientId());
        }
    }

    public void createMany(Iterable<Client> clients) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            for (Client client : clients)
                session.persist(client);
            session.getTransaction().commit();
            for (Client client : clients)
                System.out.println("[ClientDAO] Created client with id: " + client.getClientId());
        }
    }

    public void update(Client client) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.merge(client);
            session.getTransaction().commit();
            System.out.println("[ClientDAO] Updated client with id: " + client.getClientId());
        }
    }

    public void updateMany(Iterable<Client> clients) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            for (Client client : clients)
                session.merge(client);
            session.getTransaction().commit();
            for (Client client : clients)
                System.out.println("[ClientDAO] Updated client with id: " + client.getClientId());
        }
    }

    public void delete(Client client) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.remove(client);
            session.getTransaction().commit();
            System.out.println("[ClientDAO] Deleted client with id: " + client.getClientId());
        }
    }

    public void deleteMany(Iterable<Client> clients) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            for (Client client : clients)
                session.remove(client);
            session.getTransaction().commit();
            for (Client client : clients)
                System.out.println("[ClientDAO] Deleted client with id: " + client.getClientId());
        }
    }

    public List<Client> findAll() {
        try (Session session = factory.openSession()) {
            return session.createNativeQuery("select * from clients", Client.class).list();
        }
    }

    public Client findById(int id) {
        try (Session session = factory.openSession()) {
            return session.find(Client.class, id);
        }
    }

    public Client findByUsername(String username) {
        try (Session session = factory.openSession()) {
            return session.createNativeQuery("select * from clients where username = :username", Client.class)
                    .setParameter("username", username)
                    .getSingleResult();
        }
    }

    public Client findByEmail(String email) {
        try (Session session = factory.openSession()) {
            return session.createNativeQuery("select * from clients where email = :email", Client.class)
                    .setParameter("email", email)
                    .getSingleResult();
        }
    }

    public List<Client> customQuery(String query) {
        try (Session session = factory.openSession()) {
            // it is not safe to use this method with user input
            return session.createNativeQuery(query, Client.class).list();
        }
    }
}
