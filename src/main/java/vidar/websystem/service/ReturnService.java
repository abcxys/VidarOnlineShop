package vidar.websystem.service;

import formbean.SalesOrderFilterConditionForm;
import org.springframework.http.ResponseEntity;
import vidar.websystem.domain.*;
import vidar.websystem.dto.request.ReturnSlipRequest;

import java.util.List;

/**
 * @author yishi.xing
 * create datetime 5/10/2024 8:42 AM
 * description
 */
public interface ReturnService {
    List<ReturnStatus> getReturnStatusDict();

    List<SalesOrderItem> getSalesOrderReturnItemsByReturnSlipId(Long returnSlipId);

    ResponseEntity<?> addReturnSlip(User user, ReturnSlipRequest returnSlipRequest);

    DatatablesView<ReturnSlip> getFilteredReturnSlips(SalesOrderFilterConditionForm salesOrderFilterConditionForm);
}
