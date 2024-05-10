package vidar.websystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vidar.websystem.domain.ReturnItem;

import java.util.List;

/**
 * @author yishi.xing
 * create datetime 5/10/2024 9:38 AM
 * description
 */
public interface ReturnItemRepository extends JpaRepository<ReturnItem, Long> {

    List<ReturnItem> findByReturnSlipId(Long returnSlipId);
}
