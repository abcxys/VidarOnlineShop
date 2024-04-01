package vidar.websystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import vidar.websystem.domain.PlankType;

/**
 * @author yishi.xing
 * @created Mar 28, 2024 - 11:02:57 AM
 */
public interface PlankTypeRepository extends JpaRepository<PlankType, Long> {
	List<PlankType> findByIdIn(List<Long> plank_type_ids);
	
	PlankType findOneByName(String name);
	
	List<PlankType> findAll();
}
