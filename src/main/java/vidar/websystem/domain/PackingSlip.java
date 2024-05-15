package vidar.websystem.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import vidar.websystem.json.DealerSerializer;
import vidar.websystem.json.DriverSerializer;
import vidar.websystem.json.PackingStatusSerializer;
import vidar.websystem.json.ShippingMethodSerializer;

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

    @JsonSerialize(using = DealerSerializer.class)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dealer_id")
    private Dealer dealer;

    @JsonSerialize(using = PackingStatusSerializer.class)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "packing_status_id")
    private PackingStatus packingStatus;

    @JsonSerialize(using = DriverSerializer.class)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id")
    private Driver driver;

    @JsonSerialize(using = ShippingMethodSerializer.class)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipping_method_id")
    private ShippingMethod shippingMethod;

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
