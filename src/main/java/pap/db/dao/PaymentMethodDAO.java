package pap.db.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pap.db.SessionFactoryMaker;
import pap.db.entities.PaymentMethod;

import java.util.List;

public class PaymentMethodDAO {
    SessionFactory factory = SessionFactoryMaker.getFactory();

    public void create(PaymentMethod paymentMethod) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.persist(paymentMethod);
            session.getTransaction().commit();
            System.out.println("[PaymentMethodDAO] Created paymentMethod with id: " + paymentMethod.getPaymentMethodId());
        }
    }

    public void createMany(Iterable<PaymentMethod> paymentMethods) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            for (PaymentMethod paymentMethod : paymentMethods)
                session.persist(paymentMethod);
            session.getTransaction().commit();
            for (PaymentMethod paymentMethod : paymentMethods)
                System.out.println("[PaymentMethodDAO] Created paymentMethod with id: " + paymentMethod.getPaymentMethodId());
        }
    }

    public void update(PaymentMethod paymentMethod) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.merge(paymentMethod);
            session.getTransaction().commit();
            System.out.println("[PaymentMethodDAO] Updated paymentMethod with id: " + paymentMethod.getPaymentMethodId());
        }
    }


    public void delete(PaymentMethod paymentMethod) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.remove(paymentMethod);
            session.getTransaction().commit();
            System.out.println("[PaymentMethodDAO] Deleted paymentMethod with id: " + paymentMethod.getPaymentMethodId());
        }
    }

    public List<PaymentMethod> findAll() {
        try (Session session = factory.openSession()) {
            return session.createNativeQuery("select * from payment_methods", PaymentMethod.class).list();
        }
    }

    public List<PaymentMethod> findByClientId(int id) {
        try (Session session = factory.openSession()) {
            String sql = "SELECT * FROM payment_methods WHERE client_id = :clientId";
            return session.createNativeQuery(sql, PaymentMethod.class)
                    .setParameter("clientId", id)
                    .list();
        }
    }


    public List<PaymentMethod> customQuery(String query) {
        try (Session session = factory.openSession()) {
            // it is not safe to use this method with user input
            return session.createNativeQuery(query, PaymentMethod.class).list();
        }
    }
}