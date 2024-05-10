package vidar.websystem.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import vidar.websystem.json.PackingSlipSerializer;
import vidar.websystem.json.ReturnStatusSerializer;

import javax.persistence.*;
import java.util.Date;

/**
 * @author yishi.xing
 * create datetime 5/9/2024 3:27 PM
 * description
 */
@Entity
@Table(name = "return_slips")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class ReturnSlip {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonSerialize(using = PackingSlipSerializer.class)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "packing_slip_id")
    private PackingSlip packingSlip;

    @JsonSerialize(using = ReturnStatusSerializer.class)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "return_status_id")
    private ReturnStatus returnStatus;

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
