package logic.validators;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import pap.logic.validators.OfferValidator;

public class OfferValidatorTest {

    @Test
    public void testValidOffer() {
        OfferValidator offerValidator = new OfferValidator("Double", "Example Offer", LocalDate.now(), "Valid description",
                1, 2, 2, true, false, 100.0f, true);

        List<Integer> validationCodes = offerValidator.validate();

        assertEquals(List.of(), validationCodes);
    }

    @Test
    public void testInvalidRoomType() {
        OfferValidator offerValidator = new OfferValidator("S", "Example Offer", LocalDate.now(), "Valid description",
                1, 2, 2, true, false, 100.0f, true);

        List<Integer> validationCodes = offerValidator.validate();

        assertEquals(List.of(601), validationCodes);
    }

    @Test
    public void testInvalidName() {
        OfferValidator offerValidator = new OfferValidator("Double", "A", LocalDate.now(), "Valid description",
                1, 2, 2, true, false, 100.0f, true);

        List<Integer> validationCodes = offerValidator.validate();

        assertEquals(List.of(603), validationCodes);
    }

    @Test
    public void testInvalidAddDate() {
        OfferValidator offerValidator = new OfferValidator("Double", "Example Offer", LocalDate.now().plusDays(1), "Valid description",
                1, 2, 2, true, false, 100.0f, true);

        List<Integer> validationCodes = offerValidator.validate();

        assertEquals(List.of(605), validationCodes);
    }

    @Test
    public void testInvalidDescription() {
        OfferValidator offerValidator = new OfferValidator("Double", "Example Offer", LocalDate.now(), "Short",
                1, 2, 2, true, false, 100.0f, true);

        List<Integer> validationCodes = offerValidator.validate();

        assertEquals(List.of(606), validationCodes);
    }

    @Test
    public void testInvalidBathroomNo() {
        OfferValidator offerValidator = new OfferValidator("Double", "Example Offer", LocalDate.now(), "Valid description",
                -1, 2, 2, true, false, 100.0f, true);

        List<Integer> validationCodes = offerValidator.validate();

        assertEquals(List.of(608), validationCodes);
    }

    @Test
    public void testInvalidRoomNo() {
        OfferValidator offerValidator = new OfferValidator("Double", "Example Offer", LocalDate.now(), "Valid description",
                1, -2, 2, true, false, 100.0f, true);

        List<Integer> validationCodes = offerValidator.validate();

        assertEquals(List.of(609), validationCodes);
    }

    @Test
    public void testInvalidBedNo() {
        OfferValidator offerValidator = new OfferValidator("Double", "Example Offer", LocalDate.now(), "Valid description",
                1, 2, -2, true, false, 100.0f, true);

        List<Integer> validationCodes = offerValidator.validate();

        assertEquals(List.of(610), validationCodes);
    }

    @Test
    public void testInvalidPrice() {
        OfferValidator offerValidator = new OfferValidator("Double", "Example Offer", LocalDate.now(), "Valid description",
                1, 2, 2, true, false, -100.0f, true);

        List<Integer> validationCodes = offerValidator.validate();

        assertEquals(List.of(611), validationCodes);
    }


    @Test
    public void testInvalidCombined() {
        OfferValidator offerValidator = new OfferValidator("S", "A", LocalDate.now().plusDays(1), "Short",
                -1, -2, -2, true, false, -100.0f, true);

        List<Integer> validationCodes = offerValidator.validate();

        assertEquals(List.of(601, 603, 605, 606, 608, 609, 610, 611), validationCodes);
    }
}
