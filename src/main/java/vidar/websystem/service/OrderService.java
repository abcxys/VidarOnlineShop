package vidar.websystem.service;

import vidar.websystem.domain.HardwoodFloor;
import vidar.websystem.domain.SalesOrder;
import vidar.websystem.domain.User;
import vidar.websystem.dto.request.OrderRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {

    SalesOrder getOrder(Long orderId);

    List<HardwoodFloor> getOrdering();

    Page<SalesOrder> getUserOrdersList(Pageable pageable);

    Long postOrder(User user, OrderRequest orderRequest);
}
