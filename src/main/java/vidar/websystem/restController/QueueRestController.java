package vidar.websystem.restController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vidar.websystem.constants.PathConstants;
import vidar.websystem.domain.User;
import vidar.websystem.service.QueueService;
import vidar.websystem.service.UserService;

/**
 * @author yishi.xing
 * create datetime 5/23/2024 11:16 PM
 * description
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(PathConstants.QUEUE)
public class QueueRestController {

    private final UserService userService;
    private final QueueService queueService;

    @PostMapping(value = "/addQueueItem")
    public ResponseEntity<?> addQueueItem(@RequestParam("packingSlipNo") String packingSlipNo){
        log.info("addQueueItem with packingSlipNo: " + packingSlipNo);
        User user = userService.getAuthenticatedUser();
        queueService.addQueueItem(user, packingSlipNo);
        return ResponseEntity.ok("success");
    }

    @PutMapping(value = "/updateQueueItemStatus")
    public ResponseEntity<?> updateQueueItemStatus(@RequestParam("packingSlipNo") String packingSlipNo, @RequestParam("status") Integer status){
        log.info("updateQueueItemStatus with packingSlipNo: " + packingSlipNo);
        User user = userService.getAuthenticatedUser();
        queueService.updateQueueItemStatus(user, packingSlipNo, status);
        return ResponseEntity.ok("success");
    }
}
