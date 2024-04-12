package vidar.websystem.dto.request;

import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import vidar.websystem.constants.ErrorMessage;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @author yishi.xing
 * create datetime 4/11/2024 3:01 PM
 * description
 */
@Data
@ToString
public class ContainerRequest {
    private Long id;

    @NotBlank(message = ErrorMessage.FILL_IN_THE_INPUT_FIELD)
    @Length(max = 255)
    private String containerNumber;

    @NotBlank(message = ErrorMessage.FILL_IN_THE_INPUT_FIELD)
    @Length(max = 255)
    private String billOfLandingNumber;

    @NotBlank(message = ErrorMessage.FILL_IN_THE_INPUT_FIELD)
    @Length(max = 255)
    private String shippingCompany;

    @NotBlank(message = ErrorMessage.FILL_IN_THE_INPUT_FIELD)
    @Length(max = 255)
    private String freightForwarder;

    @NotBlank(message = ErrorMessage.FILL_IN_THE_INPUT_FIELD)
    @Length(max = 255)
    private String portName;

    private Date eta;
}
