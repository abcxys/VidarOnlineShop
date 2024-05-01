package vidar.websystem.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import vidar.websystem.json.DealerSerializer;
import vidar.websystem.json.SalesOrderStatusSerializer;
import vidar.websystem.json.SalesRepSerializer;
import vidar.websystem.json.WarehouseSerializer;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;
import java.util.HashSet;

@Data
@Entity
@Table(name = "sales_orders")
public class SalesOrder {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_seq")
    @SequenceGenerator(name = "orders_seq", sequenceName = "orders_seq", initialValue = 6, allocationSize = 1)
    private Long id;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "date", columnDefinition = "timestamp default current_timestamp")
    private Date date = new Date();

    @Column(name = "date_wanted")
    private Date dateWanted;

    @Column(name = "email")
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "post_index")
    private Integer postIndex;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "SO_number", nullable = false)
    private String soNumber;

    @Column(name = "PO_number", nullable = false)
    private String poNumber;

    @JsonSerialize(using = DealerSerializer.class)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dealer_id")
    private Dealer dealer;

    @JsonSerialize(using = SalesRepSerializer.class)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sales_rep_id")
    private SalesRep salesRep;

    @JsonSerialize(using = WarehouseSerializer.class)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    @JsonSerialize(using = SalesOrderStatusSerializer.class)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    private SalesOrderStatus status = new SalesOrderStatus(1L, "created", "CR", null);

    @Column(name = "release_ok")
    private boolean releaseOk;

    @JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY,
			cascade = { 
					CascadeType.PERSIST, 
					CascadeType.MERGE })
	@JoinTable(name = "sales_orders_products", joinColumns = { @JoinColumn(name = "so_id") }, inverseJoinColumns = {
			@JoinColumn(name = "product_id") })
	private Set<HardwoodFloor> hardwoodfloors = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

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

}
