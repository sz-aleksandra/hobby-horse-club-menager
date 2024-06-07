package pap.db.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pap.db.SessionFactoryMaker;
import pap.db.entities.Admin;
import pap.db.entities.Client;

import java.util.List;

public class AdminDAO {
    SessionFactory factory = SessionFactoryMaker.getFactory();

    public void create(Admin admin) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.persist(admin);
            session.getTransaction().commit();
            System.out.println("[AdminDAO] Created admin with id: " + admin.getAdminId());
        }
    }

    public void createMany(Iterable<Admin> adminsList) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            for (Admin admin : adminsList) {
                session.persist(admin);
                System.out.println("[AdminDAO] Created admin with id: " + admin.getAdminId());
            }
            session.getTransaction().commit();
        }
    }

    public void delete(Admin admin) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.remove(admin);
            session.getTransaction().commit();
            System.out.println("[AdminDAO] Deleted admin with id: " + admin.getAdminId());
        }
    }

    public void deleteMany(Iterable<Admin> adminsList) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            for (Admin admin : adminsList) {
                session.remove(admin);
                System.out.println("[AdminDAO] Deleted admin with id: " + admin.getAdminId());
            }
            session.getTransaction().commit();
        }
    }

    public void update(Admin admin) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.merge(admin);
            session.getTransaction().commit();
            System.out.println("[AdminDAO] Updated admin with id: " + admin.getAdminId());
        }
    }

    public void updateMany(Iterable<Admin> adminsList) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            for (Admin admin : adminsList) {
                session.merge(admin);
                System.out.println("[AdminDAO] Updated admin with id: " + admin.getAdminId());
            }
            session.getTransaction().commit();
        }
    }

    public List<Admin> findAll() {
        try (Session session = factory.openSession()) {
            return session.createNativeQuery("SELECT * FROM admins", Admin.class).list();
        }
    }

    public Admin findById(int id) {
        try (Session session = factory.openSession()) {
            return session.get(Admin.class, id);
        }
    }

    public Admin findByUsername(String username) {
        try (Session session = factory.openSession()) {
            return session.createNativeQuery("select * from admins where username = :username", Admin.class)
                    .setParameter("username", username)
                    .getSingleResult();
        }
    }
}
