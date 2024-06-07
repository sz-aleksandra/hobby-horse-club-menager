package pap.db.entities;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "hotels")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hotel_id", nullable = false)
    private int hotelId;

    @Basic
    @Column(name = "name", nullable = false)
    private String name;

    @Basic
    @Column(name = "add_date", nullable = false)
    private LocalDate addDate;

    @Basic
    @Column(name = "description", nullable = false)
    private String description;

    @Basic
    @Column(name = "email", nullable = false)
    private String email;

    @Basic
    @Column(name = "website", nullable = false)
    private String website;

    @Basic
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Basic
    @Column(name = "bank_account_number", nullable = false)
    private String bankAccountNumber;

    @Basic
    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @OneToMany
    @JoinColumn(name = "hotel_id")
    private List<Offer> offers;

    @OneToMany
    @JoinColumn(name = "hotel_id")
    private List<FavouriteHotel> favouriteHotels;

    @OneToMany
    @JoinColumn(name = "hotel_id")
    private List<Discount> discounts;
}
