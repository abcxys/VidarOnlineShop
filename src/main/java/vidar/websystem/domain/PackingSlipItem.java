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
 * create datetime 5/3/2024 11:18 AM
 * description
 */
@Entity
@Table(name = "sales_orders_packing")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class PackingSlipItem {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "so_product_id", nullable = false)
    private Long soProductId;

    @Column(name = "packing_slip_id")
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
