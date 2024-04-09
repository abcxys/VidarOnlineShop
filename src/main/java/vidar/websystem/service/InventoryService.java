package vidar.websystem.service;

import vidar.websystem.domain.*;
import vidar.websystem.dto.response.MessageResponse;

/**
 * @author yishi.xing
 * create datetime Feb 20, 2024 - 10:41:12 PM
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

	DatatablesView<InventoryItem> getInventoryItemsByProductId(int productId);
	
	Long getStockByFloorId(long floorId);

	boolean existsLocationTorontoWarehouse(String location);

	Long getLocationIdByBayAndWarehouseId(String bay, Long warehouseId);

	MessageResponse addInventory(User user, Inventory inventory);
}
