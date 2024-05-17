package vidar.websystem.service;

import formbean.SalesOrderFilterConditionForm;
import org.springframework.http.ResponseEntity;
import vidar.websystem.domain.*;
import vidar.websystem.dto.request.PackingSlipRequest;

import java.util.List;

/**
 * @author yishi.xing
 * @create Feb 15, 2024 - 10:47:30 PM
 */
public interface PackingService {
	PackingSlip getPackingSlipById(Long packingSlipId);

	DatatablesView<SalesOrder> getAllOrders();

	DatatablesView<PackingSlip> getFilteredPackingSlips(SalesOrderFilterConditionForm salesOrderFilterConditionForm);

	List<Driver> getDrivers();

	List<ShippingMethod> getShippingMethods();

	ResponseEntity<?> addPackingSlip(User user, PackingSlipRequest packingSlipRequest);

	ResponseEntity<?> updatePackingSlip(User user, PackingSlipRequest packingSlipRequest);

	void insertSalesOrderPackingItems(User user, PackingSlip packingSlip, PackingSlipRequest packingSlipRequest);

	List<PackingStatus> getPackingSlipStatusDict();

	DatatablesView<SalesOrderItem> getSalesOrderPackingItemsTableByPackingSlipId(Long packingSlipId);

	List<SalesOrderItem> getSalesOrderPackingItemsByPackingSlipId(Long packingSlipId);

	ResponseEntity<?> updatePackingSlipStatus(User user, PackingSlip packingSlip, Long statusId);
}
