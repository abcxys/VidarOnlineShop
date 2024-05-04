package vidar.websystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vidar.websystem.domain.PackingSlipItem;

/**
 * @author yishi.xing
 * create datetime 5/3/2024 3:57 PM
 * description
 */
public interface PackingSlipItemRepository extends JpaRepository<PackingSlipItem, Long> {
}
