package pap.db;

import pap.db.entities.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactoryMaker {
    private static SessionFactory factory;

    private static void configureFactory() {
        try {
            Configuration configuration = new Configuration();

            configuration.addAnnotatedClass(Address.class);
            configuration.addAnnotatedClass(Admin.class);
            configuration.addAnnotatedClass(Client.class);
            configuration.addAnnotatedClass(Discount.class);
            configuration.addAnnotatedClass(FavouriteHotel.class);
            configuration.addAnnotatedClass(FavouriteOffer.class);
            configuration.addAnnotatedClass(Hotel.class);
            configuration.addAnnotatedClass(Offer.class);
            configuration.addAnnotatedClass(Owner.class);
            configuration.addAnnotatedClass(PaymentMethod.class);
            configuration.addAnnotatedClass(Penalty.class);
            configuration.addAnnotatedClass(Rating.class);
            configuration.addAnnotatedClass(Reservation.class);

            configuration.configure();
            factory = configuration.buildSessionFactory();
        } catch (Exception e) {
            System.err.println("Failed to create sessionFactory object." + e);
            throw new ExceptionInInitializerError(e);
        }
    }

    public static SessionFactory getFactory() {
        if (factory == null)
            configureFactory();
        return factory;
    }
}
