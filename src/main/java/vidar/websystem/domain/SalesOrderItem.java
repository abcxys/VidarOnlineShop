package vidar.websystem.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yishi.xing
 * create datetime 4/18/2024 12:06 PM
 * description
 */
@Data
@NoArgsConstructor
public class SalesOrderItem {

    public SalesOrderItem(FloorColorSize floorColorSize, BigDecimal quantity) {
        this.floorColorSize = floorColorSize;
        this.quantity = quantity;
    }

    // Id of corresponding SalesOrderProduct
    private Long id;

    private FloorColorSize floorColorSize;

    // Quantity ordered by sales order
    private BigDecimal quantity;

    // Quantity that already picked up by sales order
    private BigDecimal quantity_picked_up = BigDecimal.ZERO;

    // Quantity on hand
    private BigDecimal quantity_on_hand = BigDecimal.ZERO;

    // Sales order date
    private Date soDate;

    // Sales order number
    private String soNumber;
}
