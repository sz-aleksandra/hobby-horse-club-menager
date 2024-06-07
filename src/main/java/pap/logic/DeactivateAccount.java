package pap.logic;

import pap.db.entities.*;
import pap.db.dao.*;
import java.util.*;

public class DeactivateAccount {
    public static List <Integer> deactivateClientAccount(int id) {
        List <Integer> codes = new ArrayList<>();
        Client user = new ClientDAO().findById(id);
        if (user == null) {
            codes.add(1001);
            return codes;
        }
        List<Reservation> reservations = new ReservationDAO().findByClientId(id);
        if (!reservations.isEmpty()) {
            for (var reservation : reservations) {
                if (reservation.getStatus().equals("active")) {
                    codes.add(1002);
                    return codes;
                }
            }
        }
        user.setActive(false);
        new ClientDAO().update(user);
        return codes;
    }

    public static List <Integer> deactivateOwnerAccount(int id) {
        List <Integer> codes = new ArrayList<>();
        Owner owner = new OwnerDAO().findById(id);
        if (owner == null) {
            codes.add(1003);
            return codes;
        }
        List <Hotel> hotels = new HotelDAO().findByOwnerId(id);
        if (!hotels.isEmpty()) {
            for (var hotel : hotels) {
                if (hotel.isActive()) {
                    codes.add(1004);
                    return codes;
                }
            }
        }
        owner.setActive(false);
        new OwnerDAO().update(owner);
        return codes;
    }
}
