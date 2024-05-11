package vidar.websystem.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
/**
 * @author yishi.xing
 * create datetime 5/11/2024 2:07 PM
 * description
 */
@Data
@Entity
@Table(name = "factory_inventory_event")
public class FactoryInventoryEvent {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "factory_inventory_id", nullable = false)
    private Long inventoryId;

    @Column(name = "inventory_event_type_id", nullable = false)
    private Long inventoryEventTypeId;

    @Column(name = "location_id", nullable = false)
    private Long locationId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time", updatable = false)
    private Date createTime;

    @Column(name = "create_user_id", updatable = false)
    private Long createUserId;

    @Column(name = "quantity", precision = 5, scale = 1, nullable = false)
    private BigDecimal quantity;

    @Column(name = "description")
    private String description;
}
