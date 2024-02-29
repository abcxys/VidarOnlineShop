package com.gmail.merikbest2015.ecommerce.service;

import java.util.List;

import com.gmail.merikbest2015.ecommerce.domain.ColorDict;
import com.gmail.merikbest2015.ecommerce.domain.DatatablesView;
import com.gmail.merikbest2015.ecommerce.domain.GradeDict;
import com.gmail.merikbest2015.ecommerce.domain.InventoryItem;
import com.gmail.merikbest2015.ecommerce.domain.SpeciesDict;
import com.gmail.merikbest2015.ecommerce.domain.WidthDict;

/**
 * @author yishi.xing
 * @created Feb 20, 2024 - 10:41:12 PM
 */
public interface InventoryService {
	
	DatatablesView<InventoryItem> getAllInventoryItems();
	
	DatatablesView<InventoryItem> getFilteredInventoryItems(int colourId,
			int widthId,
			int speciesId,
			int gradeId);
	
	List<ColorDict> getColorDict();
	
	List<WidthDict> getWidthDict();
	
	List<SpeciesDict> getSpeciesDict();
	
	List<GradeDict> getGradeDict();
}
