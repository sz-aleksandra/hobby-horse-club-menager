package pap.logic.validators;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OfferValidator {
    private static final int MIN_ROOMTYPE_LENGTH = 2;
    private static final int MAX_ROOMTYPE_LENGTH = 15;
    private static final int MIN_NAME_LENGTH = 2;
    private static final int MAX_NAME_LENGTH = 30;
    private static final int MIN_DESC_LENGTH = 10;
    private static final int MAX_DESC_LENGTH = 100;

    private final String roomType;
    private final String name;
    private final LocalDate addDate;
    private final String description;
    private final Integer bathroomNo;
    private final Integer roomNo;
    private final Integer bedNo;
    private final Boolean hasKitchen;
    private final Boolean petFriendly;
    private final Float price;
    private final Boolean isActive;
    public OfferValidator(String roomType, String name, LocalDate addDate, String description,
                          Integer bathroomNo, Integer roomNo, Integer bedNo, boolean hasKitchen, boolean petFriendly,
                          Float price, boolean isActive) {
        this.roomType = roomType;
        this.name = name;
        this.addDate = addDate;
        this.description = description;
        this.bathroomNo = bathroomNo;
        this.roomNo = roomNo;
        this.bedNo = bedNo;
        this.hasKitchen = hasKitchen;
        this.petFriendly = petFriendly;
        this.price = price;
        this.isActive = isActive;
    }

    public List<Integer> validate() {
        List <Integer> codes = new ArrayList<>();
        validateRoomType(roomType, codes);
        validateName(name, codes);
        validateAddDate(addDate, codes);
        validateDescription(description, codes);
        validateNumber(bathroomNo, codes, 608);
        validateNumber(roomNo, codes, 609);
        validateNumber(bedNo, codes, 610);
        validatePrice(price, codes);
        return codes;
    }

    public static void validateRoomType(String roomType, List <Integer> codes) {
        if (roomType.length() < MIN_ROOMTYPE_LENGTH) codes.add(601);
        if (roomType.length() > MAX_ROOMTYPE_LENGTH) codes.add(602);
    }

    public static void validateName(String name, List <Integer> codes) {
        if (name.length() < MIN_NAME_LENGTH) codes.add(603);
        if (name.length() > MAX_NAME_LENGTH) codes.add(604);
    }

    public static void validateAddDate(LocalDate addDate, List <Integer> codes) {
        if (addDate.isAfter(LocalDate.now())) codes.add(605);
    }

    public static void validateDescription(String description, List <Integer> codes) {
        if (description.length() < MIN_DESC_LENGTH) codes.add(606);
        if (description.length() > MAX_DESC_LENGTH) codes.add(607);
    }

    public static void validateNumber(Integer number, List <Integer> codes, Integer errorCode) {
        if (number < 0) codes.add(errorCode);
    }

    public static void validatePrice(Float price, List <Integer> codes) {
        if (price < 0) codes.add(611);
    }
}
