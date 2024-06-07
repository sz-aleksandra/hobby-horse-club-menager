package pap.logic.validators;

import jakarta.persistence.NoResultException;
import pap.db.dao.ClientDAO;
import pap.db.dao.OwnerDAO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserValidator {
    private static final int MIN_USERNAME_LENGTH = 8;
    private static final int MAX_USERNAME_LENGTH = 64;
    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int MAX_PASSWORD_LENGTH = 64;
    private static final int MIN_EMAIL_LENGTH = 5;
    private static final int MAX_EMAIL_LENGTH = 64;
    private static final int MIN_PHONE_LENGTH = 9;
    private static final int MAX_PHONE_LENGTH = 14;
    private static final String USERNAME_ILLEGAL_CHARS_REGEX = ".*[@#$%&/\\\\].*";
    private static final List<String> BANNED_KEYWORDS = Arrays.asList("admin", "root", "system");
    private static final String SPECIAL_CHARACTERS_REGEX = ".*[!@#$%^&*()\\[\\]{}\\-_=+/\\\\?.,<>'\";:|§].*";


    private final String username;
    private final String password;
    private final String email;
    private final String phoneNumber;
    private final AddressValidator addressValidator;

    public UserValidator(String username, String password, String email, String phoneNumber, String country,
                         String city, String street, String postalCode, String streetNo) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.addressValidator = new AddressValidator(country, city, street, postalCode, streetNo);
    }

    public List <Integer> validateCredentials() {
        List <Integer> codes = new ArrayList<>();
        validateUsername(username, codes);
        validatePassword(password, codes, username);
        validateEmail(email, codes);
        validatePhoneNumber(phoneNumber, codes);
        validateAddress(addressValidator, codes);
        return codes;
    }

    public static void validateUsername(String username, List <Integer> codes) {
        checkTooShortUsername(username, codes);
        checkTooLongUsername(username, codes);
        checkUsernameContainsIllegalChar(username, codes);
        checkUsernameContainsIllegalKeyword(username, codes);
        checkUsernameIsUnique(username, codes);
        checkUsernameIsUniqueAmongOwners(username, codes);
    }

    public static void validatePassword(String password, List <Integer> codes, String username) {
        checkTooShortPassword(password, codes);
        checkTooLongPassword(password, codes);
        checkPasswordContainsUsername(password, codes, username);
        checkPasswordDoesntContainCapital(password, codes);
        checkPasswordDoesntContainLower(password, codes);
        checkPasswordDoesntContainNumber(password, codes);
        checkPasswordDoesntContainSpecialChar(password, codes);
    }

    public static void validateEmail(String email, List <Integer> codes) {
        checkTooShortEmail(email, codes);
        checkTooLongEmail(email, codes);
        checkEmailFormat(email, codes);
    }

    public static void validatePhoneNumber(String phoneNumber, List <Integer> codes) {
        checkTooShortPhoneNumber(phoneNumber, codes);
        checkTooLongPhoneNumber(phoneNumber, codes);
        checkOnlyNumbersInPhoneNumber(phoneNumber, codes);
    }

    public static void validateAddress(AddressValidator addressValidator, List <Integer> codes) {
        codes.addAll(addressValidator.validateCredentials());
    }

    private static void checkTooShortUsername(String username, List <Integer> codes) {
        if (username.length() < MIN_USERNAME_LENGTH) codes.add(101);
    }
    private static void checkTooLongUsername(String username, List <Integer> codes) {
        if (username.length() > MAX_USERNAME_LENGTH) codes.add(102);
    }
    private static void checkUsernameContainsIllegalChar(String username, List <Integer> codes) {
        if (username.matches(USERNAME_ILLEGAL_CHARS_REGEX)) codes.add(103);
    }
    private static void checkUsernameContainsIllegalKeyword(String username, List <Integer> codes) {
        if (BANNED_KEYWORDS.stream().anyMatch(username.toLowerCase()::contains)) codes.add(104);
    }
    public static void checkUsernameIsUnique(String username, List <Integer> codes) {
        try {
            new ClientDAO().findByUsername(username);
            codes.add(105);
        } catch (NoResultException error) {
            // expected situation, do nothing
        }
        catch (Exception anotherError) {
            codes.add(1);
        }
    }
    private static void checkUsernameIsUniqueAmongOwners(String username, List <Integer> codes) {
        try {
            new OwnerDAO().findByUsername(username);
            codes.add(105);
        } catch (NoResultException error) {
            // expected situation, do nothing
        }
        catch (Exception anotherError) {
            codes.add(1);
        }
    }
    private static void checkTooShortPassword(String password, List <Integer> codes) {
        if (password.length() < MIN_PASSWORD_LENGTH) codes.add(106);
    }
    private static void checkTooLongPassword(String password, List <Integer> codes) {
        if (password.length() > MAX_PASSWORD_LENGTH) codes.add(107);
    }
    private static void checkPasswordContainsUsername(String password, List <Integer> codes, String username) {
        if (password.contains(username)) codes.add(108);
    }
    private static void checkPasswordDoesntContainCapital(String password, List <Integer> codes) {
        if (password.chars().noneMatch(Character::isUpperCase)) codes.add(109);
    }
    private static void checkPasswordDoesntContainLower(String password, List <Integer> codes) {
        if (password.chars().noneMatch(Character::isLowerCase)) codes.add(110);
    }
    private static void checkPasswordDoesntContainNumber(String password, List <Integer> codes) {
        if (password.chars().noneMatch(Character::isDigit)) codes.add(111);
    }
    private static void checkPasswordDoesntContainSpecialChar(String password, List <Integer> codes) {
        if (!password.matches(SPECIAL_CHARACTERS_REGEX)) codes.add(112);
    }

    private static void checkTooShortEmail(String email, List <Integer> codes) {
        if (email.length() < MIN_EMAIL_LENGTH) codes.add(113);
    }
    private static void checkTooLongEmail(String email, List <Integer> codes) {
        if (email.length() > MAX_EMAIL_LENGTH) codes.add(114);
    }
    private static void checkEmailFormat(String email, List <Integer> codes) {
        if (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$")) codes.add(115);
    }
    private static void checkTooShortPhoneNumber(String phoneNumber, List <Integer> codes) {
        if (phoneNumber.length() < MIN_PHONE_LENGTH) codes.add(116);
    }
    private static void checkTooLongPhoneNumber(String phoneNumber, List <Integer> codes) {
        if (phoneNumber.length() > MAX_PHONE_LENGTH) codes.add(117);
    }
    private static void checkOnlyNumbersInPhoneNumber(String phoneNumber, List <Integer> codes) {
        if (!phoneNumber.matches("\\d+")) codes.add(118);
    }
}
