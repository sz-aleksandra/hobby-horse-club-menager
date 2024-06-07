package logic.validators;

import org.junit.jupiter.api.Test;
import pap.logic.validators.RatingValidator;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RatingValidatorTest {

    @Test
    public void testValidRating() {
        RatingValidator ratingValidator = new RatingValidator(4, "Valid comment", LocalDate.now());

        List<Integer> validationCodes = ratingValidator.validate();

        assertEquals(List.of(), validationCodes);
    }

    @Test
    public void testInvalidRating() {
        RatingValidator ratingValidator = new RatingValidator(6, "Valid comment", LocalDate.now());

        List<Integer> validationCodes = ratingValidator.validate();

        assertEquals(List.of(801), validationCodes);
    }

    @Test
    public void testValidComment() {
        RatingValidator ratingValidator = new RatingValidator(3, "Valid comment", LocalDate.now());

        List<Integer> validationCodes = ratingValidator.validate();

        assertEquals(List.of(), validationCodes);
    }

    @Test
    public void testInvalidShortComment() {
        RatingValidator ratingValidator = new RatingValidator(2, "Short", LocalDate.now());

        List<Integer> validationCodes = ratingValidator.validate();

        assertEquals(List.of(802), validationCodes);
    }

    @Test
    public void testInvalidLongComment() {
        RatingValidator ratingValidator = new RatingValidator(5, "a".repeat(101), LocalDate.now());

        List<Integer> validationCodes = ratingValidator.validate();

        assertEquals(List.of(803), validationCodes);
    }

    @Test
    public void testValidDate() {
        RatingValidator ratingValidator = new RatingValidator(4, "Valid comment", LocalDate.now());

        List<Integer> validationCodes = ratingValidator.validate();

        assertEquals(List.of(), validationCodes);
    }

    @Test
    public void testInvalidFutureDate() {
        RatingValidator ratingValidator = new RatingValidator(3, "Valid comment", LocalDate.now().plusDays(1));

        List<Integer> validationCodes = ratingValidator.validate();

        assertEquals(List.of(804), validationCodes);
    }
}
