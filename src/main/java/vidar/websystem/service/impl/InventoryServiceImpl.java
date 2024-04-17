package vidar.websystem.service.impl;

import java.util.Date;
import java.util.List;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vidar.websystem.constants.SuccessMessage;
import vidar.websystem.domain.*;
import vidar.websystem.dto.request.ContainerRequest;
import vidar.websystem.dto.response.MessageResponse;
import vidar.websystem.repository.*;
import vidar.websystem.service.InventoryService;

import javax.transaction.Transactional;

/**
 * @author yishi.xing
 * create datetime Feb 20, 2024 - 10:45:52 PM
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
	
	private final InventoryRepository inventoryRepository;
	private final LocationRepository locationRepository;
	private final InventoryEventRepository inventoryEventRepository;
	private final ContainerRepository containerRepository;
	private final ProductContainerRepository productContainerRepository;
	private final ModelMapper modelMapper;

	@Override
	public DatatablesView<ProductInventoryItem> getAllInventoryItems() {
		DatatablesView<ProductInventoryItem> dataView = new DatatablesView<>();
		List<ProductInventoryItem> stock = inventoryRepository.findAllStocks();
		
		int count = (int) inventoryRepository.count();
		dataView.setData(stock);
		dataView.setRecordsTotal(count);
		return dataView;
	}
	
	@Override
	public DatatablesView<ProductInventoryItem> getFilteredInventoryItems(int colourId, int widthId, int speciesId,
																		  int gradeId) {
		DatatablesView<ProductInventoryItem> dataView = new DatatablesView<>();
		List<ProductInventoryItem> stock = inventoryRepository.findFilteredStocks(colourId, widthId,
				gradeId);
		int count = (int) inventoryRepository.count();
		dataView.setData(stock);
		dataView.setRecordsTotal(count);
		return dataView;
	}

	@Override
	public Long getStockByFloorId(Long floorId) {
		return inventoryRepository.findStockByFloorId(floorId);
	}

	/**
	 * @param id inventory_id
	 * @return the queried inventory entity
	 */
	@Override
	public Inventory getInventoryById(Long id) {
		return inventoryRepository.findById(id).orElse(null);
	}

	/**
	 * @param id container id
	 * @return Container instance correspond to argument id
	 */
	@Override
	public Container getContainerById(Long id) {
		return containerRepository.findById(id).orElse(null);
	}

	@Override
	public boolean existsLocationTorontoWarehouse(String location) {
		// The warehouse_id for Toronto warehouse is set to Long value of 1.
		return locationRepository.existsByBayAndWarehouseId(location, 1L);
	}

	@Override
	public Long getLocationIdByBayAndWarehouseId(String bay, Long warehouseId) {
		return locationRepository.findIdByBayAndWarehouseId(bay, warehouseId);
	}

	/**
	 * @param user Authenticated user
	 * @param inventory Added inventory entity instance
	 * @return Resulting Message Response
	 */
	@Override
	@SneakyThrows
	@Transactional
	public MessageResponse addInventory(User user, Inventory inventory) {
		inventory.setCreateTime(new Date());
		inventory.setCreateUserId(user.getId());
		inventoryRepository.save(inventory);
		addManualSetInventoryEvent(user, inventory);
		return new MessageResponse("alert-success", SuccessMessage.INVENTORY_ADDED);
	}

	/**
	 * @param user authenticated user information
	 * @param inventory the inventory entity that needs to be updated
	 * @return success/fail message
	 */
	@Override
	public String updateInventory(User user, Inventory inventory) {
		inventory.setUpdateTime(new Date());
		inventory.setUpdateUserId(user.getId());
		// add into inventory_event table.
		addManualSetInventoryEvent(user, inventory);
		inventoryRepository.save(inventory);
		return SuccessMessage.INVENTORY_ITEM_UPDATED;
	}

	/**
	 * @param user Authenticated user
	 * @param containerRequest ContainerRequest from client-side JSON
	 * @return Message of success/failure
	 */
	@Override
	public MessageResponse addContainer(User user, ContainerRequest containerRequest) {
		Container container = modelMapper.map(containerRequest, Container.class);
		container.setTargetWarehouseId(1L);//Set the default targeted warehouse to be Toronto warehouse
		container.setContainerStatusId(1L);
		container.setCreateTime(new Date());
		container.setCreateUserId(user.getId());
		containerRepository.save(container);
		containerRepository.flush();
		log.info("The inserted container returns id = " + container.getId());

		// Insert the container_floors entries for containerRequest->containerItems
		for (ProductContainerItem productContainerItem : containerRequest.getContainerItems()){
			productContainerItem.setContainerId(container.getId());
			productContainerItem.setCreateTime(new Date());
			productContainerItem.setCreateUserId(user.getId());
			productContainerRepository.save(productContainerItem);
		}
		return new MessageResponse("alert-success", SuccessMessage.CONTAINER_ADDED);
	}

	/**
	 * @param user Authenticated user
	 * @param container Container instance to update
	 * @return message
	 */
	@Override
	public String updateContainer(User user, Container container) {
		container.setUpdateTime(new Date());
		container.setUpdateUserId(user.getId());
		try{
			containerRepository.save(container);
			return SuccessMessage.CONTAINER_UPDATED;
		} catch (Exception e){
			return "Container update failed";
		}
	}

	/**
	 * @param user Authenticated user
	 * @param productContainerItem Container product item
	 * @return message
	 */
	@Override
	public String updateContainerItem(User user, ProductContainerItem productContainerItem) {
		productContainerItem.setUpdateTime(new Date());
		productContainerItem.setUpdateUserId(user.getId());
		try {
			productContainerRepository.save(productContainerItem);
			return SuccessMessage.CONTAINER_PRODUCT_ITEM_UPDATED;
		} catch (Exception e){
			return "Container item update failed";
		}
	}

	@Override
	public DatatablesView<ProductInventoryItem> getFilteredProductInventoryItems(int colourId, int widthId, int speciesId,
																				 int gradeId, String batch) {
		DatatablesView<ProductInventoryItem> dataView = new DatatablesView<>();
		List<ProductInventoryItem> stock = inventoryRepository.findFilteredProductStocks(colourId, widthId, speciesId,
				gradeId, batch);
		int count = stock.size();
		dataView.setData(stock);
		dataView.setRecordsTotal(count);
		return dataView;
	}

	/**
	 * @return DatatablesView of filtered containers
	 */
	@Override
	public DatatablesView<Container> getFilteredContainers(String searchType, String searchValue, Pageable pageable) {
		DatatablesView<Container> dataView = new DatatablesView<>();
		//List<Container> containers = containerRepository.findAll();
		List<Container> filteredContainers = containerRepository.searchContainers(searchType, searchValue, pageable).getContent();
		int count = filteredContainers.size();
		dataView.setData(filteredContainers);
		dataView.setRecordsTotal(count);
		return dataView;
	}

	@Override
	public DatatablesView<InventoryItem> getInventoryItemsByProductId(int productId) {
		DatatablesView<InventoryItem> dataView = new DatatablesView<>();
		List<InventoryItem> items = inventoryRepository.findInventoryItemsByProductId(productId);
		int count = items.size();
		dataView.setData(items);
		dataView.setRecordsTotal(count);
		return dataView;
	}

	/**
	 * @param containerId Container id
	 * @return Datatable of product container item.
	 */
	@Override
	public DatatablesView<ProductContainerItem> getContainerItemsById(Long containerId) {
		DatatablesView<ProductContainerItem> dataView = new DatatablesView<>();
		List<ProductContainerItem> items = productContainerRepository.findByContainerId(containerId);
		int count = items.size();
		dataView.setData(items);
		dataView.setRecordsTotal(count);
		return dataView;
	}

	/**
	 * @param productContainerId Id of product container item.
	 * @return the exact container product item.
	 */
	@Override
	public ProductContainerItem getContainerItemById(Long productContainerId) {
		return productContainerRepository.findById(productContainerId).orElse(null);
	}

	private void addManualSetInventoryEvent(User user, Inventory inventory){
		InventoryEvent event = new InventoryEvent();
		event.setInventoryId(inventory.getId());
		event.setInventoryEventTypeId(3L);
		event.setLocationId(inventory.getLocationId());
		event.setQuantity(inventory.getCurrentQuantity());
		event.setCreateTime(new Date());
		event.setCreateUserId(user.getId());
		inventoryEventRepository.save(event);
	}
}
