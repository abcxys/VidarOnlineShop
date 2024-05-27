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
        // Set the default status of queueItem to be 1(waiting).
        queueItem.setStatus(1);
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

    /**
     * @return
     */
    @Override
    public List<QueueItem> getWaitingQueueItemsCreatedToday() {
        return queueItemRepository.findAllByStatusAndCreateTimeToday(1);
    }

    /**
     * @return
     */
    @Override
    public List<QueueItem> getPreparingQueueItemsCreatedToday() {
        return queueItemRepository.findAllByStatusAndCreateTimeToday(2);
    }

    /**
     * @return
     */
    @Override
    public List<QueueItem> getCompletedQueueItemsCreatedToday() {
        return queueItemRepository.findAllByStatusAndCreateTimeToday(3);
    }

    /**
     * @param user
     * @param packingSlipNo
     * @param status
     * @return
     */
    @Override
    public ResponseEntity<?> updateQueueItemStatus(User user, String packingSlipNo, Integer status) {
        QueueItem queueItem = queueItemRepository.findByPackingSlipNo(packingSlipNo);
        if(queueItem == null) {
            return ResponseEntity.notFound().build();
        }
        queueItem.setStatus(status);
        queueItem.setUpdateUserId(user.getId());
        queueItem.setUpdateTime(new Date());
        queueItemRepository.save(queueItem);
        return null;
    }
}
