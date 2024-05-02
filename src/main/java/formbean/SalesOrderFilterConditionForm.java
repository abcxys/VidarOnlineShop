package formbean;

import lombok.Data;

import java.util.Date;
import java.util.List;

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
    private String statusIdsString;
    private Date startDate;
    private Date endDate;
}
