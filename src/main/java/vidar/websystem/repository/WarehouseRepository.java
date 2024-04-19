package vidar.websystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vidar.websystem.domain.Warehouse;

/**
 * @author yishi.xing
 * create datetime 4/19/2024 2:46 PM
 * description
 */
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
}
