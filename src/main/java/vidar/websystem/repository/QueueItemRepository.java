package vidar.websystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vidar.websystem.domain.QueueItem;

/**
 * @author yishi.xing
 * create datetime 5/22/2024 11:46 PM
 * description
 */
public interface QueueItemRepository extends JpaRepository<QueueItem, Long> {
}
