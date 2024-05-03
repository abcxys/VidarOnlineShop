package vidar.websystem.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

/**
 * @author yishi.xing
 * create datetime 4/30/2024 3:52 PM
 * description
 */
@Entity
@Table(name = "packing_slips")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class PackingSlip {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "packing_status_id", nullable = false)
    private Long packingStatusId = 1L;

    @Column(name = "driver_id", nullable = false)
    private Long driverId = 1L;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time", updatable = false)
    private Date createTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "create_user_id", updatable = false)
    private Long createUserId;

    @Column(name = "update_user_id")
    private Long updateUserId;

    @Column(name = "description")
    private String description;
}
