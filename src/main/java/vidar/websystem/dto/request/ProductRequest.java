package vidar.websystem.dto.request;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.Data;
import lombok.ToString;
import vidar.websystem.constants.ErrorMessage;

/**
 * @author yishi.xing
 * @created Mar 25, 2024 - 10:08:00 AM
 */
@Data
@ToString
public class ProductRequest {
	private Long id;
	
	@NotNull(message = ErrorMessage.FILL_IN_THE_INPUT_FIELD)
	private Integer colorId;
	
	@NotNull(message = ErrorMessage.FILL_IN_THE_INPUT_FIELD)
	private Integer sizeId;
	
	@NotNull(message = ErrorMessage.FILL_IN_THE_INPUT_FIELD)
	private Integer speciesId;
	
	@NotNull(message = ErrorMessage.FILL_IN_THE_INPUT_FIELD)
	private Integer typeId;
	
	@NotNull(message = ErrorMessage.FILL_IN_THE_INPUT_FIELD)
	private Integer gradeId;
	
	@NotBlank(message = ErrorMessage.FILL_IN_THE_INPUT_FIELD)
	@Length(max = 255)
	private String batchNumber;
	
	@NotBlank(message = ErrorMessage.FILL_IN_THE_INPUT_FIELD)
	@Length(max = 255)
	private String wearThickness;
	
    @Min(value = 4, message = ErrorMessage.FILL_IN_THE_INPUT_FIELD)
    private Integer year;
    
    @Length(max = 255)
    private String finish;
    
    @Length(max = 255)
    private String country;
    
    @NotNull(message = ErrorMessage.FILL_IN_THE_INPUT_FIELD)
    private BigDecimal price;
}
