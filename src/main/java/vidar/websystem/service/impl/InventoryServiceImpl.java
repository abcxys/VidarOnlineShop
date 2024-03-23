package vidar.websystem.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vidar.websystem.domain.ColorDict;
import vidar.websystem.domain.DatatablesView;
import vidar.websystem.domain.GradeDict;
import vidar.websystem.domain.InventoryItem;
import vidar.websystem.domain.SpeciesDict;
import vidar.websystem.domain.SizeDict;
import vidar.websystem.repository.InventoryRepository;
import vidar.websystem.repository.PlankSizeRepository;
import vidar.websystem.service.InventoryService;

/**
 * @author yishi.xing
 * @created Feb 20, 2024 - 10:45:52 PM
 */
@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
	
	private final InventoryRepository inventoryRepository;
	
	private final PlankSizeRepository plankSizeRepository;

	@Override
	public DatatablesView<InventoryItem> getAllInventoryItems() {
		DatatablesView<InventoryItem> dataView = new DatatablesView<InventoryItem>();
		List<InventoryItem> stock = inventoryRepository.findAllStocks();
		
		int count = (int) inventoryRepository.count();
		dataView.setData(stock);
		dataView.setRecordsTotal(count);
		return dataView;
	}
	
	@Override
	public DatatablesView<InventoryItem> getFilteredInventoryItems(int colourId, int widthId, int speciesId,
			int gradeId) {
		DatatablesView<InventoryItem> dataView = new DatatablesView<InventoryItem>();
		List<InventoryItem> stock = inventoryRepository.findFilteredStocks(colourId, widthId,
				gradeId);
		int count = (int) inventoryRepository.count();
		dataView.setData(stock);
		dataView.setRecordsTotal(count);
		return dataView;
	}

	@Override
	public List<ColorDict> getColorDict() {
		List<ColorDict> colorDict = inventoryRepository.findColorDict();
		return colorDict;
	}

	@Override
	public List<SizeDict> getSizeDict() {
		List<SizeDict> sizeDict = plankSizeRepository.findSizeDict();
		return sizeDict;
	}

	@Override
	public List<SpeciesDict> getSpeciesDict() {
		List<SpeciesDict> speciesDict = inventoryRepository.findSpeciesDict();
		return speciesDict;
	}

	@Override
	public List<GradeDict> getGradeDict() {
		List<GradeDict> gradeDict = inventoryRepository.findGradeDict();
		return gradeDict;
	}

	@Override
	public Long getStockByFloorId(long floorId) {
		Long quantity = inventoryRepository.findStockByFloorId(floorId);
		return quantity;
	}
}