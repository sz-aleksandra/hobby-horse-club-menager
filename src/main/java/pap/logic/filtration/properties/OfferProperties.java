package pap.logic.filtration.properties;

import lombok.Getter;

@Getter
public class OfferProperties extends Properties {
    private final String roomType;
    private final String name;
    private final Integer bathroomNoLowerBound;
    private final Integer bathroomNoUpperBound;
    private final Integer roomNoLowerBound;
    private final Integer roomNoUpperBound;
    private final Integer bedNoLowerBound;
    private final Integer bedNoUpperBound;
    private final Boolean hasKitchen;
    private final Boolean hasWifi;
    private final Boolean smokingAllowed;
    private final Boolean hasParking;
    private final Boolean petFriendly;
    private final Integer priceLowerBound;
    private final Integer priceUpperBound;

    public OfferProperties(String roomType, String name,
                           Integer bathroomNoLowerBound, Integer bathroomNoUpperBound,
                           Integer roomNoLowerBound, Integer roomNoUpperBound,
                           Integer bedNoLowerBound, Integer bedNoUpperBound,
                           Boolean hasKitchen, Boolean petFriendly,
                           Boolean hasWifi, Boolean smokingAllowed, Boolean hasParking,
                           Integer priceLowerBound, Integer priceUpperBound) {
        this.roomType = trimIfNotNull(roomType);
        this.name = trimIfNotNull(name);
        this.bathroomNoLowerBound = bathroomNoLowerBound;
        this.bathroomNoUpperBound = bathroomNoUpperBound;
        this.roomNoLowerBound = roomNoLowerBound;
        this.roomNoUpperBound = roomNoUpperBound;
        this.bedNoLowerBound = bedNoLowerBound;
        this.bedNoUpperBound = bedNoUpperBound;
        this.hasKitchen = hasKitchen;
        this.petFriendly = petFriendly;
        this.hasWifi = hasWifi;
        this.smokingAllowed = smokingAllowed;
        this.hasParking = hasParking;
        this.priceUpperBound = priceUpperBound;
        this.priceLowerBound = priceLowerBound;
    }
}
