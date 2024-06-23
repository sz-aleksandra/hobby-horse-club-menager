package pap.db.entities;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Setter
@Getter
@Table(name = "classes")
public class Class {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_id", nullable = false)
    private int classId;

    @Basic
    @Column(name = "type", nullable = false)
    private String type;

    @Basic
    @Temporal(TemporalType.DATE)
    @Column(name = "date", nullable = false)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "trainer_id", nullable = false)
    private Member trainer;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @ManyToOne
    @JoinColumn(name = "stable_id", nullable = false)
    private Stable stable;
}
