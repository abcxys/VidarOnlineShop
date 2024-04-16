package vidar.websystem.service;

import vidar.websystem.domain.*;
import vidar.websystem.dto.request.ContainerRequest;
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

	DatatablesView<Container> getFilteredContainers();

	DatatablesView<InventoryItem> getInventoryItemsByProductId(int productId);

	DatatablesView<ProductContainerItem> getContainerItemsById(Long containerId);
	
	Long getStockByFloorId(Long floorId);

	Inventory getInventoryById(Long id);

	Container getContainerById(Long id);

	boolean existsLocationTorontoWarehouse(String location);

	Long getLocationIdByBayAndWarehouseId(String bay, Long warehouseId);

	MessageResponse addInventory(User user, Inventory inventory);

	String updateInventory(User user, Inventory inventory);

	MessageResponse addContainer(User user, ContainerRequest containerRequest);

	String updateContainer(User user, Container container);
}
