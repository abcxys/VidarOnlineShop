package vidar.websystem.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

/**
 * @author yishi.xing
 * create datetime 5/22/2024 11:35 PM
 * description
 */
@Entity
@Table(name = "test_queuing")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class QueueItem {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "packing_slip_no")
    private String packingSlipNo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time", updatable = false, columnDefinition = "timestamp default current_timestamp")
    private Date createTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_time", columnDefinition = "timestamp default current_timestamp")
    private Date updateTime;

    @Column(name = "create_user_id", updatable = false)
    private Long createUserId;

    @Column(name = "update_user_id")
    private Long updateUserId;

    @Column(name = "prepare_start")
    private Date prepareStart;
}
