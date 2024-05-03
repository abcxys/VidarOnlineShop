package vidar.websystem.dto.request;

import lombok.Data;
import lombok.ToString;
import vidar.websystem.domain.PackingSlipItem;

import java.util.List;

/**
 * @author yishi.xing
 * create datetime 5/3/2024 11:28 AM
 * description
 */
@Data
@ToString
public class PackingSlipRequest {
    private Long id;

    private Long driverId;

    private List<PackingSlipItem> packingSlipItems;

    private String description;
}
