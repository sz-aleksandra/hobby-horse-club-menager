package pap.db.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pap.db.SessionFactoryMaker;
import pap.db.entities.Discount;

import java.util.List;

public class DiscountDAO {
    SessionFactory factory = SessionFactoryMaker.getFactory();

    public void create(Discount discount) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.persist(discount);
            session.getTransaction().commit();
            System.out.println("[DiscountDAO] Created discount with code: " + discount.getCode());
        }
    }

    public void createMany(Iterable<Discount> discountsList) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            for (Discount discount : discountsList) {
                session.persist(discount);
                System.out.println("[DiscountDAO] Created discount with code: " + discount.getCode());
            }
            session.getTransaction().commit();
        }
    }

    public void delete(Discount discount) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.remove(discount);
            session.getTransaction().commit();
            System.out.println("[DiscountDAO] Deleted discount with code: " + discount.getCode());
        }
    }

    public void deleteMany(Iterable<Discount> discountsList) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            for (Discount discount : discountsList) {
                session.remove(discount);
                System.out.println("[DiscountDAO] Deleted discount with code: " + discount.getCode());
            }
            session.getTransaction().commit();
        }
    }

    public void update(Discount discount) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.merge(discount);
            session.getTransaction().commit();
            System.out.println("[DiscountDAO] Updated discount with code: " + discount.getCode());
        }
    }

    public void updateMany(Iterable<Discount> discountsList) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            for (Discount discount : discountsList) {
                session.merge(discount);
                System.out.println("[DiscountDAO] Updated discount with code: " + discount.getCode());
            }
            session.getTransaction().commit();
        }
    }

    public List<Discount> findAll() {
        try (Session session = factory.openSession()) {
            return session.createNativeQuery("SELECT * FROM discounts WHERE is_active = true", Discount.class).list();
        }
    }

    public List<Discount> findAllWithNotActive() {
        try (Session session = factory.openSession()) {
            return session.createNativeQuery("SELECT * FROM discounts", Discount.class).list();
        }
    }

    public Discount findById(int id) {
        try (Session session = factory.openSession()) {
            return session.get(Discount.class, id);
        }
    }

    public Discount findByCode(String code) {
        try (Session session = factory.openSession()) {
            return session.createNativeQuery("SELECT * FROM discounts WHERE code = :code AND is_active = true", Discount.class)
                    .setParameter("code", code)
                    .uniqueResult();
        }
    }

    public Discount findByCodeWithNoActive(String code) {
        try (Session session = factory.openSession()) {
            return session.createNativeQuery("SELECT * FROM discounts WHERE code = :code", Discount.class)
                    .setParameter("code", code)
                    .uniqueResult();
        }
    }

    public Double getValueByCode(String code) {
        try (Session session = factory.openSession()) {
            return (Double) session.createNativeQuery("SELECT value FROM discounts WHERE code = :code AND is_active = true")
                    .setParameter("code", code)
                    .uniqueResult();
        }
    }

    public Integer getHotelIdByCode(String code) {
        try (Session session = factory.openSession()) {
            return (Integer) session.createNativeQuery("SELECT hotelId FROM discounts WHERE code = :code AND is_active = true")
                    .setParameter("code", code)
                    .uniqueResult();
        }
    }
}
