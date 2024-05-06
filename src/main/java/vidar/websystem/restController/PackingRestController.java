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
import vidar.websystem.domain.User;
import vidar.websystem.domain.PackingSlip;
import vidar.websystem.dto.request.PackingSlipRequest;
import vidar.websystem.service.PackingService;
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

    @PostMapping(value = "/create")
    public ResponseEntity<?> createPackingSlip(@RequestBody PackingSlipRequest packingSlipRequest) {
        User user = userService.getAuthenticatedUser();
        ResponseEntity<?> responseEntity = packingService.addPackingSlip(user, packingSlipRequest);
        return ResponseEntity.ok().body("success");
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
}
