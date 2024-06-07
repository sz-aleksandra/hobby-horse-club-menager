package pap.db.entities;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id", nullable = false)
    private int clientId;

    @Basic
    @Column(name = "username", nullable = false)
    private String username;

    @Basic
    @Column(name = "password", nullable = false)
    private String password;

    @Basic
    @Column(name = "name", nullable = false)
    private String name;

    @Basic
    @Column(name = "surname", nullable = false)
    private String surname;

    @Basic
    @Column(name = "email", nullable = false)
    private String email;

    @Basic
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Basic
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Basic
    @Column(name = "nationality", nullable = false)
    private String nationality;

    @Basic
    @Column(name = "gender", nullable = false)
    private String gender;

    @Basic
    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany
    @JoinColumn(name = "client_id")
    private List<PaymentMethod> paymentMethods;

    @OneToMany
    @JoinColumn(name = "client_id")
    private List<Rating> ratings;

    @OneToMany
    @JoinColumn(name = "client_id")
    private List<FavouriteHotel> favouriteHotels;

    @OneToMany
    @JoinColumn(name = "client_id")
    private List<FavouriteOffer> favouriteOffers;

    @OneToMany
    @JoinColumn(name = "client_id")
    private List<Reservation> reservations;
}
