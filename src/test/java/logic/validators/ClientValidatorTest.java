package logic.validators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import pap.logic.validators.ClientValidator;

import java.time.LocalDate;

class ClientValidatorTest {

    @Test
    void testClientValidator_TooShortClientUsername() {
        List <Integer> result = new ArrayList<>();
        ClientValidator.validateUsername("short", result);
        result.remove(Integer.valueOf(1));
        assertEquals(List.of(101), result);
    }

    @Test
    void testClientValidator_TooLongClientUsername() {
        List <Integer> result = new ArrayList<>();
        ClientValidator.validateUsername("a".repeat(65), result);
        result.remove(Integer.valueOf(1));
        assertEquals(List.of(102), result);
    }

    @Test
    void testClientValidator_ClientUsernameContainsIllegalChar() {
        List <Integer> result = new ArrayList<>();
        ClientValidator.validateUsername("Client@Name", result);
        result.remove(Integer.valueOf(1));
        assertEquals(List.of(103), result);
    }

    @Test
    void testClientValidator_ClientUsernameContainsIllegalKeyword() {
        List <Integer> result = new ArrayList<>();
        ClientValidator.validateUsername("adminClient", result);
        result.remove(Integer.valueOf(1));
        assertEquals(List.of(104), result);
    }

    @Test
    void testClientValidator_TooShortPassword() {
        List <Integer> result = new ArrayList<>();
        ClientValidator.validatePassword("Short1!", result, "ValidClientName");
        result.remove(Integer.valueOf(1));
        assertEquals(List.of(106), result);
    }

    @Test
    void testClientValidator_TooLongPassword() {
        List <Integer> result = new ArrayList<>();
        ClientValidator.validatePassword("a".repeat(65) + "A!1", result, "ValidClientName");
        result.remove(Integer.valueOf(1));
        assertEquals(List.of(107), result);
    }

    @Test
    void testClientValidator_PasswordContainsClientName() {
        List <Integer> result = new ArrayList<>();
        ClientValidator.validatePassword("ValidClientName1!", result, "ValidClientName");
        result.remove(Integer.valueOf(1));
        assertEquals(List.of(108), result);
    }

    @Test
    void testClientValidator_PasswordDoesntContainCapital() {
        List <Integer> result = new ArrayList<>();
        ClientValidator.validatePassword("validpassword1!", result, "ValidClientName");
        result.remove(Integer.valueOf(1));
        assertEquals(List.of(109), result);
    }

    @Test
    void testClientValidator_PasswordDoesntContainLower() {
        List <Integer> result = new ArrayList<>();
        ClientValidator.validatePassword("VALIDPASSWORD1!", result, "ValidClientName");
        result.remove(Integer.valueOf(1));
        assertEquals(List.of(110), result);
    }

    @Test
    void testClientValidator_PasswordDoesntContainNumber() {
        List <Integer> result = new ArrayList<>();
        ClientValidator.validatePassword("ValidPassword!", result, "ValidClientName");
        result.remove(Integer.valueOf(1));
        assertEquals(List.of(111), result);
    }

    @Test
    void testClientValidator_PasswordDoesntContainSpecialChar() {
        List <Integer> result = new ArrayList<>();
        ClientValidator.validatePassword("ValidPassword1", result, "ValidClientName");
        result.remove(Integer.valueOf(1));
        assertEquals(List.of(112), result);
    }

    @Test
    void testClientValidator_CorrectCredentials() {
        List <Integer> result = new ArrayList<>();
        ClientValidator.validatePassword("ValidPassword1!", result, "ValidClientName");
        result.remove(Integer.valueOf(1));
        assertEquals(List.of(), result);
    }

    @Test
    void testClientValidator_ClientUsernameTooShortAndPasswordTooLong() {
        List <Integer> result = new ArrayList<>();
        ClientValidator.validateUsername("Usr", result);
        ClientValidator.validatePassword("a".repeat(65) + "ValidPassword1!", result, "Usr");
        result.remove(Integer.valueOf(1));
        assertEquals(List.of(101, 107), result);
    }

    @Test
    void testClientValidator_ClientNameTooLongAndContainsIllegalChar() {
        List <Integer> result = new ArrayList<>();
        ClientValidator.validateUsername("a".repeat(65) + "$", result);
        ClientValidator.validatePassword("ValidPassw0rd!", result, "a".repeat(65) + "$");
        result.remove(Integer.valueOf(1));
        assertEquals(List.of(102, 103), result);
    }

    @Test
    void testClientValidator_ClientNameContainsMultipleIllegalKeywords() {
        List <Integer> result = new ArrayList<>();
        ClientValidator.validateUsername("AdminClientRoot", result);
        ClientValidator.validatePassword("ValidPassword1!", result, "AdminClientRoot");
        result.remove(Integer.valueOf(1));
        assertEquals(List.of(104), result);
    }

