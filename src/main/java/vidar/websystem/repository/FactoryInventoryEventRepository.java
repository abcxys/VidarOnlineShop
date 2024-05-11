package vidar.websystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vidar.websystem.domain.FactoryInventoryEvent;

/**
 * @author yishi.xing
 * create datetime 5/11/2024 2:11 PM
 * description
 */
public interface FactoryInventoryEventRepository extends JpaRepository<FactoryInventoryEvent, Long> {
}
