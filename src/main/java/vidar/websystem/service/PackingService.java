package vidar.websystem.service;

import vidar.websystem.domain.DatatablesView;
import vidar.websystem.domain.SalesOrder;

/**
 * @author yishi.xing
 * @created Feb 15, 2024 - 10:47:30 PM
 */
public interface PackingService {
	DatatablesView<SalesOrder> getAllOrders();
}
