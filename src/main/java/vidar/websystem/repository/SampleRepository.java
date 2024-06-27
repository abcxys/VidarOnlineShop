package vidar.websystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vidar.websystem.domain.Sample;
/**
 * @author yishi.xing
 * create datetime 6/26/2024 10:34 PM
 * description
 */
public interface SampleRepository extends JpaRepository<Sample, Long> {

}
