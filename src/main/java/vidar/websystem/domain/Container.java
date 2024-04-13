package vidar.websystem.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

/**
 * @author yishi.xing
 * create datetime 4/12/2024 4:09 PM
 * description
 */
@Entity
@Table(name = "container")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Container {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Column(name = "target_warehouse_id", nullable = false)
    private Long targetWarehouseId;

    @Column(name = "container_status_id", nullable = false)
    private Long containerStatusId;

    @Column(name = "container_number", nullable = false)
    private String containerNumber;

    @Column(name = "billoflanding_number", nullable = false)
    private String billOfLandingNumber;

    @Column(name = "shipping_company", nullable = false)
    private String shippingCompany;

    @Column(name = "freight_forwarder", nullable = false)
    private String freightForwarder;

    @Column(name = "estimated_arrival_date")
    private Date estimatedArrivalDate;

    @Column(name = "arrival_date")
    private Date arrivalDate;

    @Column(name = "port_date")
    private Date portDate;

    @Column(name = "on_rail_date")
    private Date onRailDate;

    @Column(name = "port_name")
    private String portName;

    @Column(name = "description")
    private String description;
}
