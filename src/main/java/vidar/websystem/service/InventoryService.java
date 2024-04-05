package vidar.websystem.service;

import vidar.websystem.domain.DatatablesView;
import vidar.websystem.domain.ProductInventoryItem;

/**
 * @author yishi.xing
 * @created Feb 20, 2024 - 10:41:12 PM
 */
public interface InventoryService {
	
	DatatablesView<ProductInventoryItem> getAllInventoryItems();
	
	DatatablesView<ProductInventoryItem> getFilteredInventoryItems(int colourId,
																   int widthId,
																   int speciesId,
																   int gradeId);
	
	DatatablesView<ProductInventoryItem> getFilteredProductInventoryItems(int colourId,
																		  int widthId,
																		  int speciesId,
																		  int gradeId,
																		  String batch);
	
	Long getStockByFloorId(long floorId);
}
