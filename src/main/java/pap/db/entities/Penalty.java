package pap.db.entities;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "penalties")
public class Penalty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "penalty_id", nullable = false)
    private int penaltyId;

    @Basic
    @Column(name = "reason", nullable = false)
    private String reason;

    @Basic
    @Column(name = "amount", nullable = false)
    private int amount;

    @Basic
    @Column(name = "is_paid", nullable = false)
    private boolean isPaid;

    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;
}
