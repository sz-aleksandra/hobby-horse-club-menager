package pap.db.entities;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "horses")
public class Horse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "horse_id", nullable = false)
    private int horseId;

    @Basic
    @Column(name = "breed", nullable = false)
    private String breed;

    @Basic
    @Column(name = "height", nullable = false)
    private int height;

    @Basic
    @Column(name = "color", nullable = false)
    private String color;

    @Basic
    @Column(name = "eye_color", nullable = false)
    private String eyeColor;

    @Basic
    @Column(name = "age", nullable = false)
    private int age;

    @Basic
    @Column(name = "origin", nullable = false)
    private String origin;

    @ManyToOne
    @JoinColumn(name = "hairstyle_id", nullable = false)
    private Accessory hairstyle;

    @ManyToOne
    @JoinColumn(name = "accessories_id", nullable = false)
    private List<Accessory> accessories;

    @OneToMany(mappedBy = "horse_id")
    private List<Rider> riders;
}
