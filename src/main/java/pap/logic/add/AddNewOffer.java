package pap.logic.add;

import pap.db.dao.OfferDAO;
import pap.db.entities.Hotel;
import pap.db.entities.Offer;

import java.time.LocalDate;

public class AddNewOffer {
    private final Offer offer;
    public AddNewOffer(String roomType, String name, LocalDate addDate, String description,
                       Integer bathroomNo, Integer roomNo, Integer bedNo, boolean hasKitchen, boolean petFriendly,
                       Float price, boolean isActive, Hotel hotel) {
        this.offer = new Offer();
        this.offer.setRoomType(roomType);
        this.offer.setName(name);
        this.offer.setAddDate(addDate);
        this.offer.setDescription(description);
        this.offer.setBathroomNumber(bathroomNo);
        this.offer.setRoomNumber(roomNo);
        this.offer.setBedNumber(bedNo);
        this.offer.setHasKitchen(hasKitchen);
        this.offer.setPetFriendly(petFriendly);
        this.offer.setPrice(price);
        this.offer.setActive(isActive);
        this.offer.setHotel(hotel);
    }

    public void insertIntoDatabase() {
        new OfferDAO().create(offer);
    }
}
