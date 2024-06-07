package pap.logic;

import java.util.ArrayList;
import java.util.List;
import pap.db.entities.Rating;

public class RatingMean {
    public static double calculateMean(List<Rating> ratings) {
        if (ratings == null || ratings.isEmpty()) {
            return 0;
        }
        List<Integer> numRatings = new ArrayList<Integer>();
        for (Rating rating: ratings){
            numRatings.add(rating.getRating());
        }
        return numRatings.stream().mapToDouble(Integer::doubleValue).average().orElse(0.0);
    }
}
