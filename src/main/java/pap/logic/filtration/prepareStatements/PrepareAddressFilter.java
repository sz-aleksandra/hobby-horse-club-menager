package pap.logic.filtration.prepareStatements;

import pap.logic.filtration.properties.AddressProperties;

public class PrepareAddressFilter {
    private AddressProperties addressInfo;

    public PrepareAddressFilter(AddressProperties addressInfo) {
        this.addressInfo = addressInfo;
    }

    public String prepareQuery() {
        StringBuilder baseQuery = new StringBuilder("SELECT offers.* FROM offers JOIN hotels ON (offers.hotel_id = hotels.hotel_id) JOIN addresses ON (hotels.address_id = addresses.address_id) WHERE offers.is_active = true");

        if (addressInfo.getCity() != null && !addressInfo.getCity().isEmpty()) {
            baseQuery.append(" AND LOWER(addresses.city) like LOWER('").append(addressInfo.getCity()).append("%')");
        }

        if (addressInfo.getCountry() != null && !addressInfo.getCountry().isEmpty()) {
            baseQuery.append(" AND LOWER(addresses.country) like LOWER('").append(addressInfo.getCountry()).append("%')");
        }

        if (addressInfo.getStreet() != null && !addressInfo.getStreet().isEmpty()) {
            baseQuery.append(" AND LOWER(addresses.street) like LOWER('").append(addressInfo.getStreet()).append("%')");
        }

        if (addressInfo.getPostalCode() != null && !addressInfo.getPostalCode().isEmpty()) {
            baseQuery.append(" AND LOWER(addresses.postal_code) like LOWER('").append(addressInfo.getPostalCode()).append("%')");
        }

        if (addressInfo.getStreetNo() != null && !addressInfo.getStreetNo().isEmpty()) {
            baseQuery.append(" AND LOWER(addresses.street_no) like LOWER('").append(addressInfo.getStreetNo()).append("%')");
        }

        return baseQuery.toString();
    }
}
