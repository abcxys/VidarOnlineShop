package vidar.websystem.dto.request;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author yishi.xing
 * create datetime 5/9/2024 4:26 PM
 * description
 */
@Data
@ToString
public class ReturnSlipRequest {
    private Long id;

    private Long packingSlipId;

    private Long returnStatusId = 1L;

    private List<SalesOrderItemRequest> returnItems;

    private String description;
}
