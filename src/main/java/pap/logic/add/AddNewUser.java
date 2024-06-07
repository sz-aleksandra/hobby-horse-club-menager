package pap.logic.add;

import pap.db.entities.*;
import pap.db.dao.*;

import java.time.LocalDate;

public class AddNewUser {
    private final Client user;
    private Address userAddress;

    boolean address;

    public AddNewUser(String username, String password, String name, String surname, String email, String phoneNumber,
                      String country, String city, String street, String postalCode, String streetNo,
                      LocalDate birthDate, String nationality, String gender, boolean activeStatus) {
        userAddress = new Address();
        userAddress.setCountry(country);
        userAddress.setCity(city);
        userAddress.setStreet(street);
        userAddress.setStreetNumber(streetNo);
        userAddress.setPostalCode(postalCode);
        user = new Client();
        user.setUsername(username);
        user.setPassword(password);
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setBirthDate(birthDate);
        user.setNationality(nationality);
        user.setGender(gender);
        user.setAddress(userAddress);
        user.setActive(activeStatus);
        address = true;
    }

    public AddNewUser(String username, String password, String name, String surname, String email, String phoneNumber,
                      LocalDate birthDate, String nationality, String gender, boolean activeStatus) {
        user = new Client();
        user.setUsername(username);
        user.setPassword(password);
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setBirthDate(birthDate);
        user.setNationality(nationality);
        user.setGender(gender);
        user.setActive(activeStatus);
        address = false;
    }

    public void insertIntoDatabase() {
        if (address){
            new ClientDAO().createWithNewAddress(user);
        } else {
            new ClientDAO().create(user);
        }
    }
}
