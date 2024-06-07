package pap.logic.filtration.prepareStatements;

import pap.logic.filtration.properties.HotelProperties;

public class PrepareHotelFilter {
    private HotelProperties hotelInfo;

    public PrepareHotelFilter(HotelProperties hotelInfo) {
        this.hotelInfo = hotelInfo;
    }

    public String prepareQuery() {
        StringBuilder baseQuery = new StringBuilder("SELECT offers.* FROM offers JOIN hotels ON (offers.hotel_id = hotels.hotel_id) WHERE offers.is_active = true");

        if (hotelInfo.getName() != null && !hotelInfo.getName().isEmpty()) {
            baseQuery.append(" AND LOWER(hotels.name) like LOWER('").append(hotelInfo.getName()).append("%')");
        }

        return baseQuery.toString();
    }
}
