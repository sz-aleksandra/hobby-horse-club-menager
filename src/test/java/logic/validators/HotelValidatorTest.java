package logic.validators;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import pap.logic.validators.HotelValidator;

public class HotelValidatorTest {

    @Test
    public void testValidHotel() {
        HotelValidator hotelValidator = new HotelValidator("Example Hotel", LocalDate.now().minusDays(1), "Valid description",
                "Country", "City", "Street", "12345", "1", "valid@example.com", "example.com", "123456789", "1234567890123456", true, 0);

        List<Integer> validationCodes = hotelValidator.validate();
        validationCodes.remove(Integer.valueOf(1));
        assertEquals(List.of(), validationCodes);
    }

    @Test
    public void testInvalidName() {
        HotelValidator hotelValidator = new HotelValidator("A", LocalDate.now().minusDays(1), "Valid description",
                "Country", "City", "Street", "12345", "1", "valid@example.com", "example.com", "123456789", "1234567890123456", true, 0);

        List<Integer> validationCodes = hotelValidator.validate();
        validationCodes.remove(Integer.valueOf(1));
        assertEquals(List.of(701), validationCodes);
    }

    @Test
    public void testInvalidAddDate() {
        HotelValidator hotelValidator = new HotelValidator("Example Hotel", LocalDate.now().plusDays(1), "Valid description",
                "Country", "City", "Street", "12345", "1", "valid@example.com", "example.com", "123456789", "1234567890123456", true, 0);

        List<Integer> validationCodes = hotelValidator.validate();
        validationCodes.remove(Integer.valueOf(1));
        assertEquals(List.of(703), validationCodes);
    }

    @Test
    public void testInvalidDescription() {
        HotelValidator hotelValidator = new HotelValidator("Example Hotel", LocalDate.now().minusDays(1), "Short",
                "Country", "City", "Street", "12345", "1", "valid@example.com", "example.com", "123456789", "1234567890123456", true, 0);

        List<Integer> validationCodes = hotelValidator.validate();
        validationCodes.remove(Integer.valueOf(1));
        assertEquals(List.of(704), validationCodes);
    }

    @Test
    public void testInvalidEmail() {
        HotelValidator hotelValidator = new HotelValidator("Example Hotel", LocalDate.now().minusDays(1), "Valid description",
                "Country", "City", "Street", "12345", "1", "aaa", "example.com", "123456789", "1234567890123456", true, 0);

        List<Integer> validationCodes = hotelValidator.validate();
        validationCodes.remove(Integer.valueOf(1));
        assertEquals(List.of(706, 708), validationCodes);
    }

    @Test
    public void testInvalidWebsite() {
        HotelValidator hotelValidator = new HotelValidator("Example Hotel", LocalDate.now().minusDays(1), "Valid description",
                "Country", "City", "Street", "12345", "1", "valid@example.com", "in", "123456789", "1234567890123456", true, 0);

        List<Integer> validationCodes = hotelValidator.validate();
        validationCodes.remove(Integer.valueOf(1));
        assertEquals(List.of(709, 711), validationCodes);
    }

    @Test
    public void testInvalidPhoneNumber() {
        HotelValidator hotelValidator = new HotelValidator("Example Hotel", LocalDate.now().minusDays(1), "Valid description",
                "Country", "City", "Street", "12345", "1", "valid@example.com", "example.com", "invalid", "1234567890123456", true, 0);

        List<Integer> validationCodes = hotelValidator.validate();
        validationCodes.remove(Integer.valueOf(1));
        assertEquals(List.of(712, 714), validationCodes);
    }

    @Test
    public void testInvalidBankAccountNumber() {
        HotelValidator hotelValidator = new HotelValidator("Example Hotel", LocalDate.now().minusDays(1), "Valid description",
                "Country", "City", "Street", "12345", "1", "valid@example.com", "example.com", "123456789", "123456789012345678901234567890123456", true, 0);

        List<Integer> validationCodes = hotelValidator.validate();
        validationCodes.remove(Integer.valueOf(1));
        assertEquals(List.of(716), validationCodes);
    }
}
