package vidar.websystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import vidar.websystem.domain.Inventory;
import vidar.websystem.domain.InventoryItem;
import vidar.websystem.domain.ProductInventoryItem;

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
    		"LEFT JOIN grades grade ON floor.grade_id = grade.id " +
			"LEFT JOIN wood_species species ON floor.wood_species_id = species.id")
    List<ProductInventoryItem> findAllStocks();
	
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
    List<ProductInventoryItem> findFilteredStocks(int colourId,
												  int widthId,
												  int gradeId);
	
	@Query(nativeQuery = true, value = 
			"SELECT t.id as id, size.width_in_inch as width, color.name as colorName, " + 
			"grade.alias as gradeAlias, species.name as speciesName, batch_id as batch, t.bay as bay, COALESCE(t.stock, 0) as stock FROM " +
			"(SELECT floor.id as id, floor.plank_color_id, floor.plank_size_id, floor.grade_id, " +
			"floor.wood_species_id, floor.batch_id, STRING_AGG(locations.bay, ',') as bay, SUM(inventory.current_quantity) as stock FROM " +
			"hardwoodfloors floor " +
			"LEFT JOIN inventory	ON inventory.floor_id = floor.id " +
			"LEFT JOIN locations ON inventory.location_id = locations.id " + 
			"GROUP BY floor.id) t " +
			"LEFT JOIN plank_colors color ON t.plank_color_id = color.id " + 
			"LEFT JOIN plank_sizes size ON t.plank_size_id = size.id " +
			"LEFT JOIN grades grade ON t.grade_id = grade.id " +
			"LEFT JOIN wood_species species ON t.wood_species_id = species.id " +
			"WHERE (:colourId = -1 or color.id = :colourId) " + 
    		"AND (:widthId = -1 or size.id = :widthId) " +
			"AND (:speciesId = -1 or species.id = :speciesId) " +
			"AND (:gradeId = -1 or grade.id = :gradeId) " +
			"AND (UPPER(t.batch_id) LIKE UPPER(CONCAT('%', :batch, '%')))")
	List<ProductInventoryItem> findFilteredProductStocks(int colourId,
														 int widthId,
														 int speciesId,
														 int gradeId,
														 String batch);
	
	@Query(nativeQuery = true, value = 
			"SELECT inventory.current_quantity as stock FROM " +
			"inventory inventory " +
    		"LEFT JOIN hardwoodfloors floor ON floor.id = inventory.floor_id " +
    		"WHERE (:floorId = -1 or floor.id = :floorId) ")
	Long findStockByFloorId(long floorId);

	@Query(nativeQuery = true, value =
			"SELECT inty.id as id, inty.floor_id as productId, " +
	        "locs.bay as location, inty.current_quantity as quantity FROM " +
	        "inventory inty "+
			"LEFT JOIN locations locs "+
			"ON inty.location_id = locs.id " +
			"WHERE (:floorId = -1 or inty.floor_id = :floorId) " +
			"ORDER BY location")
	List<InventoryItem> findInventoryItemsByProductId(int floorId);
}
