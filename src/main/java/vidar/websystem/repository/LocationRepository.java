package vidar.websystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vidar.websystem.domain.Location;
import org.springframework.data.jpa.repository.Query;

/**
 * @author yishi.xing
 * create datetime 4/9/2024 10:14 AM
 * description
 */

public interface LocationRepository extends JpaRepository<Location, Long> {
    boolean existsByBayAndWarehouseId(String bay, Long warehouseId);

    @Query(nativeQuery = true, value = "SELECT loc.id FROM locations loc " +
            "WHERE loc.bay = :bay AND loc.warehouse_id = :warehouseId")
    Long findIdByBayAndWarehouseId(String bay, Long warehouseId);
}
