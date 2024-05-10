package vidar.websystem.restController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import formbean.SalesOrderFilterConditionForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vidar.websystem.constants.PathConstants;
import vidar.websystem.domain.DatatablesView;
import vidar.websystem.domain.ReturnSlip;
import vidar.websystem.domain.User;
import vidar.websystem.dto.request.ReturnSlipRequest;
import vidar.websystem.service.ReturnService;
import vidar.websystem.service.UserService;

/**
 * @author yishi.xing
 * create datetime 5/9/2024 11:20 AM
 * description
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(PathConstants.RETURN)
public class ReturnRestController {

    private final UserService userService;
    private final ReturnService returnService;

    @PostMapping(value = "/create")
    public ResponseEntity<?> createReturnSlip(@RequestBody ReturnSlipRequest returnSlipRequest) {
        User user = userService.getAuthenticatedUser();
        return returnService.addReturnSlip(user, returnSlipRequest);
    }

    @GetMapping(value = "/getFilteredReturnSlips")
    @ResponseBody
    public String getFilteredReturnSlips(SalesOrderFilterConditionForm salesOrderFilterConditionForm) {
        DatatablesView<ReturnSlip> returnSlipView = returnService.getFilteredReturnSlips(salesOrderFilterConditionForm);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(returnSlipView);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping(value = "/getSalesOrderItemsById")
    public ResponseEntity<?> getSalesOrderItemsById(@RequestParam("returnSlipId") Long returnSlipId) {
        return ResponseEntity.ok().body(returnService.getSalesOrderReturnItemsByReturnSlipId(returnSlipId));
    }
}
