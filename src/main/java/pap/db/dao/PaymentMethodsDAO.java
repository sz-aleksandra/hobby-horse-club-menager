package pap.db.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pap.db.SessionFactoryMaker;
import pap.db.entities.PaymentMethod;

import java.util.List;

public class PaymentMethodsDAO {
    SessionFactory factory = SessionFactoryMaker.getFactory();

    public void create(PaymentMethod paymentMethod) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.persist(paymentMethod);
            session.getTransaction().commit();
            System.out.println("[PaymentMethodsDAO] Created payment method with id: " + paymentMethod.getPaymentMethodId());
        }
    }

    public void createMany(Iterable<PaymentMethod> paymentMethodsList) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            for (PaymentMethod paymentMethod : paymentMethodsList) {
                session.persist(paymentMethod);
                System.out.println("[PaymentMethodsDAO] Created payment method with id: " + paymentMethod.getPaymentMethodId());
            }
            session.getTransaction().commit();
        }
    }

    public void remove(PaymentMethod paymentMethod) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.remove(paymentMethod);
            session.getTransaction().commit();
            System.out.println("[PaymentMethodsDAO] Removed payment method with id: " + paymentMethod.getPaymentMethodId());
        }
    }

    public void removeMany(Iterable<PaymentMethod> paymentMethodsList) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            for (PaymentMethod paymentMethod : paymentMethodsList) {
                session.remove(paymentMethod);
                System.out.println("[PaymentMethodsDAO] Removed payment method with id: " + paymentMethod.getPaymentMethodId());
            }
            session.getTransaction().commit();
        }
    }

    public void merge(PaymentMethod paymentMethod) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.merge(paymentMethod);
            session.getTransaction().commit();
            System.out.println("[PaymentMethodsDAO] Merged payment method with id: " + paymentMethod.getPaymentMethodId());
        }
    }

    public void mergeMany(Iterable<PaymentMethod> paymentMethodsList) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            for (PaymentMethod paymentMethod : paymentMethodsList) {
                session.merge(paymentMethod);
                System.out.println("[PaymentMethodsDAO] Merged payment method with id: " + paymentMethod.getPaymentMethodId());
            }
            session.getTransaction().commit();
        }
    }

    public PaymentMethod findById(int id) {
        try (Session session = factory.openSession()) {
            return session.get(PaymentMethod.class, id);
        }
    }

    public List<PaymentMethod> getPaymentMethodForClient(int clientId) {
        try (Session session = factory.openSession()) {
            return session.createNativeQuery("SELECT * FROM payment_methods WHERE client_id = :clientId", PaymentMethod.class)
                    .setParameter("clientId", clientId)
                    .list();
        }
    }
}
