package vidar.websystem.service;

import formbean.SalesOrderFilterConditionForm;
import org.springframework.http.ResponseEntity;
import vidar.websystem.domain.*;
import vidar.websystem.dto.request.SalesOrderRequest;

import java.util.List;

/**
 * @author yishi.xing
 * create datetime 4/23/2024 9:52 AM
 * description
 */
public interface SalesOrderService {
    SalesOrder getSalesOrder(Long salesOrderId);

    DatatablesView<SalesOrder> getFilteredSalesOrders(SalesOrderFilterConditionForm salesOrderFilterConditionForm);

    DatatablesView<SalesOrderItem> getSalesOrderProductsBySOId(Long salesOrderId);

    ResponseEntity<?> addSalesOrder(User user, SalesOrderRequest salesOrderRequest);

    ResponseEntity<?> updateSalesOrder(User user, SalesOrderRequest salesOrderRequest);

    List<SalesOrderStatus> getSalesOrderStatusDict();

    void insertSalesOrderItems(User user, SalesOrder salesOrder, SalesOrderRequest salesOrderRequest);

    void deactivateSalesOrderProductsBySoId(Long salesOrderId, User user);
}
