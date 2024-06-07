package pap.logic.discountCodes;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import pap.db.SessionFactoryMaker;
import pap.db.entities.Discount;
import pap.db.entities.Hotel;

public class AddDiscountCodeForAllHotels {
    SessionFactory factory = SessionFactoryMaker.getFactory();
    private final Discount discount;

    public AddDiscountCodeForAllHotels(String code, Integer valueType, String description,
                              Float value, Hotel hotel, boolean isActive) {
        this.discount = new Discount();
        this.discount.setCode(code);
        this.discount.setValueType(valueType);
        this.discount.setType(1);
        this.discount.setDescription(description);
        this.discount.setValue(value);
        this.discount.setHotel(hotel);
        this.discount.setActive(isActive);
    }

    public void insertIntoDatabase() {
        String query = "INSERT INTO discounts (code, value_type, type, description, value, hotel_id, is_active) " +
                "VALUES (:code, :valueType, :type, :description, :value, 0, true)";

        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createNativeQuery(query)
                    .setParameter("code", discount.getCode())
                    .setParameter("valueType", discount.getValueType())
                    .setParameter("type", discount.getType())
                    .setParameter("description", discount.getDescription())
                    .setParameter("value", discount.getValue())
                    .executeUpdate();
        }
    }
}
