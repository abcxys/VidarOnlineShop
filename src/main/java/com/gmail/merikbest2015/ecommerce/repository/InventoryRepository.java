package com.gmail.merikbest2015.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gmail.merikbest2015.ecommerce.domain.Inventory;
import com.gmail.merikbest2015.ecommerce.domain.InventoryItem;

/**
 * @author yishi.xing
 * @created Feb 20, 2024 - 10:48:48 PM
 */
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
	@Query(nativeQuery = true, value = "SELECT floor.id as id, floor.filename as filename, color.name as colorName, size.width_in_inch as width, size.length as length, " + 
    		"size.thickness_in_inch as thickness, squarefoot_per_carton as sqftPerCarton FROM hardwoodfloors floor " +
    		"LEFT JOIN plank_colors color ON floor.plank_color_id = color.id " +
    		"LEFT JOIN plank_sizes size ON floor.plank_size_id = size.id " +
    		"WHERE floor.id IN :ids")
    List<InventoryItem> findAllStocks();
}
