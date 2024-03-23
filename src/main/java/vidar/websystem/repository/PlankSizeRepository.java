package vidar.websystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import vidar.websystem.domain.PlankSize;
import vidar.websystem.domain.SizeDict;

/**
 * @author yishi.xing
 * @created Feb 2, 2024 - 10:36:40 PM
 */
public interface PlankSizeRepository extends JpaRepository<PlankSize, Long> {
	List<PlankSize> findByIdIn(List<Long> plank_size_ids);
	
	@Query(nativeQuery = true, value = "SELECT DISTINCT size.id AS id, size.width_in_inch AS widthInInch, "
			+ "size.squarefoot_per_carton AS SqftPerCarton, size.length AS length FROM plank_sizes size ORDER BY widthInInch ASC")
    List<SizeDict> findSizeDict();
}
