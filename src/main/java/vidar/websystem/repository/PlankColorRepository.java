package vidar.websystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import vidar.websystem.domain.PlankColor;

/**
 * @author yishi.xing
 * @created Feb 3, 2024 - 10:26:19 PM
 */
public interface PlankColorRepository extends JpaRepository<PlankColor, Long> {
	List<PlankColor> findByIdIn(List<Long> plank_color_ids);
	
	PlankColor findOneByName(String name);
}
