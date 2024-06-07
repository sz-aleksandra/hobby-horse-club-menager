package pap.logic.reservation;

import pap.db.entities.*;
import pap.db.dao.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class NewReservation {
    private final Reservation reservation;

    public NewReservation(LocalDate startDate, LocalDate endDate, String description, Float paidAmount,
                          boolean isPaid, Client client, Offer offer){
        reservation = new Reservation();
        reservation.setStartDate(startDate);
        reservation.setEndDate(endDate);
        reservation.setDescription(description);
        reservation.setPaidAmount(paidAmount);
        reservation.setStatus("Active");
        reservation.setPaid(isPaid);
        reservation.setClient(client);
        reservation.setOffer(offer);
        reservation.setPenalties(new ArrayList<>());
    }

    public void insertIntoDatabase() {
        new ReservationDAO().create(reservation);
    }
}
