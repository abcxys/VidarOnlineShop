package com.gmail.merikbest2015.ecommerce.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

/**
 * @author yishi.xing
 * @created Feb 13, 2024 - 10:03:06 PM
 */
@Data
@Entity
@Table(name = "orders_hardwoodfloors")
public class ItemOrder {
	@Id
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "order_id")
	Order order;
	
	@ManyToOne
	@JoinColumn(name = "hardwoodfloors_id")
	HardwoodFloor floor;
	
	float quantity;
}
