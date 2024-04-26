package vidar.websystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vidar.websystem.domain.SalesOrderStatus;

/**
 * @author yishi.xing
 * create datetime 4/26/2024 3:12 PM
 * description
 */
public interface SalesOrderStatusRepository extends JpaRepository<SalesOrderStatus, Long> {
}
