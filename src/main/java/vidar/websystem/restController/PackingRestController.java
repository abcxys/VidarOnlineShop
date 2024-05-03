package vidar.websystem.restController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vidar.websystem.constants.PathConstants;
import vidar.websystem.domain.User;
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
}
