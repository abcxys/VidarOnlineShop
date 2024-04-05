package formbean;

import lombok.Data;

/**
 * @author yishi.xing
 * @created Feb 26, 2024 - 11:05:50 PM
 */
@Data
public class InventoryFilterConditionForm {
	private Integer draw;
	private Integer startPos;
	private Integer pageSize;
	private String orderName;
	private String orderType;
	
	private String colour;
	private String width;
	private String species;
	private String grade;
	private String batch;
}
