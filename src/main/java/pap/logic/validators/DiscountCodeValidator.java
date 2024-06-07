package pap.logic.validators;

import jakarta.persistence.NoResultException;
import pap.db.dao.ClientDAO;
import pap.db.dao.DiscountDAO;
import pap.db.entities.Hotel;

import java.util.ArrayList;
import java.util.List;

public class DiscountCodeValidator {
    private final static int CODE_LENGTH = 10;
    private final static int MIN_DESC_LENGTH = 5;
    private final static int MAX_DESC_LENGTH = 50;
    private final String code;
    private final Integer valueType;
    private final Integer type;
    private final String description;
    private final Float value;
    private final Hotel hotel;
    private final boolean isActive;

    public DiscountCodeValidator(String code, Integer valueType, Integer type, String description,
                                 Float value, Hotel hotel, boolean isActive) {
        this.code = code;
        this.valueType = valueType;
        this.type = type;
        this.description = description;
        this.value = value;
        this.hotel = hotel;
        this.isActive = isActive;
    }

    public List<Integer> validate() {
        List <Integer> codes = new ArrayList<>();
        validateCode(code, codes);
        validateValueType(valueType, codes);
        validateType(type, codes);
        validateDescription(description, codes);
        validateValue(value, codes, valueType);
        return codes;
    }

    public static void validateCode(String code, List <Integer> codes) {
        if (code.length() != CODE_LENGTH) codes.add(901);
        if (!code.matches("[a-zA-Z0-9]+")) codes.add(902);
        try {
            new DiscountDAO().findByCodeWithNoActive(code);
            codes.add(909);
        } catch (NoResultException error) {
            // expected situation, do nothing
        }
        catch (Exception anotherError) {
            codes.add(1);
        }
    }

    public static void validateValueType(Integer valueType, List <Integer> codes) {
        if (valueType != 0 && valueType != 1) codes.add(903);
    }

    public static void validateType(Integer type, List <Integer> codes) {
        if (type != 0 && type != 1) codes.add(904);
    }

    public static void validateDescription(String description, List <Integer> codes) {
        if (description.length() < MIN_DESC_LENGTH) codes.add(905);
        if (description.length() > MAX_DESC_LENGTH) codes.add(906);
    }

    public static void validateValue(Float value, List <Integer> codes, Integer valueType) {
        if (valueType == 1) {
            if (value < 0 || value > 100) codes.add(907);
        } else {
            if (value < 0) codes.add(908);
        }
    }
}
