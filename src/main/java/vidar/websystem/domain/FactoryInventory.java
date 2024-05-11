package vidar.websystem.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;

import lombok.Data;

/**
 * @author yishi.xing
 * created Feb 20, 2024 - 11:17:38 PM
 */
@Data
@Entity
@Table(name = "factory_inventory")
public class FactoryInventory {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "location_id_seq")
    @SequenceGenerator(name = "location_id_seq", sequenceName = "location_id_seq", initialValue = 7, allocationSize = 1)
    private Long id;

    @Column(name = "floor_id", nullable = false)
    private Long floorId;

    @Column(name = "location_id", nullable = false)
    private Long locationId;

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

    @Column(name = "current_quantity", precision = 5, scale = 1, nullable = false)
    private BigDecimal currentQuantity;

    @Column(name = "initial_quantity", precision = 5, scale = 1, nullable = false)
    private BigDecimal initialQuantity;
}
