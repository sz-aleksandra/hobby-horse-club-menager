package pap.db;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import pap.db.entities.*;

public class SessionFactoryMaker {
    private static SessionFactory factory;

    private static void configureFactory() {
        try {
            Configuration configuration = new Configuration();

            configuration.addAnnotatedClass(Accessory.class);
            configuration.addAnnotatedClass(Address.class);
            configuration.addAnnotatedClass(Class.class);
            configuration.addAnnotatedClass(Employee.class);
            configuration.addAnnotatedClass(Group.class);
            configuration.addAnnotatedClass(Horse.class);
            configuration.addAnnotatedClass(Member.class);
            configuration.addAnnotatedClass(Position.class);
            configuration.addAnnotatedClass(PositionHistory.class);
            configuration.addAnnotatedClass(Rider.class);
            configuration.addAnnotatedClass(Stable.class);
            configuration.addAnnotatedClass(Tournament.class);
            configuration.addAnnotatedClass(TournamentParticipants.class);

            configuration.configure();
            factory = configuration.buildSessionFactory();
        } catch (Exception e) {
            System.err.println("Failed to create sessionFactory object: " + e);
            throw new ExceptionInInitializerError(e);
        }
    }

    public static SessionFactory getFactory() {
        if (factory == null) {
            configureFactory();
        }
        return factory;
    }
}


