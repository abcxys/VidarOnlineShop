package vidar.websystem.service;

import org.springframework.http.ResponseEntity;
import vidar.websystem.domain.User;
import vidar.websystem.domain.SalesOrder;
import vidar.websystem.dto.request.SalesOrderRequest;

/**
 * @author yishi.xing
 * create datetime 4/23/2024 9:52 AM
 * description
 */
public interface SalesOrderService {
    SalesOrder getSalesOrder(Long salesOrderId);

    ResponseEntity<?> addSalesOrder(User user, SalesOrderRequest salesOrderRequest);
}
