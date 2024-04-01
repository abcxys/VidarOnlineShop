package vidar.websystem.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import vidar.websystem.domain.PlankSize;

/**
 * @author yishi.xing
 * @created Feb 2, 2024 - 10:36:40 PM
 */
public interface PlankSizeRepository extends JpaRepository<PlankSize, Long> {
	List<PlankSize> findByIdIn(List<Long> plank_size_ids);
	
	List<PlankSize> findAll();
	
	/**
	 * Query duplicity with a combination of database fields
	 * @param width
	 * @param length
	 * @param thickness
	 * @param squarefootage
	 * @return
	 */
	PlankSize findByWidthInInchAndLengthAndThicknessInInchAndSquarefootPerCarton(
			String width,
			String length,
			String thickness,
			BigDecimal squarefootage);
}
