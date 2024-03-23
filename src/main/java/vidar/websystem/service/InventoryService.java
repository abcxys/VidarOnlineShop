package vidar.websystem.service;

import java.util.List;

import vidar.websystem.domain.ColorDict;
import vidar.websystem.domain.DatatablesView;
import vidar.websystem.domain.GradeDict;
import vidar.websystem.domain.InventoryItem;
import vidar.websystem.domain.SpeciesDict;
import vidar.websystem.domain.SizeDict;

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
	
	Long getStockByFloorId(long floorId);
	
	List<ColorDict> getColorDict();
	
	List<SizeDict> getSizeDict();
	
	List<SpeciesDict> getSpeciesDict();
	
	List<GradeDict> getGradeDict();
}
