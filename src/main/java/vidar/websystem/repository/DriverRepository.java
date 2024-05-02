package vidar.websystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vidar.websystem.domain.Driver;

/**
 * @author yishi.xing
 * create datetime 5/2/2024 3:52 PM
 * description
 */
public interface DriverRepository extends JpaRepository<Driver, Long> {
}
