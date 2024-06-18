package pap.db.entities;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id", nullable = false)
    private int employeeId;

    @ManyToOne
    @JoinColumn(name = "position_id", nullable = false)
    private Position position;

    @Basic
    @Column(name = "salary", nullable = false)
    private double salary;

    @Basic
    @Temporal(TemporalType.DATE)
    @Column(name = "date_employed", nullable = false)
    private Date dateEmployed;

    @OneToMany(mappedBy = "employee")
    private List<PositionHistory> positionHistories;
}
