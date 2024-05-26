package vidar.websystem.service;

import org.springframework.http.ResponseEntity;
import vidar.websystem.domain.QueueItem;
import vidar.websystem.domain.User;

import java.util.List;

/**
 * @author yishi.xing
 * create datetime 5/23/2024 11:19 PM
 * description
 */
public interface QueueService {

    ResponseEntity<?> addQueueItem(User user, String packingSlipNo);

    List<QueueItem> getQueueItemsCreatedToday();
}
