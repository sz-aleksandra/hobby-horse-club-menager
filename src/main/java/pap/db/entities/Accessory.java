package pap.db.entities;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "accessories")
public class Accessory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accessory_id", nullable = false)
    private int accessoryId;

    @Basic
    @Column(name = "name", nullable = false)
    private String name;
}
