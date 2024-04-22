package vidar.websystem.domain;

import lombok.Data;

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

    @Column(name = "total_price", nullable = false)
    private Double totalPrice;

    @Column(name = "date", columnDefinition = "timestamp default current_timestamp")
    private Date date = new Date();

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "post_index", nullable = false)
    private Integer postIndex;

	@ManyToMany(fetch = FetchType.LAZY, 
			cascade = { 
					CascadeType.PERSIST, 
					CascadeType.MERGE })
	@JoinTable(name = "sales_orders_hardwoodfloors", joinColumns = { @JoinColumn(name = "sales_order_id") }, inverseJoinColumns = {
			@JoinColumn(name = "hardwoodfloors_id") })
	private Set<HardwoodFloor> hardwoodfloors = new HashSet<>();
//	
//	@OneToMany(mappedBy = "salesOrder")
//    private Set<FloorOrder> floorOrders = new HashSet<FloorOrder>();

    @ManyToOne
    private User user;
}
