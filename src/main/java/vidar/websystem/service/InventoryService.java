package vidar.websystem.service;

import org.springframework.data.domain.Pageable;
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

	DatatablesView<ProductInventoryItem> getFilteredProductFactoryInventoryItems(int colourId,
																				 int widthId,
																				 int speciesId,
																				 int gradeId,
																				 String batch);

	DatatablesView<Container> getFilteredContainers(String searchType, String searchValue, Pageable pageable);

	DatatablesView<InventoryItem> getInventoryItemsByProductId(int productId);

	DatatablesView<InventoryItem> getFactoryInventoryItemsByProductId(int productId);

	DatatablesView<ProductContainerItem> getContainerItemsById(Long containerId);

	ProductContainerItem getContainerItemById(Long productContainerId);
	
	Long getStockByFloorId(Long floorId);

	Inventory getInventoryById(Long id);

	FactoryInventory getFactoryInventoryById(Long id);

	Container getContainerById(Long id);

	boolean existsLocationTorontoWarehouse(String location);

	Long getLocationIdByBayAndWarehouseId(String bay, Long warehouseId);

	MessageResponse addInventory(User user, Inventory inventory);

	MessageResponse addFactoryInventory(User user, FactoryInventory factoryInventory);

	String updateInventory(User user, Inventory inventory);

	String updateFactoryInventory(User user, FactoryInventory factoryInventory);

	MessageResponse addContainer(User user, ContainerRequest containerRequest);

	String updateContainer(User user, Container container);

	String updateContainerItem(User user, ProductContainerItem productContainerItem);
}
