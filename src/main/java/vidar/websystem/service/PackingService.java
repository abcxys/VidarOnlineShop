package vidar.websystem.service;

import org.springframework.http.ResponseEntity;
import vidar.websystem.domain.DatatablesView;
import vidar.websystem.domain.Driver;
import vidar.websystem.domain.SalesOrder;
import vidar.websystem.domain.User;
import vidar.websystem.dto.request.PackingSlipRequest;

import java.util.List;

/**
 * @author yishi.xing
 * @created Feb 15, 2024 - 10:47:30 PM
 */
public interface PackingService {
	DatatablesView<SalesOrder> getAllOrders();

	List<Driver> getDrivers();

	ResponseEntity<?> addPackingSlip(User user, PackingSlipRequest packingSlipRequest);
}
