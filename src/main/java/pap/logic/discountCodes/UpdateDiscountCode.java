package pap.logic.discountCodes;

import pap.db.dao.DiscountDAO;
import pap.db.entities.Discount;

public class UpdateDiscountCode {
    private final Discount discountCode;
    public UpdateDiscountCode(Discount discount) {
        this.discountCode = discount;
    }

    public void deactivate() {
        discountCode.setActive(false);
        new DiscountDAO().update(discountCode);
    }

    public void activate() {
        discountCode.setActive(true);
        new DiscountDAO().update(discountCode);
    }
}
