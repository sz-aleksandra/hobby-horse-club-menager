package logic.validators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import java.util.*;

import pap.logic.validators.OwnerValidator;

class OwnerValidatorTest {

    @Test
    void testValidateCredentials_CorrectCredentials() {
        OwnerValidator validator = new OwnerValidator(
                "validUsername", "ValidPassword1!", "ValidCompany", "valid@example.com",
                "123456789", "Poland", "Warsaw", "Nowogrodzka", "00-000", "20/3", "1234567890"
        );
        List <Integer> codes = validator.validateOwnerCredentials();
        codes.remove(Integer.valueOf(1));
        assertEquals(List.of(), codes);
    }

    @Test
    void testValidateCredentials_InvalidUsername_TooShort() {
        OwnerValidator validator = new OwnerValidator(
                "z", "ValidPassword1!", "ValidCompany", "valid@example.com",
                "123456789", "Poland", "Warsaw", "Nowogrodzka", "00-000", "20/3", "1234567890"
        );
        List <Integer> codes = validator.validateOwnerCredentials();
        codes.remove(Integer.valueOf(1));
        assertEquals(List.of(101), codes);
    }

    @Test
    void testValidateCredentials_InvalidUsername_TooLong() {
        OwnerValidator validator = new OwnerValidator(
                "a".repeat(65), "ValidPassword1!", "ValidCompany", "valid@example.com",
                "123456789", "Poland", "Warsaw", "Nowogrodzka", "00-000", "20/3", "1234567890"
        );
        List <Integer> codes = validator.validateOwnerCredentials();
        codes.remove(Integer.valueOf(1));
        assertEquals(List.of(102), codes);
    }

    @Test
    void testValidateCredentials_InvalidUsername_IllegalChar() {
        OwnerValidator validator = new OwnerValidator(
                "user@name", "ValidPassword1!", "ValidCompany", "valid@example.com",
                "123456789", "Poland", "Warsaw", "Nowogrodzka", "00-000", "20/3", "1234567890"
        );
        List <Integer> codes = validator.validateOwnerCredentials();
        codes.remove(Integer.valueOf(1));
        assertEquals(List.of(103), codes);
    }

    @Test
    void testValidateCredentials_InvalidUsername_IllegalKeyword() {
        OwnerValidator validator = new OwnerValidator(
                "adminUser", "ValidPassword1!", "ValidCompany", "valid@example.com",
                "123456789", "Poland", "Warsaw", "Nowogrodzka", "00-000", "20/3", "1234567890"
        );
        List <Integer> codes = validator.validateOwnerCredentials();
        codes.remove(Integer.valueOf(1));
        assertEquals(List.of(104), codes);
    }

    @Test
    void testValidateCredentials_InvalidPassword_TooShort() {
        OwnerValidator validator = new OwnerValidator(
                "ValidUsername", "Pwd1!", "ValidCompany", "valid@example.com",
                "123456789", "Poland", "Warsaw", "Nowogrodzka", "00-000", "20/3", "1234567890"
        );
        List <Integer> codes = validator.validateOwnerCredentials();
        codes.remove(Integer.valueOf(1));
        assertEquals(List.of(106), codes);
    }

    @Test
    void testValidateCredentials_InvalidPassword_TooLong() {
        OwnerValidator validator = new OwnerValidator(
                "ValidUsername", "a".repeat(65) + "ValidPassword1!", "ValidCompany", "valid@example.com",
                "123456789", "Poland", "Warsaw", "Nowogrodzka", "00-000", "20/3", "1234567890"
        );
        List <Integer> codes = validator.validateOwnerCredentials();
        codes.remove(Integer.valueOf(1));
        assertEquals(List.of(107), codes);
    }

