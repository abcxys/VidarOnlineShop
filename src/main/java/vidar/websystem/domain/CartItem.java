package vidar.websystem.domain;

import lombok.Data;

import java.math.BigDecimal;

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
	public CartItem(HardwoodFloor floor, BigDecimal quantity) {
		this.floor = floor;
		this.quantity = quantity;
	}

	private HardwoodFloor floor;
	
	private BigDecimal quantity;
}
