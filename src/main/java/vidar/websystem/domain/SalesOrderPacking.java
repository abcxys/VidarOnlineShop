package vidar.websystem.domain;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yishi.xing
 * create datetime 4/30/2024 4:11 PM
 * description
 */
@Entity
@Table(name = "sales_orders_packing")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class SalesOrderPacking {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "so_product_id", nullable = false)
    private Long soProductId;

    @Column(name = "packing_slip_id", nullable = false)
    private Long packingSlipId;

    @Column(name = "quantity")
    private BigDecimal quantity;

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
