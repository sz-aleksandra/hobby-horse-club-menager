package pap.logic.add;

import pap.db.dao.HotelDAO;
import pap.db.entities.Address;
import pap.db.entities.Hotel;
import pap.db.entities.Owner;

import java.time.LocalDate;

public class AddNewHotel {
    private final Hotel hotel;

    public AddNewHotel(String name, LocalDate addDate, String description, String country, String city,
                       String street, String postalCode, String streetNo, String email, String website, String phoneNumber,
                       String bankAccountNumber, boolean isActive, Owner owner) {
        this.hotel = new Hotel();
        this.hotel.setName(name);
        this.hotel.setAddDate(addDate);
        this.hotel.setDescription(description);
        Address address = new Address();
        address.setCountry(country);
        address.setCity(city);
        address.setStreet(street);
        address.setPostalCode(postalCode);
        address.setStreetNumber(streetNo);
        this.hotel.setAddress(address);
        this.hotel.setEmail(email);
        this.hotel.setWebsite(website);
        this.hotel.setPhoneNumber(phoneNumber);
        this.hotel.setBankAccountNumber(bankAccountNumber);
        this.hotel.setActive(isActive);
        this.hotel.setOwner(owner);
    }

    public void insertIntoDatabase() {
        new HotelDAO().createWithNewAddress(hotel);
    }
}
