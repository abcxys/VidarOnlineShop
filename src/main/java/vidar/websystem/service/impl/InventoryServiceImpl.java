package vidar.websystem.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vidar.websystem.domain.DatatablesView;
import vidar.websystem.domain.ProductInventoryItem;
import vidar.websystem.repository.InventoryRepository;
import vidar.websystem.service.InventoryService;

/**
 * @author yishi.xing
 * @created Feb 20, 2024 - 10:45:52 PM
 */
@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
	
	private final InventoryRepository inventoryRepository;

	@Override
	public DatatablesView<ProductInventoryItem> getAllInventoryItems() {
		DatatablesView<ProductInventoryItem> dataView = new DatatablesView<ProductInventoryItem>();
		List<ProductInventoryItem> stock = inventoryRepository.findAllStocks();
		
		int count = (int) inventoryRepository.count();
		dataView.setData(stock);
		dataView.setRecordsTotal(count);
		return dataView;
	}
	
	@Override
	public DatatablesView<ProductInventoryItem> getFilteredInventoryItems(int colourId, int widthId, int speciesId,
																		  int gradeId) {
		DatatablesView<ProductInventoryItem> dataView = new DatatablesView<ProductInventoryItem>();
		List<ProductInventoryItem> stock = inventoryRepository.findFilteredStocks(colourId, widthId,
				gradeId);
		int count = (int) inventoryRepository.count();
		dataView.setData(stock);
		dataView.setRecordsTotal(count);
		return dataView;
	}

	@Override
	public Long getStockByFloorId(long floorId) {
		Long quantity = inventoryRepository.findStockByFloorId(floorId);
		return quantity;
	}

	@Override
	public DatatablesView<ProductInventoryItem> getFilteredProductInventoryItems(int colourId, int widthId, int speciesId,
																				 int gradeId, String batch) {
		DatatablesView<ProductInventoryItem> dataView = new DatatablesView<ProductInventoryItem>();
		List<ProductInventoryItem> stock = inventoryRepository.findFilteredProductStocks(colourId, widthId, speciesId,
				gradeId, batch);
		int count = (int) inventoryRepository.count();
		dataView.setData(stock);
		dataView.setRecordsTotal(count);
		return dataView;
	}
}
