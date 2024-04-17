package vidar.websystem.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import vidar.websystem.domain.DatatablesView;
import vidar.websystem.domain.SalesOrder;
import vidar.websystem.repository.SalesOrderRepository;
import vidar.websystem.service.PackingService;

/**
 * @author yishi.xing
 * @created Feb 15, 2024 - 10:50:47 PM
 */
@Service
@RequiredArgsConstructor
public class PackingServiceImpl implements PackingService{
	
	private final SalesOrderRepository salesOrderRepository;

	@Override
	public DatatablesView<SalesOrder> getAllOrders() {
		DatatablesView<SalesOrder> dataView = new DatatablesView<SalesOrder>();
		List<SalesOrder> salesOrderList = salesOrderRepository.findAll();
		
		int count = (int) salesOrderRepository.count();
		dataView.setData(salesOrderList);
		dataView.setRecordsTotal(count);
		return dataView;
	}

}