    @Test
    void testClientValidator_PasswordTooShortAndDoesntContainSpecialChar() {
        List <Integer> result = new ArrayList<>();
        ClientValidator.validateUsername("ValidClientName", result);
        ClientValidator.validatePassword("Pwd1", result, "ValidClientName");
        result.remove(Integer.valueOf(1));
        assertEquals(List.of(106, 112), result);
    }

    @Test
    void testClientValidator_PasswordTooLongAndContainsClientName() {
        List <Integer> result = new ArrayList<>();
        ClientValidator.validateUsername("ValidClientName", result);
        ClientValidator.validatePassword("a".repeat(65) + "ValidClientName1!", result, "ValidClientName");
        result.remove(Integer.valueOf(1));
        assertEquals(List.of(107, 108), result);
    }

    @Test
    void testClientValidator_ClientNameContainsIllegalCharAndPasswordContainsSpecialChar() {
        List <Integer> result = new ArrayList<>();
        ClientValidator.validateUsername("Client@Name", result);
        ClientValidator.validatePassword("SecurePwd1!", result, "Client@Name");
        result.remove(Integer.valueOf(1));
        assertEquals(List.of(103), result);
    }

    @Test
    void testClientValidator_ClientNameContainsIllegalKeywordAndPasswordDoesntContainLower() {
        List <Integer> result = new ArrayList<>();
        ClientValidator.validateUsername("AdminClient", result);
        ClientValidator.validatePassword("SECUREPWD1!", result, "AdminClient");
        result.remove(Integer.valueOf(1));
        assertEquals(List.of(104, 110), result);
    }

    @Test
    void testClientValidator_PasswordContainsCapitalLowerNumberAndSpecialChar() {
        List <Integer> result = new ArrayList<>();
        ClientValidator.validateUsername("ValidClientName", result);
        ClientValidator.validatePassword("SecurePwd1!", result, "ValidClientName");
        result.remove(Integer.valueOf(1));
        assertEquals(List.of(), result);
    }

    @Test
    void testClientValidator_TooShortName() {
        List<Integer> codes = new ArrayList<>();
        ClientValidator.validateName("J", codes);
        codes.remove(Integer.valueOf(1));
        assertEquals(List.of(201), codes);
    }

    @Test
    void testClientValidator_TooLongName() {
        List<Integer> codes = new ArrayList<>();
        ClientValidator.validateName("a".repeat(65), codes);
        codes.remove(Integer.valueOf(1));
        assertEquals(List.of(202), codes);
    }

    @Test
    void testClientValidator_TooShortSurname() {
        List<Integer> codes = new ArrayList<>();
        ClientValidator.validateSurname("D", codes);
        codes.remove(Integer.valueOf(1));
        assertEquals(List.of(204), codes);
    }

    @Test
    void testClientValidator_TooLongSurname() {
        List<Integer> codes = new ArrayList<>();
        ClientValidator.validateSurname("a".repeat(65), codes);
        codes.remove(Integer.valueOf(1));
        assertEquals(List.of(205), codes);
    }

    @Test
    void testClientValidator_TooShortEmail() {
        List<Integer> codes = new ArrayList<>();
        ClientValidator.validateEmail("a@bc", codes);
        codes.remove(Integer.valueOf(1));
        assertEquals(List.of(113, 115), codes);
    }

    @Test
    void testClientValidator_TooLongEmail() {
        List<Integer> codes = new ArrayList<>();
        ClientValidator.validateEmail("a".repeat(65) + "@", codes);
        codes.remove(Integer.valueOf(1));
        assertEquals(List.of(114, 115), codes);
    }

    @Test
    void testClientValidator_WrongEmailFormat() {
        List<Integer> codes = new ArrayList<>();
        ClientValidator.validateEmail("invalid-email", codes);
        codes.remove(Integer.valueOf(1));
        assertEquals(List.of(115), codes);
    }

    @Test
    void testClientValidator_WrongEmailFormatSecond() {
        List<Integer> codes = new ArrayList<>();
        ClientValidator.validateEmail("a@aa.", codes);
        codes.remove(Integer.valueOf(1));
        assertEquals(List.of(115), codes);
    }

    @Test
    void testClientValidator_WrongEmailFormatThird() {
        List<Integer> codes = new ArrayList<>();
        ClientValidator.validateEmail("a@#.a", codes);
        codes.remove(Integer.valueOf(1));
        assertEquals(List.of(115), codes);
    }

    @Test
    void testClientValidator_WrongEmailFormatFourth() {
        List<Integer> codes = new ArrayList<>();
        ClientValidator.validateEmail("a@a.$", codes);
        codes.remove(Integer.valueOf(1));
        assertEquals(List.of(115), codes);
    }

    @Test
    void testClientValidator_WrongEmailFormatFifth() {
        List<Integer> codes = new ArrayList<>();
        ClientValidator.validateEmail("a@..a", codes);
        codes.remove(Integer.valueOf(1));
        assertEquals(List.of(115), codes);
    }

