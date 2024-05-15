package vidar.websystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vidar.websystem.domain.ShippingMethod;

/**
 * @author yishi.xing
 * create datetime 5/15/2024 3:45 PM
 * description
 */
public interface ShippingMethodRepository extends JpaRepository<ShippingMethod, Long> {
}
