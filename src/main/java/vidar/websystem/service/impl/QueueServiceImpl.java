package vidar.websystem.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import vidar.websystem.domain.QueueItem;
import vidar.websystem.domain.User;
import vidar.websystem.repository.QueueItemRepository;
import vidar.websystem.service.QueueService;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author yishi.xing
 * create datetime 5/23/2024 11:22 PM
 * description
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class QueueServiceImpl implements QueueService {

    private final QueueItemRepository queueItemRepository;
    /**
     * @param user Authenticated user
     * @param packingSlipNo String to form queueItem
     * @return Web ResponseEntity
     */
    @Override
    public ResponseEntity<?> addQueueItem(User user, String packingSlipNo) {
        QueueItem queueItem = new QueueItem();
        queueItem.setCreateUserId(user.getId());
        queueItem.setPackingSlipNo(packingSlipNo);
        queueItem.setCreateTime(new Date());
        queueItemRepository.save(queueItem);
        return null;
    }

    /**
     * @return List of QueueItem that is created today
     */
    @Override
    public List<QueueItem> getQueueItemsCreatedToday() {
        return queueItemRepository.findAllByCreateTimeToday();
    }
}
