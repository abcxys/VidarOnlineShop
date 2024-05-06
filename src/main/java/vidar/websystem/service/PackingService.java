package vidar.websystem.service;

import formbean.SalesOrderFilterConditionForm;
import org.springframework.http.ResponseEntity;
import vidar.websystem.domain.*;
import vidar.websystem.dto.request.PackingSlipRequest;

import java.util.List;

/**
 * @author yishi.xing
 * @created Feb 15, 2024 - 10:47:30 PM
 */
public interface PackingService {
	DatatablesView<SalesOrder> getAllOrders();

	DatatablesView<PackingSlip> getFilteredPackingSlips(SalesOrderFilterConditionForm salesOrderFilterConditionForm);

	List<Driver> getDrivers();

	ResponseEntity<?> addPackingSlip(User user, PackingSlipRequest packingSlipRequest);

	void insertSalesOrderPackingItems(User user, PackingSlip packingSlip, PackingSlipRequest packingSlipRequest);

	List<PackingStatus> getPackingSlipStatusDict();
}
