package vidar.websystem.domain;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yishi.xing
 * @created Feb 13, 2024 - 10:03:06 PM
 */
@Entity
@Table(name = "sales_orders_products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class SalesOrderProduct {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/*
	@ManyToOne
	@JoinColumn(name = "so_id", nullable = false)
	SalesOrder salesOrder;

	@ManyToOne
	@JoinColumn(name = "hardwoodfloor_id", nullable = false)
	HardwoodFloor hardwoodFloor;
	*/

	@Column(name = "product_id", nullable = false)
	private Long hardwoodfloorId;

	@Column(name = "so_id", nullable = false)
	private Long salesOrderId;

	@Column(name = "quantity_ordered", nullable = false)
	private BigDecimal quantityOrdered;

	@Column(name = "quantity_picked_up")
	private BigDecimal quantityPickedUp = BigDecimal.ZERO;

	@Column(name = "active")
	private boolean active = true;

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
