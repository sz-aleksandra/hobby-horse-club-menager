package pap.logic.discountCodes;

import pap.db.dao.DiscountDAO;
import pap.db.entities.Discount;
import pap.db.entities.Hotel;

public class AddNewDiscountCode {
    private final Discount discount;

    public AddNewDiscountCode(String code, Integer valueType, Integer type, String description,
                              Float value, Hotel hotel, boolean isActive) {
        this.discount = new Discount();
        this.discount.setCode(code);
        this.discount.setValueType(valueType);
        this.discount.setType(type);
        this.discount.setDescription(description);
        this.discount.setValue(value);
        this.discount.setHotel(hotel);
        this.discount.setActive(isActive);
    }

    public void insertIntoDatabase() {
        new DiscountDAO().create(discount);
    }
}
