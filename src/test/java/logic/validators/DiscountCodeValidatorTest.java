package logic.validators;

import org.junit.jupiter.api.Test;
import pap.db.entities.Hotel;
import pap.logic.validators.DiscountCodeValidator;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DiscountCodeValidatorTest {

    @Test
    public void validate_ValidDiscountCode_ShouldNotAddError() {
        DiscountCodeValidator validator = new DiscountCodeValidator(
                "ABCDE12345", 0, 0, "Valid description", 10.0f, new Hotel(), true);
        List<Integer> validationCodes = validator.validate();
        validationCodes.remove(Integer.valueOf(909));
        assertEquals(List.of(), validationCodes);
    }

    @Test
    public void validate_InvalidCodeLength_ShouldAddErrorCode901() {
        DiscountCodeValidator validator = new DiscountCodeValidator(
                "ABC", 0, 0, "Valid description", 10.0f, new Hotel(), true);
        List<Integer> validationCodes = validator.validate();
        validationCodes.remove(Integer.valueOf(909));
        assertEquals(List.of(901), validationCodes);
    }

    @Test
    public void validate_InvalidCodeCharacters_ShouldAddErrorCode902() {
        DiscountCodeValidator validator = new DiscountCodeValidator(
                "ABC!@#$%^&", 0, 0, "Valid description", 10.0f, new Hotel(), true);
        List<Integer> validationCodes = validator.validate();
        validationCodes.remove(Integer.valueOf(909));
        assertEquals(List.of(902), validationCodes);
    }

    @Test
    public void validate_InvalidValueType_ShouldAddErrorCode903() {
        DiscountCodeValidator validator = new DiscountCodeValidator(
                "ABCDE12345", 2, 0, "Valid description", 10.0f, new Hotel(), true);
        List<Integer> validationCodes = validator.validate();
        validationCodes.remove(Integer.valueOf(909));
        assertEquals(List.of(903), validationCodes);
    }

    @Test
    public void validate_InvalidType_ShouldAddErrorCode904() {
        DiscountCodeValidator validator = new DiscountCodeValidator(
                "ABCDE12345", 0, 2, "Valid description", 10.0f, new Hotel(), true);
        List<Integer> validationCodes = validator.validate();
        validationCodes.remove(Integer.valueOf(909));
        assertEquals(List.of(904), validationCodes);
    }

    @Test
    public void validate_InvalidShortDescription_ShouldAddErrorCode905() {
        DiscountCodeValidator validator = new DiscountCodeValidator(
                "ABCDE12345", 0, 0, "Desc", 10.0f, new Hotel(), true);
        List<Integer> validationCodes = validator.validate();
        validationCodes.remove(Integer.valueOf(909));
        assertEquals(List.of(905), validationCodes);
    }

    @Test
    public void validate_InvalidLongDescription_ShouldAddErrorCode906() {
        DiscountCodeValidator validator = new DiscountCodeValidator(
                "ABCDE12345", 0, 0, "a".repeat(51), 10.0f, new Hotel(), true);
        List<Integer> validationCodes = validator.validate();
        validationCodes.remove(Integer.valueOf(909));
        assertEquals(List.of(906), validationCodes);
    }

    @Test
    public void validate_InvalidPercentageValue_ShouldAddErrorCode907() {
        DiscountCodeValidator validator = new DiscountCodeValidator(
                "ABCDE12345", 1, 0, "Valid description", 110.0f, new Hotel(), true);
        List<Integer> validationCodes = validator.validate();
        validationCodes.remove(Integer.valueOf(909));
        assertEquals(List.of(907), validationCodes);
    }

    @Test
    public void validate_InvalidNegativeValue_ShouldAddErrorCode908() {
        DiscountCodeValidator validator = new DiscountCodeValidator(
                "ABCDE12345", 0, 0, "Valid description", -5.0f, new Hotel(), true);
        List<Integer> validationCodes = validator.validate();
        validationCodes.remove(Integer.valueOf(909));
        assertEquals(List.of(908), validationCodes);
    }
}
