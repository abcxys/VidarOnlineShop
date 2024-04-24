package vidar.websystem.service.impl;

import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import vidar.websystem.constants.ErrorMessage;
import vidar.websystem.constants.SuccessMessage;
import vidar.websystem.domain.SalesOrder;
import vidar.websystem.domain.SalesOrderItem;
import vidar.websystem.domain.SalesOrderProduct;
import vidar.websystem.domain.User;
import vidar.websystem.dto.request.SalesOrderItemRequest;
import vidar.websystem.dto.request.SalesOrderRequest;
import vidar.websystem.dto.response.MessageResponse;
import vidar.websystem.repository.*;
import vidar.websystem.service.SalesOrderService;

import javax.mail.Message;

/**
 * @author yishi.xing
 * create datetime 4/23/2024 9:52 AM
 * description
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SalesOrderServiceImpl implements SalesOrderService {

    private final ModelMapper modelMapper;
    private final SalesOrderRepository salesOrderRepository;
    private final SalesRepRepository salesRepRepository;
    private final DealerRepository dealerRepository;
    private final SalesOrderProductRepository salesOrderProductRepository;
    private final UserRepository userRepository;

    /**
     * @param user Authenticated user
     * @param salesOrderRequest SalesOrderRequest from client-side
     * @return Success/Failure message
     */
    @Override
    public MessageResponse addSalesOrder(User user, SalesOrderRequest salesOrderRequest) {
        SalesOrder salesOrder = modelMapper.map(salesOrderRequest, SalesOrder.class);
        // Set city as the salesOrderRequest's address attribute, just for now.
        salesOrder.setCity(salesOrderRequest.getAddress());
        salesOrder.setUser(user);
        salesOrder.setDealer(dealerRepository.findById(salesOrderRequest.getDealerId()).orElse(null));
        salesOrder.setSalesRep(salesRepRepository.findById(salesOrderRequest.getSalesRepId()).orElse(null));
        salesOrder.setCreateTime(new Date());
        salesOrder.setCreateUserId(user.getId());
        try {
            salesOrderRepository.save(salesOrder);
            salesOrderRepository.flush();
            log.info("The inserted sales order returns id = " + salesOrder.getId());

            // Insert salesOrderItem entries for salesOrderRequest
            for (SalesOrderItemRequest soi : salesOrderRequest.getSalesOrderItems()) {
                SalesOrderProduct sop = new SalesOrderProduct();
                sop.setSalesOrderId(salesOrder.getId());
                sop.setQuantityOrdered(soi.getQuantity());
                sop.setHardwoodfloorId(soi.getProductId());
                sop.setCreateTime(new Date());
                sop.setCreateUserId(user.getId());
                salesOrderProductRepository.save(sop);
            }

            // Clear everything in cart after successfully create new sales order.
            user.getPerfumeList().clear();
            userRepository.save(user);
        } catch (Exception e){
            return new MessageResponse("alert-danger", ErrorMessage.SALES_ORDER_CREATED_FAILED);
        }

        return new MessageResponse("alert-success", SuccessMessage.SALES_ORDER_CREATED);
    }
}
