package com.gmail.merikbest2015.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gmail.merikbest2015.ecommerce.domain.ColorDict;
import com.gmail.merikbest2015.ecommerce.domain.FloorColorSize;
import com.gmail.merikbest2015.ecommerce.domain.Inventory;
import com.gmail.merikbest2015.ecommerce.domain.InventoryItem;
import com.gmail.merikbest2015.ecommerce.domain.WidthDict;

/**
 * @author yishi.xing
 * @created Feb 20, 2024 - 10:48:48 PM
 */
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
	@Query(nativeQuery = true, value = "SELECT size.width_in_inch as width, color.name as colorName, " + 
			"grade.alias as gradeAlias, inventory.current_quantity as stock FROM inventory inventory " +
    		"LEFT JOIN hardwoodfloors floor ON floor.id = inventory.floor_id " +
    		"LEFT JOIN plank_colors color ON floor.plank_color_id = color.id " +
    		"LEFT JOIN plank_sizes size ON floor.plank_size_id = size.id " +
    		"LEFT JOIN grades grade ON floor.grade_id = grade.id")
    List<InventoryItem> findAllStocks();
	
	@Query(nativeQuery = true, value = "SELECT DISTINCT color.id AS id, color.name as colorName, color.alias as colorAlias "
			+ "FROM hardwoodfloors floor "
			+ "LEFT JOIN plank_colors color "
			+ "ON floor.plank_color_id = color.id")
    List<ColorDict> findColorDict();
	
	@Query(nativeQuery = true, value = "SELECT DISTINCT size.id AS id, size.width_in_inch AS widthInInch "
			+ "FROM hardwoodfloors floor "
			+ "LEFT JOIN plank_sizes size "
			+ "ON floor.plank_size_id = size.id")
    List<WidthDict> findWidthDict();
}