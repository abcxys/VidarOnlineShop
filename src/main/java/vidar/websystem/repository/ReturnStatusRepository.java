package vidar.websystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vidar.websystem.domain.ReturnStatus;

/**
 * @author yishi.xing
 * create datetime 5/10/2024 8:52 AM
 * description
 */
public interface ReturnStatusRepository extends JpaRepository<ReturnStatus, Long> {
    ReturnStatus findById(long id);
}
