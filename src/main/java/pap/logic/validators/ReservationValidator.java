package pap.logic.validators;

import java.time.LocalDate;
import java.util.*;
import pap.db.dao.*;
import pap.db.entities.*;

public class ReservationValidator {

    private final LocalDate dateStart;
    private final LocalDate dateEnd;
    private final Integer offerId;
    private final PaymentMethod creditCard;

    List<Integer> codes;

    public ReservationValidator(LocalDate dateStart, LocalDate dateEnd, PaymentMethod creditCard, Integer offerId){
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.offerId = offerId;
        this.creditCard = creditCard;
        this.codes = new ArrayList<>();
    }

    public List <Integer> validate(){
        checkOfferActive(offerId, codes);
        checkDateAvailable(offerId, dateStart, dateEnd, codes);
        checkDateCorrect(dateStart, dateEnd, codes);
        checkCreditCard(creditCard, codes);
        return codes;
    }

    private static void checkOfferActive(Integer offerId, List <Integer> codes){
        try {
            Offer offer = new OfferDAO().findById(offerId);
            if (!offer.isActive()){
                codes.add(1);
            }
        } catch (Exception ignored) {}
    }

    private static void checkCreditCard(PaymentMethod creditCard, List <Integer> codes){
        try {
            if (creditCard == null){
                codes.add(4);
            }
        } catch (Exception ignored) {}
    }

    private static void checkDateAvailable(Integer offerId, LocalDate dateStart, LocalDate dateEnd, List<Integer> codes){
        try {
            List<Reservation> reservations = new ReservationDAO().findByOfferId(offerId);
            for (Reservation reservation: reservations){
                if (reservation.getStatus().equals("Active") && !(reservation.getEndDate().isBefore(dateStart) || reservation.getStartDate().isAfter(dateEnd))){
                    codes.add(2);
                    return;
                }
            }
        } catch (Exception ignored) {}
    }

    private static void checkDateCorrect(LocalDate dateStart, LocalDate dateEnd, List<Integer> codes){
        System.out.println(dateStart + " " + dateEnd);
        if (dateEnd.isBefore(dateStart)){
            codes.add(3);
        }
    }
}