package pap.db.entities;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "payment_methods")
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_method_id", nullable = false)
    private int paymentMethodId;

    @Basic
    @Column(name = "card_number", nullable = false)
    private String cardNumber;

    @Basic
    @Column(name = "cvv", nullable = false)
    private String cvv;

    @Basic
    @Column(name = "expiration_date", nullable = false)
    private String expirationDate;

    @Basic
    @Column(name = "card_holder", nullable = false)
    private String cardHolder;

    @Basic
    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
}
