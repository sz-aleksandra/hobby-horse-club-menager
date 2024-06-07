package pap.db.entities;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "offers")
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "offer_id", nullable = false)
    private int offerId;

    @Basic
    @Column(name = "room_type", nullable = false)
    private String roomType;

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
    @Column(name = "bathroom_no", nullable = false)
    private int bathroomNumber;

    @Basic
    @Column(name = "room_no", nullable = false)
    private int roomNumber;

    @Basic
    @Column(name = "bed_no", nullable = false)
    private int bedNumber;

    @Basic
    @Column(name = "has_kitchen", nullable = false)
    private boolean hasKitchen;

    @Basic
    @Column(name = "pet_friendly", nullable = false)
    private boolean petFriendly;

    @Basic
    @Column(name = "has_wifi", nullable = false)
    private boolean hasWifi;

    @Basic
    @Column(name = "smoking_allowed", nullable = false)
    private boolean smokingAllowed;

    @Basic
    @Column(name = "has_parking", nullable = false)
    private boolean hasParking;

    @Basic
    @Column(name = "price", nullable = false)
    private float price;

    @Basic
    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @Basic
    @Column(name = "image", nullable = false)
    private byte[] imageData;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @OneToMany
    @JoinColumn(name = "offer_id")
    private List<FavouriteOffer> favouriteOffers;

    @OneToMany
    @JoinColumn(name = "offer_id")
    private List<Rating> ratings;

    @OneToMany
    @JoinColumn(name = "offer_id")
    private List<Reservation> reservations;
}
