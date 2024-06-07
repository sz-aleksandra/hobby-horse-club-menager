package pap.logic.validators;

import java.time.LocalDate;
import java.util.List;

public class ClientValidator extends UserValidator {
    private static final int MIN_NAME_LENGTH = 2;
    private static final int MAX_NAME_LENGTH = 64;
    private static final int MIN_SURNAME_LENGTH = 2;
    private static final int MAX_SURNAME_LENGTH = 64;
    private static final int MIN_NATIONALITY_LENGTH = 2;
    private static final int MAX_NATIONALITY_LENGTH = 64;

    private final String name;
    private final String surname;
    private final LocalDate birthDate;
    private final String nationality;
    private final String gender;

    public ClientValidator(String username, String password, String name, String surname, String email, String phoneNumber,
                           String country, String city, String street, String postalCode, String streetNo,
                           LocalDate birthDate, String nationality, String gender) {
        super(username, password, email, phoneNumber, country, city, street, postalCode, streetNo);
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.nationality = nationality;
        this.gender = gender;
    }

    public List <Integer> validateClientCredentials() {
        List <Integer> codes = super.validateCredentials();
        validateName(name, codes);
        validateSurname(surname, codes);
        validateBirthDate(birthDate, codes);
        validateNationality(nationality, codes);
        validateGender(gender, codes);
        return codes;
    }

    public static void validateName(String name, List<Integer> codes) {
        checkTooShortName(name, codes);
        checkTooLongName(name, codes);
        checkNameContainsOnlyLetters(name, codes);
    }

    public static void validateSurname(String surname, List <Integer> codes) {
        checkTooShortSurname(surname, codes);
        checkTooLongSurname(surname, codes);
        checkSurnameContainsOnlyLetters(surname, codes);
    }

    public static void validateBirthDate(LocalDate birthDate, List <Integer> codes) {
        checkInvalidBirthDate(birthDate, codes);
    }

    public static void validateNationality(String nationality, List <Integer> codes) {
        checkTooShortNationality(nationality, codes);
        checkTooLongNationality(nationality, codes);
        checkNationalityContainsOnlyLetters(nationality, codes);
    }

    public static void validateGender(String gender, List <Integer> codes) {
        checkGender(gender, codes);
    }

    private static void checkTooShortName(String name, List <Integer> codes) {
        if (name.length() < MIN_NAME_LENGTH) codes.add(201);
    }
    private static void checkTooLongName(String name, List <Integer> codes) {
        if (name.length() > MAX_NAME_LENGTH) codes.add(202);
    }
    private static void checkNameContainsOnlyLetters(String name, List <Integer> codes) {
        if (!name.matches("[a-zA-Z]+")) codes.add(203);
    }
    private static void checkTooShortSurname(String surname, List <Integer> codes) {
        if (surname.length() < MIN_SURNAME_LENGTH) codes.add(204);
    }
    private static void checkTooLongSurname(String surname, List <Integer> codes) {
        if (surname.length() > MAX_SURNAME_LENGTH) codes.add(205);
    }
    private static void checkSurnameContainsOnlyLetters(String surname, List <Integer> codes) {
        if (!surname.matches("[a-zA-Z]+")) codes.add(206);
    }
    private static void checkInvalidBirthDate(LocalDate birthDate, List <Integer> codes) {
        if (!birthDate.isBefore(LocalDate.now())) codes.add(207);
    }
    private static void checkTooShortNationality(String nationality, List <Integer> codes) {
        if (nationality.length() < MIN_NATIONALITY_LENGTH) codes.add(208);
    }
    private static void checkTooLongNationality(String nationality, List <Integer> codes) {
        if (nationality.length() > MAX_NATIONALITY_LENGTH) codes.add(209);
    }
    private static void checkNationalityContainsOnlyLetters(String nationality, List <Integer> codes) {
        if (!nationality.matches("[a-zA-Z]+")) codes.add(210);
    }
    private static void checkGender(String gender, List <Integer> codes) {
        if (!gender.equalsIgnoreCase("male") && !gender.equalsIgnoreCase("female") && !gender.equalsIgnoreCase("other"))
            codes.add(211);
    }
}
