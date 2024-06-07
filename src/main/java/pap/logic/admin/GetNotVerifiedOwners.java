package pap.logic.admin;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pap.db.SessionFactoryMaker;
import pap.db.entities.Owner;

import java.util.List;

public class GetNotVerifiedOwners {
    private SessionFactory factory = SessionFactoryMaker.getFactory();

    public List <Owner> find() {
        try (Session session = factory.openSession()) {
            return session.createNativeQuery("select * from owners where is_verified = false", Owner.class).list();
        }
    }
}
