package vidar.websystem.dto.request;

import lombok.Data;
import lombok.ToString;

/**
 * @author yishi.xing
 * create datetime 4/8/2024 4:33 PM
 * description
 */
@Data
@ToString
public class InventoryItemRequest {
    private Long id;

    private Long productId;

    private String location;

    private Integer quantity;
}
