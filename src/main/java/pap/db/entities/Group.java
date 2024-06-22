package pap.db.entities;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "groups")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id", nullable = false)
    private int groupId;

    @Basic
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "class_id", nullable = false)
    private Class class;

    @OneToMany(mappedBy = "group")
    private List<Rider> riders;

    @OneToMany(mappedBy = "group")
    private List<Class> classes;
}
