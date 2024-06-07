package pap.logic.guiAction;

import pap.db.dao.OfferDAO;
import pap.db.dao.RatingDAO;
import pap.db.entities.Offer;

import java.util.HashMap;

public class OfferDetails {
    public HashMap<String, String> getOfferInfo(Integer offerId) {
        HashMap<String, String> offerInfo = new HashMap<>();
        OfferDAO od = new OfferDAO();
        Offer offer = od.findById(offerId);

        offerInfo.put("name", offer.getName());
        offerInfo.put("hotel", offer.getHotel().getName());
        offerInfo.put("price", String.format("%.2f", offer.getPrice()) + " PLN");
        offerInfo.put("description", offer.getDescription());
        offerInfo.put("street", offer.getHotel().getAddress().getStreet());
        offerInfo.put("street_nr", offer.getHotel().getAddress().getStreetNumber());
        offerInfo.put("city", offer.getHotel().getAddress().getCity());
        offerInfo.put("country", offer.getHotel().getAddress().getCountry());
        offerInfo.put("room_type", offer.getRoomType());
        offerInfo.put("rooms_nr", String.valueOf(offer.getRoomNumber()));
        offerInfo.put("bathrooms_nr", String.valueOf(offer.getBathroomNumber()));
        offerInfo.put("people_nr", String.valueOf(offer.getBedNumber()));
        offerInfo.put("review_score", String.valueOf(new RatingDAO().getAverageRatingForOffer(offerId)));
        offerInfo.put("reviews_nr", String.valueOf(new RatingDAO().getNumberOfRatingsForOffer(offerId)));
        String yes = "";
        String no = "";
        if (offer.isHasKitchen())
            yes += "Kitchen:  ✔    ";
        else
            no += "Kitchen:  ✘    ";
        if (offer.isPetFriendly())
            yes += "Pet-friendly:  ✔    ";
        else
            no += "Pet-friendly:  ✘    ";
        if (offer.isHasWifi())
            yes += "Free Wi-Fi:  ✔    ";
        else
            no += "Free Wi-Fi:  ✘    ";
        if (offer.isSmokingAllowed())
            yes += "Smoking allowed:  ✔    ";
        else
            no += "Smoking allowed:  ✘    ";
        if (offer.isHasParking())
            yes += "Parking available:  ✔    ";
        else
            no += "Parking available:  ✘    ";
        offerInfo.put("facilities_yes", yes);
        offerInfo.put("facilities_no", no);
        return offerInfo;
    }
}
