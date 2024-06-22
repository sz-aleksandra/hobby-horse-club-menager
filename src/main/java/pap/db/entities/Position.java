package pap.db.entities;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "positions")
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "position_id", nullable = false)
    private int positionId;

    @Basic
    @Column(name = "name", nullable = false)
    private String name;

    @Basic
    @Column(name = "salary_min", nullable = false)
    private double salaryMin;

    @Basic
    @Column(name = "salary_max", nullable = false)
    private double salaryMax;

    @ManyToOne
    @JoinColumn(name = "licence_id", nullable = false)
    private Licence licence;

    @ManyToOne
    @JoinColumn(name = "coaching_licence_id")
    private Licence coachingLicence;

    @Basic
    @Column(name = "speciality", nullable = false)
    private String speciality;

    @OneToMany(mappedBy = "position")
    private List<Employee> employees;
}
