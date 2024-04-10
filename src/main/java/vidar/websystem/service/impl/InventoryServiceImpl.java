package vidar.websystem.service.impl;

import java.util.Date;
import java.util.List;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vidar.websystem.domain.*;
import vidar.websystem.dto.response.MessageResponse;
import vidar.websystem.repository.InventoryRepository;
import vidar.websystem.repository.LocationRepository;
import vidar.websystem.service.InventoryService;

import javax.transaction.Transactional;

/**
 * @author yishi.xing
 * create datetime Feb 20, 2024 - 10:45:52 PM
 */
@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
	
	private final InventoryRepository inventoryRepository;
	private final LocationRepository locationRepository;

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
	 * @param user
	 * @param inventory
	 * @return
	 */
	@Override
	@SneakyThrows
	@Transactional
	public MessageResponse addInventory(User user, Inventory inventory) {
		inventory.setCreateTime(new Date());
		inventory.setCreateUserId(user.getId());
		inventoryRepository.save(inventory);
		return new MessageResponse("alert-success", "New inventory added successfully");
	}

	/**
	 * @param user authenticated user information
	 * @param inventory the inventory entity that needs to be updated
	 * @return success/fail message
	 */
	@Override
	public String updateInventory(User user, Inventory inventory) {
		//TODO: add into inventory_event table.
		inventory.setUpdateTime(new Date());
		inventory.setUpdateUserId(user.getId());
		inventoryRepository.save(inventory);
		return "Inventory item updated successfully!";
	}

	@Override
	public DatatablesView<ProductInventoryItem> getFilteredProductInventoryItems(int colourId, int widthId, int speciesId,
																				 int gradeId, String batch) {
		DatatablesView<ProductInventoryItem> dataView = new DatatablesView<>();
		List<ProductInventoryItem> stock = inventoryRepository.findFilteredProductStocks(colourId, widthId, speciesId,
				gradeId, batch);
		int count = (int) inventoryRepository.count();
		dataView.setData(stock);
		dataView.setRecordsTotal(count);
		return dataView;
	}

	@Override
	public DatatablesView<InventoryItem> getInventoryItemsByProductId(int productId) {
		DatatablesView<InventoryItem> dataView = new DatatablesView<>();
		List<InventoryItem> items = inventoryRepository.findInventoryItemsByProductId(productId);
		int count = (int) inventoryRepository.count();
		dataView.setData(items);
		dataView.setRecordsTotal(count);
		return dataView;
	}
}
