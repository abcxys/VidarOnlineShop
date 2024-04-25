package vidar.websystem.service;

import org.springframework.http.ResponseEntity;
import vidar.websystem.domain.User;
import vidar.websystem.dto.request.SalesOrderRequest;
import vidar.websystem.dto.response.MessageResponse;

/**
 * @author yishi.xing
 * create datetime 4/23/2024 9:52 AM
 * description
 */
public interface SalesOrderService {
    ResponseEntity<?> addSalesOrder(User user, SalesOrderRequest salesOrderRequest);
}
