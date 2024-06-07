package pap.logic.filtration.prepareStatements;

import pap.logic.filtration.properties.OwnerProperties;

public class PrepareOwnerFilter {
    private OwnerProperties ownerInfo;

    public PrepareOwnerFilter(OwnerProperties ownerInfo) {
        this.ownerInfo = ownerInfo;
    }

    public String prepareQuery() {
        StringBuilder baseQuery = new StringBuilder("SELECT offers.* FROM offers JOIN hotels ON (offers.hotel_id = hotels.hotel_id) JOIN owners ON (hotels.owner_id = owners.owner_id) WHERE offers.is_active = true");

        if (ownerInfo.getCompanyName() != null && !ownerInfo.getCompanyName().isEmpty()) {
            baseQuery.append(" AND LOWER(owners.company_name) like LOWER('").append(ownerInfo.getCompanyName()).append("%')");
        }

        return baseQuery.toString();
    }
}
