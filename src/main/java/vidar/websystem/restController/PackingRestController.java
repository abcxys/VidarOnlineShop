package vidar.websystem.restController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import formbean.SalesOrderFilterConditionForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vidar.websystem.constants.PathConstants;
import vidar.websystem.constants.SuccessMessage;
import vidar.websystem.domain.DatatablesView;
import vidar.websystem.domain.SalesOrderItem;
import vidar.websystem.domain.User;
import vidar.websystem.domain.PackingSlip;
import vidar.websystem.dto.request.PackingSlipRequest;
import vidar.websystem.service.PackingService;
import vidar.websystem.service.ProductService;
import vidar.websystem.service.UserService;

/**
 * @author yishi.xing
 * create datetime 5/3/2024 11:05 AM
 * description
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(PathConstants.PACKING)
public class PackingRestController {

    private final PackingService packingService;
    private final UserService userService;
    private final ProductService productService;

    @PostMapping(value = "/create")
    public ResponseEntity<?> createPackingSlip(@RequestBody PackingSlipRequest packingSlipRequest) {
        User user = userService.getAuthenticatedUser();
        return packingService.addPackingSlip(user, packingSlipRequest);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<?> updatePackingSlip(@RequestBody PackingSlipRequest packingSlipRequest) {
        User user = userService.getAuthenticatedUser();
        return packingService.updatePackingSlip(user, packingSlipRequest);
    }

    @GetMapping(value = "/getFilteredPackingSlips")
    @ResponseBody
    public String getFilteredPackingSlips(SalesOrderFilterConditionForm salesOrderFilterConditionForm) {
        DatatablesView<PackingSlip> packingSlipView = packingService.getFilteredPackingSlips(salesOrderFilterConditionForm);
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(packingSlipView);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping(value ="/getSalesOrderItemsTableById")
    @ResponseBody
    public String getSalesOrderItemsTableById(@RequestParam("packingSlipId") Long packingSlipId) {
        DatatablesView<SalesOrderItem> packingItemsView = packingService.getSalesOrderPackingItemsTableByPackingSlipId(packingSlipId);
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(packingItemsView);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping(value = "/getSalesOrderItemsById")
    public ResponseEntity<?> getSalesOrderItemsById(@RequestParam("packingSlipId") Long packingSlipId){
        return ResponseEntity.ok().body(packingService.getSalesOrderPackingItemsByPackingSlipId(packingSlipId));
    }

    @PutMapping(value = "/updatePackingStatus")
    public ResponseEntity<?> updatePackingSlipStatus(@RequestParam("id") Long id,
                                                     @RequestParam("statusId") Long statusId) {
        User user = userService.getAuthenticatedUser();
        PackingSlip packingSlip = packingService.getPackingSlipById(id);
        return ResponseEntity.ok(packingService.updatePackingSlipStatus(user, packingSlip, statusId));
    }
}
