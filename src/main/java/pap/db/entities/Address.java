package pap.db.entities;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id", nullable = false)
    private int addressId;

    @Basic
    @Column(name = "country", nullable = false)
    private String country;

    @Basic
    @Column(name = "city", nullable = false)
    private String city;

    @Basic
    @Column(name = "street", nullable = false)
    private String street;

    @Basic
    @Column(name = "postal_code", nullable = false)
    private String postalCode;

    @Basic
    @Column(name = "street_no", nullable = false)
    private String streetNumber;

    @OneToMany
    @JoinColumn(name = "address_id")
    private List<Client> clients;

    @OneToMany
    @JoinColumn(name = "address_id")
    private List<Owner> owners;

    @OneToMany
    @JoinColumn(name = "address_id")
    private List<Hotel> hotels;
}
