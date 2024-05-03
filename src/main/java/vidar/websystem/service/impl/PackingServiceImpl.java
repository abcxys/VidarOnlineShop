package vidar.websystem.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import vidar.websystem.domain.DatatablesView;
import vidar.websystem.domain.Driver;
import vidar.websystem.domain.SalesOrder;
import vidar.websystem.repository.DriverRepository;
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

	private final DriverRepository driverRepository;

	@Override
	public DatatablesView<SalesOrder> getAllOrders() {
		DatatablesView<SalesOrder> dataView = new DatatablesView<>();
		List<SalesOrder> salesOrderList = salesOrderRepository.findAll();
		
		int count = (int) salesOrderRepository.count();
		dataView.setData(salesOrderList);
		dataView.setRecordsTotal(count);
		return dataView;
	}

	/**
	 * @return All drivers in database.
	 */
	@Override
	public List<Driver> getDrivers() {
		return driverRepository.findAll();
	}

}
