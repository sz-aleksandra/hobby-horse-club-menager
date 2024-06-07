package pap.logic.filtration.filter;

import pap.db.dao.ReservationDAO;
import pap.db.entities.Offer;
import pap.db.entities.Reservation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CheckReservations {
    public static List<Offer> checkReservations(List <Offer> offers, LocalDate startDate, LocalDate endDate) {
        List <Offer> finalOffers = new ArrayList<>();
        for (var offer : offers) {
            boolean canReserve = true;
            List <Reservation> reservationList = new ReservationDAO().findByOfferId(offer.getOfferId());
            for (var reservation : reservationList) {
                if (reservation.getStatus().equalsIgnoreCase("active") &&
                        !reservation.getStartDate().isAfter(startDate) &&
                        !reservation.getEndDate().isBefore(startDate)) {
                    canReserve = false;
                    break;
                } else if (reservation.getStatus().equalsIgnoreCase("active") &&
                        !reservation.getStartDate().isAfter(endDate) &&
                        !reservation.getEndDate().isBefore(endDate)) {
                    canReserve = false;
                    break;
                }
            }
            if (canReserve) {
                finalOffers.add(offer);
            }
        }
        return finalOffers;
    }
}
