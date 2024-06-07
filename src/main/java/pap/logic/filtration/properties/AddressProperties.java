package pap.logic.filtration.properties;

import lombok.Getter;

@Getter
public class AddressProperties extends Properties {
    private final String country;
    private final String city;
    private final String street;
    private final String postalCode;
    private final String streetNo;

    public AddressProperties(String country, String city, String street, String postalCode, String streetNo) {
        this.country = trimIfNotNull(country);
        this.city = trimIfNotNull(city);
        this.street = trimIfNotNull(street);
        this.postalCode = trimIfNotNull(postalCode);
        this.streetNo = trimIfNotNull(streetNo);
    }
}
