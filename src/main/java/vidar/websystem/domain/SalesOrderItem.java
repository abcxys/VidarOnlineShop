package vidar.websystem.domain;

import java.math.BigDecimal;

/**
 * @author yishi.xing
 * create datetime 4/18/2024 12:06 PM
 * description
 */
public class SalesOrderItem {

    public SalesOrderItem(FloorColorSize floorColorSize, BigDecimal quantity) {
        this.floorColorSize = floorColorSize;
        this.quantity = quantity;
    }

    private FloorColorSize floorColorSize;

    private BigDecimal quantity;
}
