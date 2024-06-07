package pap.logic.reservation;
import pap.db.entities.*;
import pap.db.dao.*;

import java.time.LocalDate;
import java.util.List;

public class ReservationFunctionality {

    private final Reservation reservation;

    public ReservationFunctionality(Integer resId){
        reservation = new ReservationDAO().findById(resId);
    }
    public void cancelReservation(){

        if (reservation.getStartDate().minusDays(3).isBefore(LocalDate.now())) {
            changePaidAmount((float) 0);
            changeStatus("Cancelled");
        }
        if (reservation.getStartDate().isBefore(LocalDate.now())) {
            changePaidAmount((float) (reservation.getPaidAmount() * 0.5));
            changeStatus("Cancelled");
        }
        else {
            changeStatus("Cancelled");
        }
    }

    public void changeStartDate(LocalDate startDate){
        if (startDate.isBefore(reservation.getEndDate())){
            reservation.setStartDate(startDate);
            new ReservationDAO().update(reservation);
        }
    }

    public void changeEndDate(LocalDate endDate){
        if (endDate.isAfter(reservation.getStartDate())){
            reservation.setEndDate(endDate);
            new ReservationDAO().update(reservation);
        }
    }

    public void changeDates(LocalDate startDate, LocalDate endDate){
        reservation.setEndDate(endDate);
        reservation.setStartDate(startDate);
        new ReservationDAO().update(reservation);

    }

    public void changeDescription(String description){
        reservation.setDescription(description);
        new ReservationDAO().update(reservation);
    }

    public void changePaidAmount(Float paidAmount){
        reservation.setPaidAmount(paidAmount);
        new ReservationDAO().update(reservation);
    }

    public void changeStatus(String status){
        reservation.setStatus(status);
        new ReservationDAO().update(reservation);
    }

    public void changeIsPaid(Boolean isPaid){
        reservation.setPaid(isPaid);
        new ReservationDAO().update(reservation);
    }

    public void setNewPenalties(String reason, Integer amount, Boolean isPaid){
        List<Penalty> penalties = reservation.getPenalties();
        Penalty penalty = new Penalty();
        penalty.setReason(reason);
        penalty.setAmount(amount);
        penalty.setPaid(isPaid);
        penalty.setReservation(reservation);
        penalties.add(penalty);
        reservation.setPenalties(penalties);
        new ReservationDAO().update(reservation);
    }
}
