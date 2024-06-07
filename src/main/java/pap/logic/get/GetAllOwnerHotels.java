package pap.logic.get;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pap.db.SessionFactoryMaker;
import pap.db.entities.Hotel;
import pap.db.entities.Owner;

import java.util.List;

public class GetAllOwnerHotels {
    private SessionFactory factory = SessionFactoryMaker.getFactory();
    private Owner owner;

    public GetAllOwnerHotels(Owner owner) {
        this.owner = owner;
    }

    public List<Hotel> get() {
        List <Hotel> hotels;
        String query = "select * from hotels where owner_id = '" + owner.getOwnerId() + "'";
        try (Session session = factory.openSession()) {
            hotels = session.createNativeQuery(query, Hotel.class).list();
        }
        return hotels;
    }
}
