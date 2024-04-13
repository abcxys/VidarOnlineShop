package vidar.websystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vidar.websystem.domain.Container;

/**
 * @author yishi.xing
 * created Apr 13, 2024 - 11:21:01 AM
 */
public interface ContainerRepository extends JpaRepository<Container, Long> {

}
