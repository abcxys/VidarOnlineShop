package formbean;

import lombok.Data;

import java.util.Date;

/**
 * @author yishi.xing
 * create datetime 4/26/2024 10:40 AM
 * description
 */
@Data
public class SalesOrderFilterConditionForm {
    private Integer draw;
    private Integer startPos;
    private Integer pageSize;
    private String orderName;
    private String orderType;

    private Long dealerId;
    private Date startDate;
    private Date endDate;
}
