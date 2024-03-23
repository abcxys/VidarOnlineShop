package vidar.websystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import vidar.websystem.domain.ColorDict;
import vidar.websystem.domain.FloorColorSize;
import vidar.websystem.domain.GradeDict;
import vidar.websystem.domain.Inventory;
import vidar.websystem.domain.InventoryItem;
import vidar.websystem.domain.SpeciesDict;
import vidar.websystem.domain.SizeDict;

/**
 * @author yishi.xing
 * @created Feb 20, 2024 - 10:48:48 PM
 */
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
	@Query(nativeQuery = true, value = "SELECT size.width_in_inch as width, color.name as colorName, " + 
			"grade.alias as gradeAlias, species.name as speciesName, inventory.current_quantity as stock FROM inventory inventory " +
    		"LEFT JOIN hardwoodfloors floor ON floor.id = inventory.floor_id " +
    		"LEFT JOIN plank_colors color ON floor.plank_color_id = color.id " +
    		"LEFT JOIN plank_sizes size ON floor.plank_size_id = size.id " +
    		"LEFT JOIN grades grade ON floor.grade_id = grade.id" + 
			"LEFT JOIN wood_species species ON floor.wood_species_id = species.id")
    List<InventoryItem> findAllStocks();
	
	@Query(nativeQuery = true, value = 
			"SELECT size.width_in_inch as width, color.name as colorName, " + 
			"grade.alias as gradeAlias, species.name as speciesName, inventory.current_quantity as stock FROM " +
			"inventory inventory " +
    		"LEFT JOIN hardwoodfloors floor ON floor.id = inventory.floor_id " +
    		"LEFT JOIN plank_colors color ON floor.plank_color_id = color.id " +
    		"LEFT JOIN plank_sizes size ON floor.plank_size_id = size.id " +
    		"LEFT JOIN grades grade ON floor.grade_id = grade.id " +
    		"LEFT JOIN wood_species species ON floor.wood_species_id = species.id " + 
    		"WHERE (:colourId = -1 or color.id = :colourId) " + 
    		"AND (:widthId = -1 or size.id = :widthId) " + 
    		"AND (:gradeId = -1 or grade.id = :gradeId) ")
    List<InventoryItem> findFilteredStocks(int colourId,
			int widthId,
			int gradeId);
	
	@Query(nativeQuery = true, value = 
			"SELECT inventory.current_quantity as stock FROM " +
			"inventory inventory " +
    		"LEFT JOIN hardwoodfloors floor ON floor.id = inventory.floor_id " +
    		"WHERE (:floorId = -1 or floor.id = :floorId) ")
	Long findStockByFloorId(long floorId);
	
	@Query(nativeQuery = true, value = "SELECT DISTINCT color.id AS id, color.name as colorName, color.alias as colorAlias "
			+ "FROM plank_colors color ORDER BY colorName ASC")
    List<ColorDict> findColorDict();
	
	@Query(nativeQuery = true, value = "SELECT DISTINCT species.id AS id, species.name AS name "
			+ "FROM wood_species species ORDER BY name ASC")
    List<SpeciesDict> findSpeciesDict();
	
	@Query(nativeQuery = true, value = "SELECT DISTINCT grades.id AS id, grades.name AS gradeName, grades.alias as gradeAlias "
			+ "FROM grades ORDER BY gradeName ASC")
    List<GradeDict> findGradeDict();
}
