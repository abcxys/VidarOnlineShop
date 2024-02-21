package com.gmail.merikbest2015.ecommerce.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gmail.merikbest2015.ecommerce.domain.DatatablesView;
import com.gmail.merikbest2015.ecommerce.domain.InventoryItem;
import com.gmail.merikbest2015.ecommerce.repository.InventoryRepository;
import com.gmail.merikbest2015.ecommerce.service.InventoryService;

import lombok.RequiredArgsConstructor;

/**
 * @author yishi.xing
 * @created Feb 20, 2024 - 10:45:52 PM
 */
@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
	
	private final InventoryRepository inventoryRepository;

	@Override
	public DatatablesView<InventoryItem> getAllInventoryItems() {
		DatatablesView<InventoryItem> dataView = new DatatablesView<InventoryItem>();
		List<InventoryItem> stock = inventoryRepository.findAllStocks();
		
		int count = (int) inventoryRepository.count();
		dataView.setData(stock);
		dataView.setRecordsTotal(count);
		return dataView;
	}
}
