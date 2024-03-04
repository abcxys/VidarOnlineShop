package com.gmail.merikbest2015.ecommerce.domain;

import lombok.Data;

/**
 * @author yishi.xing
 * @created Mar 3, 2024 - 6:45:10 PM
 */
@Data
public class CartItem {
	
	/**
	 * @param floor
	 * @param quantity
	 */
	public CartItem(HardwoodFloor floor, Long quantity) {
		this.floor = floor;
		this.quantity = quantity;
	}

	private HardwoodFloor floor;
	
	private Long quantity;
}
