package pap.db.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.resource.transaction.backend.jdbc.internal.JdbcIsolationDelegate;
import pap.db.SessionFactoryMaker;
import pap.db.entities.Discount;

import java.util.List;

public class DiscountsDAO {
    SessionFactory factory = SessionFactoryMaker.getFactory();

    public void create(Discount discount) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.persist(discount);
            session.getTransaction().commit();
            System.out.println("[DiscountsDAO] Created discount with id: " + discount.getDiscountId());
        }
    }

    public void createMany(Iterable<Discount> discounts) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            for (Discount discount : discounts)
                session.persist(discount);
            session.getTransaction().commit();
            for (Discount discount : discounts)
                System.out.println("[DiscountsDAO] Created paymentMethod with id: " + discount.getDiscountId());
        }
    }

    public void update(Discount discount) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.merge(discount);
            session.getTransaction().commit();
            System.out.println("[DiscountsDAO] Updated discount with id: " + discount.getDiscountId());
        }
    }


    public void delete(Discount discount) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.remove(discount);
            session.getTransaction().commit();
            System.out.println("[DiscountsDAO] Deleted discount with id: " + discount.getDiscountId());
        }
    }

    public List<Discount> findAll() {
        try (Session session = factory.openSession()) {
            return session.createNativeQuery("select * from discounts", Discount.class).list();
        }
    }

    public List<Discount> customQuery(String query) {
        try (Session session = factory.openSession()) {
            // it is not safe to use this method with user input
            return session.createNativeQuery(query, Discount.class).list();
        }
    }
}