    @Test
    void testValidateCredentials_InvalidPassword_ContainsUsername() {
        OwnerValidator validator = new OwnerValidator(
                "ValidUsername", "ValidUsername1!", "ValidCompany", "valid@example.com",
                "123456789", "Poland", "Warsaw", "Nowogrodzka", "00-000", "20/3", "1234567890"
        );
        List <Integer> codes = validator.validateOwnerCredentials();
        codes.remove(Integer.valueOf(1));
        assertEquals(List.of(108), codes);
    }

    @Test
    void testValidateCredentials_InvalidPassword_NoCapital() {
        OwnerValidator validator = new OwnerValidator(
                "ValidUsername", "validpassword1!", "ValidCompany", "valid@example.com",
                "123456789", "Poland", "Warsaw", "Nowogrodzka", "00-000", "20/3", "1234567890"
        );
        List <Integer> codes = validator.validateOwnerCredentials();
        codes.remove(Integer.valueOf(1));
        assertEquals(List.of(109), codes);
    }

    @Test
    void testValidateCredentials_InvalidPassword_NoLower() {
        OwnerValidator validator = new OwnerValidator(
                "ValidUsername", "VALIDPASSWORD1!", "ValidCompany", "valid@example.com",
                "123456789", "Poland", "Warsaw", "Nowogrodzka", "00-000", "20/3", "1234567890"
        );
        List <Integer> codes = validator.validateOwnerCredentials();
        codes.remove(Integer.valueOf(1));
        assertEquals(List.of(110), codes);
    }

    @Test
    void testValidateCredentials_InvalidPassword_NoNumber() {
        OwnerValidator validator = new OwnerValidator(
                "ValidUsername", "ValidPassword!", "ValidCompany", "valid@example.com",
                "123456789", "Poland", "Warsaw", "Nowogrodzka", "00-000", "20/3", "1234567890"
        );
        List <Integer> codes = validator.validateOwnerCredentials();
        codes.remove(Integer.valueOf(1));
        assertEquals(List.of(111), codes);
    }

    @Test
    void testValidateCredentials_InvalidPassword_NoSpecialChar() {
        OwnerValidator validator = new OwnerValidator(
                "ValidUsername", "ValidPassword1", "ValidCompany", "valid@example.com",
                "123456789", "Poland", "Warsaw", "Nowogrodzka", "00-000", "20/3", "1234567890"
        );
        List <Integer> codes = validator.validateOwnerCredentials();
        codes.remove(Integer.valueOf(1));
        assertEquals(List.of(112), codes);
    }

    @Test
    void testValidateCredentials_InvalidCompanyName_TooShort() {
        OwnerValidator validator = new OwnerValidator(
                "ValidUsername", "ValidPassword1!", "A", "valid@example.com",
                "123456789", "Poland", "Warsaw", "Nowogrodzka", "00-000", "20/3", "1234567890"
        );
        List <Integer> codes = validator.validateOwnerCredentials();
        codes.remove(Integer.valueOf(1));
        assertEquals(List.of(301), codes);
    }

    @Test
    void testValidateCredentials_InvalidCompanyName_TooLong() {
        OwnerValidator validator = new OwnerValidator(
                "ValidUsername", "ValidPassword1!", "A".repeat(65), "valid@example.com",
                "123456789", "Poland", "Warsaw", "Nowogrodzka", "00-000", "20/3", "1234567890"
        );
        List <Integer> codes = validator.validateOwnerCredentials();
        codes.remove(Integer.valueOf(1));
        assertEquals(List.of(302), codes);
    }

    @Test
    void testValidateCredentials_InvalidEmail_TooShort() {
        OwnerValidator validator = new OwnerValidator(
                "ValidUsername", "ValidPassword1!", "ValidCompany", "a@b",
                "123456789", "Poland", "Warsaw", "Nowogrodzka", "00-000", "20/3", "1234567890"
        );
        List <Integer> codes = validator.validateOwnerCredentials();
        codes.remove(Integer.valueOf(1));
        assertEquals(List.of(113, 115), codes);
    }

