package vidar.websystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vidar.websystem.domain.FactoryInventory;
import vidar.websystem.domain.InventoryItem;
import vidar.websystem.domain.ProductInventoryItem;

import java.util.List;

/**
 * @author yishi.xing
 * create datetime 5/11/2024 2:15 PM
 * description
 */
public interface FactoryInventoryRepository extends JpaRepository<FactoryInventory, Long> {

    @Query(nativeQuery = true, value =
            "SELECT t.id as id, size.width_in_inch as width, color.name as colorName, " +
                    "grade.alias as gradeAlias, species.name as speciesName, batch_id as batch, COALESCE(t.bay, 'N/A') as bay, COALESCE(t.stock, 0) as stock FROM " +
                    "(SELECT floor.id as id, floor.plank_color_id, floor.plank_size_id, floor.grade_id, " +
                    "floor.wood_species_id, floor.batch_id, STRING_AGG(locations.bay, ',') as bay, SUM(factory_inventory.current_quantity) as stock FROM " +
                    "hardwoodfloors floor " +
                    "LEFT JOIN factory_inventory	ON factory_inventory.floor_id = floor.id " +
                    "LEFT JOIN locations ON factory_inventory.location_id = locations.id " +
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
    List<ProductInventoryItem> findFilteredProductFactoryStocks(int colourId,
                                                                int widthId,
                                                                int speciesId,
                                                                int gradeId,
                                                                String batch);

    @Query(nativeQuery = true, value =
            "SELECT fac_inty.id as id, fac_inty.floor_id as productId, " +
                    "locs.bay as location, fac_inty.current_quantity as quantity FROM " +
                    "factory_inventory fac_inty "+
                    "LEFT JOIN locations locs "+
                    "ON fac_inty.location_id = locs.id " +
                    "WHERE (:productId = -1 or fac_inty.floor_id = :productId) " +
                    "ORDER BY location")
    List<InventoryItem> findFactoryInventoryItemsByProductId(int productId);
}
