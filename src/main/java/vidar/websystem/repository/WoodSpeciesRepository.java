package vidar.websystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import vidar.websystem.domain.WoodSpecies;

/**
 * @author yishi.xing
 * @created Mar 27, 2024 - 3:21:01 PM
 */
public interface WoodSpeciesRepository extends JpaRepository<WoodSpecies, Long> {
	List<WoodSpecies> findByIdIn(List<Long> wood_species_ids);
	
	List<WoodSpecies> findAll();
}
