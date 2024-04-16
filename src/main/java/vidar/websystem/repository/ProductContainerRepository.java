package vidar.websystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vidar.websystem.domain.ProductContainerItem;

import java.util.List;

/**
 * @author yishi.xing
 * create datetime 4/13/2024 12:33 PM
 * description
 */
public interface ProductContainerRepository extends JpaRepository<ProductContainerItem, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM container_floors cf WHERE cf.container_id = :containerId")
    List<ProductContainerItem> findByContainerId(Long containerId);
}
