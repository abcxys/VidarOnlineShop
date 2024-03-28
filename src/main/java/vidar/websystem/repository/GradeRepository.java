package vidar.websystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import vidar.websystem.domain.Grade;

/**
 * @author yishi.xing
 * @created Mar 28, 2024 - 2:46:52 PM
 */
public interface GradeRepository extends JpaRepository<Grade, Long> {
	List<Grade> findByIdIn(List<Long> grade_ids);
	
	Grade findOneByName(String name);
	
	List<Grade> findAll();
}