    @Test
    void testClientValidator_ValidEmail() {
        List<Integer> codes = new ArrayList<>();
        ClientValidator.validateEmail("a@a.aa", codes);
        codes.remove(Integer.valueOf(1));
        assertEquals(List.of(), codes);
    }

    @Test
    void testClientValidator_ValidEmailSecond() {
        List<Integer> codes = new ArrayList<>();
        ClientValidator.validateEmail("a@pw.edu.pl", codes);
        codes.remove(Integer.valueOf(1));
        assertEquals(List.of(), codes);
    }

    @Test
    void testClientValidator_ValidEmailThird() {
        List<Integer> codes = new ArrayList<>();
        ClientValidator.validateEmail("a-b@a.aa", codes);
        codes.remove(Integer.valueOf(1));
        assertEquals(List.of(), codes);
    }

    @Test
    void testClientValidator_TooShortPhoneNumber() {
        List<Integer> codes = new ArrayList<>();
        ClientValidator.validatePhoneNumber("123", codes);
        codes.remove(Integer.valueOf(1));
        assertEquals(List.of(116), codes);
    }

    @Test
    void testClientValidator_TooLongPhoneNumber() {
        List<Integer> codes = new ArrayList<>();
        ClientValidator.validatePhoneNumber("12345678901234567890", codes);
        codes.remove(Integer.valueOf(1));
        assertEquals(List.of(117), codes);
    }

    @Test
    void testClientValidator_PhoneNumberWithLetters() {
        List<Integer> codes = new ArrayList<>();
        ClientValidator.validatePhoneNumber("123456789a", codes);
        codes.remove(Integer.valueOf(1));
        assertEquals(List.of(118), codes);
    }

    @Test
    void testClientValidator_PhoneNumberWithSpecialChars() {
        List<Integer> codes = new ArrayList<>();
        ClientValidator.validatePhoneNumber("123456789$", codes);
        codes.remove(Integer.valueOf(1));
        assertEquals(List.of(118), codes);
    }

    @Test
    void testClientValidator_InvalidBirthDate() {
        List<Integer> codes = new ArrayList<>();
        ClientValidator.validateBirthDate(LocalDate.now(), codes);
        codes.remove(Integer.valueOf(1));
        assertEquals(List.of(207), codes);
    }

    @Test
    void testClientValidator_TooShortNationality() {
        List<Integer> codes = new ArrayList<>();
        ClientValidator.validateNationality("U", codes);
        codes.remove(Integer.valueOf(1));
        assertEquals(List.of(208), codes);
    }

    @Test
    void testClientValidator_TooLongNationality() {
        List<Integer> codes = new ArrayList<>();
        ClientValidator.validateNationality("a".repeat(65), codes);
        codes.remove(Integer.valueOf(1));
        assertEquals(List.of(209), codes);
    }

    @Test
    void testClientValidator_InvalidGender() {
        List<Integer> codes = new ArrayList<>();
        ClientValidator.validateGender("unknown", codes);
        codes.remove(Integer.valueOf(1));
        assertEquals(List.of(211), codes);
    }

    @Test
    void testClientValidator_ValidateCredentials() {
        ClientValidator vuc = new ClientValidator("ValidClientName", "InvalidPassword", "Jan", "Kowalski",
                "jankowalski@pw.edu.pl", "123456789", "Poland", "Warsaw", "Nowowiejska", "00-123", "1",
                LocalDate.parse("2020-01-01"), "Polish", "Male");
        List <Integer> codes = vuc.validateClientCredentials();
        codes.remove(Integer.valueOf(1));
        assertEquals(List.of(111, 112), codes);
    }

    @Test
    void testClientValidator_ValidateCredentialsWrongAddress() {
        ClientValidator vuc = new ClientValidator("ValidClientName", "ValidPassword1!", "Jan", "Kowalski",
                "jankowalski@pw.edu.pl", "123456789", "P", "Warsaw", "Nowowiejska", "00", "1",
                LocalDate.parse("2020-01-01"), "Polish", "Male");
        List <Integer> codes = vuc.validateClientCredentials();
        codes.remove(Integer.valueOf(1));
        assertEquals(List.of(501, 507), codes);
    }

    @Test
    void testClientValidator_ValidateCredentialsJoinCodes() {
        ClientValidator vuc = new ClientValidator("ValidClientName", "ValidPassword!", "Jan", "Kowalski",
                "jankowalski@pw.edu.pl", "123456789", "P", "Warsaw", "Nowowiejska", "00", "1",
                LocalDate.parse("2020-01-01"), "Polish", "Male");
        List <Integer> codes = vuc.validateClientCredentials();
        codes.remove(Integer.valueOf(1));
        assertEquals(List.of(111, 501, 507), codes);
    }
}
