package pap.db.entities;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "owners")
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "owner_id", nullable = false)
    private int ownerId;

    @Basic
    @Column(name = "username", nullable = false)
    private String username;

    @Basic
    @Column(name = "password", nullable = false)
    private String password;

    @Basic
    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Basic
    @Column(name = "email", nullable = false)
    private String email;

    @Basic
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Basic
    @Column(name = "nip", nullable = false)
    private String nip;

    @Basic
    @Column(name = "is_verified", nullable = false)
    private boolean isVerified;

    @Basic
    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany
    @JoinColumn(name = "owner_id")
    private List<Hotel> hotels;
}
