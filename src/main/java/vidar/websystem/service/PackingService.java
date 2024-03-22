package vidar.websystem.service;

import vidar.websystem.domain.DatatablesView;
import vidar.websystem.domain.Order;

/**
 * @author yishi.xing
 * @created Feb 15, 2024 - 10:47:30 PM
 */
public interface PackingService {
	DatatablesView<Order> getAllOrders();
}
