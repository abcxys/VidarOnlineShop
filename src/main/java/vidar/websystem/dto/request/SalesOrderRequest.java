package vidar.websystem.dto.request;

import lombok.ToString;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import vidar.websystem.constants.ErrorMessage;
import vidar.websystem.domain.SalesOrderItem;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

/**
 * @author yishi.xing
 * create datetime 4/22/2024 11:35 AM
 * description
 */
@Data
@ToString
public class SalesOrderRequest {
    private Long id;

    @NotBlank(message = ErrorMessage.FILL_IN_THE_INPUT_FIELD)
    @Length(max = 255)
    private String address;

    private Date date;

    private Date dateWanted;

    @NotBlank(message = ErrorMessage.FILL_IN_THE_INPUT_FIELD)
    @Length(max = 255)
    private String soNumber;

    @NotBlank(message = ErrorMessage.FILL_IN_THE_INPUT_FIELD)
    @Length(max = 255)
    private String poNumber;

    private Long dealerId;

    private Long salesRepId;

    private Long warehouseId;

    private List<SalesOrderItem> salesOrderItems;
}
