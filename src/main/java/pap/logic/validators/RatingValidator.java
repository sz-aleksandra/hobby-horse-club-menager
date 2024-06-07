package pap.logic.validators;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RatingValidator {
    private static final int MIN_COMMENT_LENGTH = 10;
    private static final int MAX_COMMENT_LENGTH = 100;

    private final Integer rating;
    private final String comment;
    private final LocalDate date;

    public RatingValidator(Integer rating, String comment, LocalDate date) {
        this.rating = rating;
        this.comment = comment;
        this.date = date;
    }

    public List <Integer> validate() {
        List <Integer> codes = new ArrayList<>();
        validateRating(rating, codes);
        validateComment(comment, codes);
        validateDate(date, codes);
        return codes;
    }

    public static void validateRating(Integer rating, List <Integer> codes) {
        if (rating > 5 || rating < 1) codes.add(801);
    }

    public static void validateComment(String comment, List <Integer> codes) {
        if (comment.length() < MIN_COMMENT_LENGTH) codes.add(802);
        if (comment.length() > MAX_COMMENT_LENGTH) codes.add(803);
    }

    public static void validateDate(LocalDate date, List <Integer> codes) {
        if (date.isAfter(LocalDate.now())) codes.add(804);
    }
}
