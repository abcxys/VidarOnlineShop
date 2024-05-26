package vidar.websystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vidar.websystem.domain.QueueItem;

import java.util.List;

/**
 * @author yishi.xing
 * create datetime 5/22/2024 11:46 PM
 * description
 */
public interface QueueItemRepository extends JpaRepository<QueueItem, Long> {

    @Query("SELECT i FROM QueueItem i WHERE FUNCTION('DATE', i.createTime) = FUNCTION('DATE', CURRENT_DATE)")
    List<QueueItem> findAllByCreateTimeToday();
}
