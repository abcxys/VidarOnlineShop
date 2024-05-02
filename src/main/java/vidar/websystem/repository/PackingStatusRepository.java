package vidar.websystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vidar.websystem.domain.PackingStatus;

/**
 * @author yishi.xing
 * create datetime 5/2/2024 4:09 PM
 * description
 */
public interface PackingStatusRepository extends JpaRepository<PackingStatus, Long> {
    PackingStatus findById(long id);
}
