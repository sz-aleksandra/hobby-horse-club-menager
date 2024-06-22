package pap.db.entities;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "riders")
public class Rider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rider_id", nullable = false)
    private int riderId;

    @Basic
    @Column(name = "parent_consent", nullable = false)
    private String parentConsent;

    @Basic
    @Column(name = "licence", nullable = false)
    private String licence;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @ManyToOne
    @JoinColumn(name = "horse_id", nullable = false)
    private Horse horse;
}
