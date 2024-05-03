package vidar.websystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vidar.websystem.domain.PackingSlip;

/**
 * @author yishi.xing
 * create datetime 5/3/2024 2:03 PM
 * description
 */
public interface PackingSlipRepository extends JpaRepository<PackingSlip, Long> {
}
