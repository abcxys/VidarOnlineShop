package vidar.websystem.service.impl;

import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import vidar.websystem.constants.SuccessMessage;
import vidar.websystem.domain.SalesOrder;
import vidar.websystem.domain.SalesOrderItem;
import vidar.websystem.domain.User;
import vidar.websystem.dto.request.SalesOrderRequest;
import vidar.websystem.dto.response.MessageResponse;
import vidar.websystem.repository.DealerRepository;
import vidar.websystem.repository.SalesOrderRepository;
import vidar.websystem.repository.SalesRepRepository;
import vidar.websystem.service.SalesOrderService;

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
        salesOrderRepository.save(salesOrder);
        salesOrderRepository.flush();
        log.info("The inserted sales order returns id = " + salesOrder.getId());

        // Insert salesOrderItem entries for salesOrderRequest
        for (SalesOrderItem soi : salesOrderRequest.getSalesOrderItems()) {
            log.info(soi.toString());
        }
        return new MessageResponse("alert-success", SuccessMessage.CONTAINER_ADDED);
    }
}
