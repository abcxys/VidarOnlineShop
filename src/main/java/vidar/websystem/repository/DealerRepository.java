package vidar.websystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vidar.websystem.domain.Dealer;

import java.util.List;

/**
 * @author yishi.xing
 * create datetime 4/18/2024 3:41 PM
 * description
 */
public interface DealerRepository extends JpaRepository<Dealer, Long> {

    List<Dealer> findByActiveTrue();

    Dealer findByCompanyName(String companyName);
}
