package vidar.websystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vidar.websystem.domain.InventoryEvent;

public interface InventoryEventRepository extends JpaRepository<InventoryEvent, Long> {
    
}
