package pap.db.entities;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Setter
@Getter
@Table(name = "positions_history")
public class PositionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id", nullable = false)
    private int historyId;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "position_id", nullable = false)
    private Position position;

    @Basic
    @Temporal(TemporalType.DATE)
    @Column(name = "date_start", nullable = false)
    private Date dateStart;

    @Basic
    @Temporal(TemporalType.DATE)
    @Column(name = "date_end")
    private Date dateEnd;
}
