package pap.db.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pap.db.SessionFactoryMaker;
import pap.db.entities.Address;

import java.util.List;

public class AddressDAO {
    SessionFactory factory = SessionFactoryMaker.getFactory();

    public void create(Address address) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.persist(address);
            session.getTransaction().commit();
            System.out.println("[AddressDAO] Created address with id: " + address.getAddressId());
        }
    }

    public void createMany(Iterable<Address> addressesList) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            for (Address address : addressesList) {
                session.persist(address);
                System.out.println("[AddressDAO] Created address with id: " + address.getAddressId());
            }
            session.getTransaction().commit();
        }
    }

    public void delete(Address address) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.remove(address);
            session.getTransaction().commit();
            System.out.println("[AddressDAO] Deleted address with id: " + address.getAddressId());
        }
    }

    public void deleteMany(Iterable<Address> addressesList) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            for (Address address : addressesList) {
                session.remove(address);
                System.out.println("[AddressDAO] Deleted address with id: " + address.getAddressId());
            }
            session.getTransaction().commit();
        }
    }

    public void update(Address address) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.remove(address);
            session.getTransaction().commit();
            System.out.println("[AddressDAO] Updated address with id: " + address.getAddressId());
        }
    }

    public void updateMany(Iterable<Address> addressesList) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            for (Address address : addressesList) {
                session.merge(address);
                System.out.println("[AddressDAO] Updated address with id: " + address.getAddressId());
            }
            session.getTransaction().commit();
        }
    }

    public List<Address> findAll() {
        try (Session session = factory.openSession()) {
            return session.createNativeQuery("SELECT * FROM addresses", Address.class).list();
        }
    }

    public Address findById(int id) {
        try (Session session = factory.openSession()) {
            return session.get(Address.class, id);
        }
    }

    public List<Address> findAddressesInCountry(String country) {
        try (Session session = factory.openSession()) {
            return session.createNativeQuery("SELECT * FROM addresses WHERE country = :country", Address.class)
                    .setParameter("country", country)
                    .list();
        }
    }

    public List<Address> findAddressesInCity(String city) {
        try (Session session = factory.openSession()) {
            return session.createNativeQuery("SELECT * FROM addresses WHERE city = :city", Address.class)
                    .setParameter("city", city)
                    .list();
        }
    }

    public List<Address> findAddressesInStreet(String street) {
        try (Session session = factory.openSession()) {
            return session.createNativeQuery("SELECT * FROM addresses WHERE street = :street", Address.class)
                    .setParameter("street", street)
                    .list();
        }
    }
}
