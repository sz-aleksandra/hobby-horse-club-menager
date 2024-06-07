package pap.logic.validators;


import jakarta.persistence.NoResultException;
import pap.db.dao.DiscountDAO;
import pap.db.dao.HotelDAO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HotelValidator {
    private static final int MIN_NAME_LENGTH = 2;
    private static final int MAX_NAME_LENGTH = 32;
    private static final int MIN_DESC_LENGTH = 10;
    private static final int MAX_DESC_LENGTH = 100;
    private static final int MIN_EMAIL_LENGTH = 5;
    private static final int MAX_EMAIL_LENGTH = 30;
    private static final int MIN_WEBSITE_LENGTH = 4;
    private static final int MAX_WEBSITE_LENGTH = 64;
    private static final int MIN_PHONE_NUMBER_LENGTH = 9;
    private static final int MAX_PHONE_NUMBER_LENGTH = 14;
    private static final int MIN_BANK_ACCOUNT_NUMBER = 15;
    private static final int MAX_BANK_ACCOUNT_NUMBER = 32;

    private final String name;
    private final String description;
    private final LocalDate addDate;
    private final AddressValidator addressValidator;
    private final String email;
    private final String website;
    private final String phoneNumber;
    private final String bankAccountNumber;
    private final boolean isActive;
    private final Integer ownerId;
    public HotelValidator(String name, LocalDate addDate, String description, String country, String city,
                          String street, String postalCode, String streetNo, String email, String website, String phoneNumber,
                          String bankAccountNumber, boolean isActive, Integer ownerId) {
        this.name = name;
        this.addDate = addDate;
        this.description = description;
        this.addressValidator = new AddressValidator(country, city, street, postalCode, streetNo);
        this.email = email;
        this.website = website;
        this.phoneNumber = phoneNumber;
        this.bankAccountNumber = bankAccountNumber;
        this.isActive = isActive;
        this.ownerId = ownerId;
    }

    public List<Integer> validate() {
        List <Integer> codes = new ArrayList<>();
        validateName(name, codes, ownerId);
        validateAddDate(addDate, codes);
        validateDescription(description, codes);
        codes.addAll(addressValidator.validateCredentials());
        validateEmail(email, codes);
        validateWebsite(website, codes);
        validatePhoneNumber(phoneNumber, codes);
        validateBankAccountNumber(bankAccountNumber, codes);
        return codes;
    }

    public static void validateName(String name, List <Integer> codes, Integer ownerId) {
        if (name.length() < MIN_NAME_LENGTH) codes.add(701);
        if (name.length() > MAX_NAME_LENGTH) codes.add(702);
        try {
            new HotelDAO().findByNameAndOwnerId(name, ownerId);
            codes.add(717);
        } catch (NoResultException error) {
            // expected situation, do nothing
        }
        catch (Exception anotherError) {
            codes.add(1);
        }
    }

    public static void validateAddDate(LocalDate addDate, List <Integer> codes) {
        if (addDate.isAfter(LocalDate.now())) codes.add(703);
    }

    public static void validateDescription(String description, List <Integer> codes) {
        if (description.length() < MIN_DESC_LENGTH) codes.add(704);
        if (description.length() > MAX_DESC_LENGTH) codes.add(705);
    }

    public static void validateEmail(String email, List <Integer> codes) {
        if (email.length() < MIN_EMAIL_LENGTH) codes.add(706);
        if (email.length() > MAX_EMAIL_LENGTH) codes.add(707);
        if (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$")) codes.add(708);
    }

    public static void validateWebsite(String website, List <Integer> codes) {
        if (website.length() < MIN_WEBSITE_LENGTH) codes.add(709);
        if (website.length() > MAX_WEBSITE_LENGTH) codes.add(710);
        if (!website.matches(".+\\.[a-zA-Z]{2,4}")) codes.add(711);
    }

    public static void validatePhoneNumber(String phoneNumber, List <Integer> codes) {
        if (phoneNumber.length() < MIN_PHONE_NUMBER_LENGTH) codes.add(712);
        if (phoneNumber.length() > MAX_PHONE_NUMBER_LENGTH) codes.add(713);
        if (!phoneNumber.matches("\\d+")) codes.add(714);
    }

    public static void validateBankAccountNumber(String bankAccountNumber, List <Integer> codes) {
        if (bankAccountNumber.length() < MIN_BANK_ACCOUNT_NUMBER) codes.add(715);
        if (bankAccountNumber.length() > MAX_BANK_ACCOUNT_NUMBER) codes.add(716);
    }
}
