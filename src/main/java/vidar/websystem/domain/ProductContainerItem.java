package vidar.websystem.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yishi.xing
 * create datetime 4/12/2024 9:53 AM
 * description
 */
@Entity
@Table(name = "container_floors")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class ProductContainerItem {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "container_id", nullable = false)
    private Long containerId;

    @Column(name = "floor_id", nullable = false)
    private Long productId;

    @Column(name = "skid", nullable = false)
    private Integer skid;

    @Column(name = "quantity", nullable = false)
    private BigDecimal box;

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
