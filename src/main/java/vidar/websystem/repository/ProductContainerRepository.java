package vidar.websystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vidar.websystem.domain.ProductContainerItem;

/**
 * @author yishi.xing
 * create datetime 4/13/2024 12:33 PM
 * description
 */
public interface ProductContainerRepository extends JpaRepository<ProductContainerItem, Long> {
}
