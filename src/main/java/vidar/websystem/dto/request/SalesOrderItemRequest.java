package vidar.websystem.dto.request;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @author yishi.xing
 * create datetime 4/23/2024 3:30 PM
 * description
 */
@Data
@ToString
public class SalesOrderItemRequest {
    private Long productId;

    private BigDecimal quantity;
}
