package pap.db.entities;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id", nullable = false)
    private int reservationId;

    @Basic
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Basic
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Basic
    @Column(name = "description", nullable = false)
    private String description;

    @Basic
    @Column(name = "paid_amount", nullable = false)
    private float paidAmount;

    @Basic
    @Column(name = "status", nullable = false)
    private String status;

    @Basic
    @Column(name = "is_paid", nullable = false)
    private boolean isPaid;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "offer_id")
    private Offer offer;

    @OneToMany
    @JoinColumn(name = "reservation_id")
    private List<Penalty> penalties;
}