    @Test
    void testValidateCredentials_InvalidEmail_TooLong() {
        OwnerValidator validator = new OwnerValidator(
                "ValidUsername", "ValidPassword1!", "ValidCompany", "a".repeat(65) + "@example.com",
                "123456789", "Poland", "Warsaw", "Nowogrodzka", "00-000", "20/3", "1234567890"
        );
        List <Integer> codes = validator.validateOwnerCredentials();
        codes.remove(Integer.valueOf(1));
        assertEquals(List.of(114), codes);
    }

    @Test
    void testValidateCredentials_InvalidEmail_NoAtSymbol() {
        OwnerValidator validator = new OwnerValidator(
                "ValidUsername", "ValidPassword1!", "ValidCompany", "invalidemail.com",
                "123456789", "Poland", "Warsaw", "Nowogrodzka", "00-000", "20/3", "1234567890"
        );
        List <Integer> codes = validator.validateOwnerCredentials();
        codes.remove(Integer.valueOf(1));
        assertEquals(List.of(115), codes);
    }

    @Test
    void testValidateCredentials_InvalidPhoneNumber_TooShort() {
        OwnerValidator validator = new OwnerValidator(
                "ValidUsername", "ValidPassword1!", "ValidCompany", "valid@example.com",
                "123", "Poland", "Warsaw", "Nowogrodzka", "00-000", "20/3", "1234567890"
        );
        List <Integer> codes = validator.validateOwnerCredentials();
        codes.remove(Integer.valueOf(1));
        assertEquals(List.of(116), codes);
    }

    @Test
    void testValidateCredentials_InvalidPhoneNumber_TooLong() {
        OwnerValidator validator = new OwnerValidator(
                "ValidUsername", "ValidPassword1!", "ValidCompany", "valid@example.com",
                "1234567890123456", "Poland", "Warsaw", "Nowogrodzka", "00-000", "20/3", "1234567890"
        );
        List <Integer> codes = validator.validateOwnerCredentials();
        codes.remove(Integer.valueOf(1));
        assertEquals(List.of(117), codes);
    }

    @Test
    void testValidateCredentials_InvalidNip_TooShort() {
        OwnerValidator validator = new OwnerValidator(
                "ValidUsername", "ValidPassword1!", "ValidCompany", "valid@example.com",
                "123456789", "Poland", "Warsaw", "Nowogrodzka", "00-000", "20/3", "12345678"
        );
        List <Integer> codes = validator.validateOwnerCredentials();
        codes.remove(Integer.valueOf(1));
        assertEquals(List.of(303), codes);
    }

    @Test
    void testValidateCredentials_InvalidNip_NonNumeric() {
        OwnerValidator validator = new OwnerValidator(
                "ValidUsername", "ValidPassword1!", "ValidCompany", "valid@example.com",
                "123456789", "Poland", "Warsaw", "Nowogrodzka", "00-000", "20/3", "12345678aa"
        );
        List <Integer> codes = validator.validateOwnerCredentials();
        codes.remove(Integer.valueOf(1));
        assertEquals(List.of(304), codes);
    }

    @Test
    void testValidateCredentials_WrongEmail() {
        OwnerValidator validator = new OwnerValidator(
                "ValidUsername", "ValidPassword1!", "ValidCompany", "a-@.com",
                "123456789", "Poland", "Warsaw", "Nowogrodzka", "00-000", "20/3", "1234567890"
        );
        List <Integer> codes = validator.validateOwnerCredentials();
        codes.remove(Integer.valueOf(1));
        assertEquals(List.of(115), codes);
    }

    @Test
    void testValidateCredentials_ValidEmail() {
        OwnerValidator validator = new OwnerValidator(
                "ValidUsername", "ValidPassword1!", "ValidCompany", "v_-3d@example.com",
                "123456789", "Poland", "Warsaw", "Nowogrodzka", "00-000", "20/3", "1234567890"
        );
        List <Integer> codes = validator.validateOwnerCredentials();
        codes.remove(Integer.valueOf(1));
        assertEquals(List.of(), codes);
    }
}
