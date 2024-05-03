package vidar.websystem.service;

import vidar.websystem.domain.DatatablesView;
import vidar.websystem.domain.Driver;
import vidar.websystem.domain.SalesOrder;

import java.util.List;

/**
 * @author yishi.xing
 * @created Feb 15, 2024 - 10:47:30 PM
 */
public interface PackingService {
	DatatablesView<SalesOrder> getAllOrders();

	List<Driver> getDrivers();
}
