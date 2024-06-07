package pap.logic.filtration.prepareStatements;

import pap.logic.filtration.properties.OfferProperties;

public class PrepareOfferFilter {
    private OfferProperties offerInfo;

    public PrepareOfferFilter(OfferProperties offerInfo) {
        this.offerInfo = offerInfo;
    }

    public String prepareQuery() {
        StringBuilder baseQuery = new StringBuilder("SELECT * FROM offers WHERE offers.is_active = true");

        if (offerInfo.getRoomType() != null && !offerInfo.getRoomType().isEmpty()) {
            baseQuery.append(" AND LOWER(offers.room_type) like LOWER('").append(offerInfo.getRoomType()).append("%')");
        }

        if (offerInfo.getName() != null && !offerInfo.getName().isEmpty()) {
            baseQuery.append(" AND LOWER(offers.name) like LOWER('").append(offerInfo.getName()).append("%')");
        }

        if (offerInfo.getBathroomNoLowerBound() != null) {
            baseQuery.append(" AND offers.bathroom_no >= '").append(offerInfo.getBathroomNoLowerBound()).append("'");
        }

        if (offerInfo.getBathroomNoUpperBound() != null) {
            baseQuery.append(" AND offers.bathroom_no <= '").append(offerInfo.getBathroomNoUpperBound()).append("'");
        }

        if (offerInfo.getRoomNoLowerBound() != null) {
            baseQuery.append(" AND offers.room_no >= '").append(offerInfo.getRoomNoLowerBound()).append("'");
        }

        if (offerInfo.getRoomNoUpperBound() != null) {
            baseQuery.append(" AND offers.room_no <= '").append(offerInfo.getRoomNoUpperBound()).append("'");
        }

        if (offerInfo.getBedNoLowerBound() != null) {
            baseQuery.append(" AND offers.bed_no >= '").append(offerInfo.getBedNoLowerBound()).append("'");
        }

        if (offerInfo.getBedNoUpperBound() != null) {
            baseQuery.append(" AND offers.bed_no <= '").append(offerInfo.getBedNoUpperBound()).append("'");
        }

        if (offerInfo.getHasKitchen() != null) {
            baseQuery.append(" AND offers.has_kitchen = '").append(offerInfo.getHasKitchen()).append("'");
        }

        if (offerInfo.getPetFriendly() != null) {
            baseQuery.append(" AND offers.pet_friendly = '").append(offerInfo.getPetFriendly()).append("'");
        }

        if (offerInfo.getHasWifi() != null) {
            baseQuery.append(" AND offers.has_wifi = '").append(offerInfo.getHasWifi()).append("'");
        }

        if (offerInfo.getSmokingAllowed() != null) {
            baseQuery.append(" AND offers.smoking_allowed = '").append(offerInfo.getSmokingAllowed()).append("'");
        }

        if (offerInfo.getHasParking() != null) {
            baseQuery.append(" AND offers.has_parking = '").append(offerInfo.getHasParking()).append("'");
        }

        if (offerInfo.getPriceLowerBound() != null) {
            baseQuery.append(" AND offers.price >= '").append(offerInfo.getPriceLowerBound()).append("'");
        }

        if (offerInfo.getPriceUpperBound() != null) {
            baseQuery.append(" AND offers.price <= '").append(offerInfo.getPriceUpperBound()).append("'");
        }
        return baseQuery.toString();
    }
}
