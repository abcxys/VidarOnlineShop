package vidar.websystem.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import formbean.SalesOrderFilterConditionForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import vidar.websystem.constants.ErrorMessage;
import vidar.websystem.constants.SuccessMessage;
import vidar.websystem.domain.*;
import vidar.websystem.dto.request.SalesOrderItemRequest;
import vidar.websystem.dto.request.SalesOrderRequest;
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
    private final SalesOrderStatusRepository salesOrderStatusRepository;
    private final HardwoodFloorsRepository hardwoodFloorsRepository;

    @Override
    public SalesOrder getSalesOrder(Long salesOrderId){
        return salesOrderRepository.findById(salesOrderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessage.ORDER_NOT_FOUND));
    }

    /**
     * @param salesOrderFilterConditionForm filtering conditions
     * @return queried results
     */
    @Override
    public DatatablesView<SalesOrder> getFilteredSalesOrders(SalesOrderFilterConditionForm salesOrderFilterConditionForm) {
        DatatablesView<SalesOrder> dataView = new DatatablesView<>();
        List<SalesOrder> salesOrderList = salesOrderRepository.findFilteredSalesOrders(
                salesOrderFilterConditionForm.getDealerId(),
                salesOrderFilterConditionForm.getStartDate(),
                salesOrderFilterConditionForm.getEndDate()
        );
        dataView.setData(salesOrderList);
        dataView.setRecordsTotal(salesOrderList.size());
        return dataView;
    }

    /**
     * @param salesOrderId salesOrderId field of SalesOrderProduct class
     * @return Datatable view of the sales order products.
     */
    @Override
    public DatatablesView<SalesOrderItem> getSalesOrderProductsBySOId(Long salesOrderId) {
        DatatablesView<SalesOrderItem> dataView = new DatatablesView<>();
        List<SalesOrderProduct> salesOrderProducts = salesOrderProductRepository.findBySalesOrderId(salesOrderId);
        List<SalesOrderItem> salesOrderItems = salesOrderProducts.stream().map(item -> {
            FloorColorSize floorColorSize = hardwoodFloorsRepository.findFloorColorById(item.getHardwoodfloorId());
            return new SalesOrderItem(floorColorSize, item.getQuantityOrdered());
        }).collect(Collectors.toList());
        dataView.setData(salesOrderItems);
        dataView.setRecordsTotal(salesOrderItems.size());
        return dataView;
    }

    /**
     * @param user Authenticated user
     * @param salesOrderRequest SalesOrderRequest from client-side
     * @return Success/Failure message
     */
    @Override
    public ResponseEntity<?> addSalesOrder(User user, SalesOrderRequest salesOrderRequest) {
        SalesOrder salesOrder = modelMapper.map(salesOrderRequest, SalesOrder.class);
        // Set city as the salesOrderRequest's address attribute, just for now.
        salesOrder.setCity(salesOrderRequest.getAddress());
        salesOrder.setUser(user);
        Dealer dealer = dealerRepository.findById(salesOrderRequest.getDealerId()).orElse(null);
        salesOrder.setDealer(dealer);
        salesOrder.setFirstName(dealer == null ? null : dealer.getCompanyName());
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
            //return new MessageResponse("alert-danger", ErrorMessage.SALES_ORDER_CREATED_FAILED);
            return ResponseEntity.badRequest().body(ErrorMessage.SALES_ORDER_CREATED_FAILED);
        }

        //return new MessageResponse("alert-success", SuccessMessage.SALES_ORDER_CREATED);
        return ResponseEntity.ok().body(SuccessMessage.SALES_ORDER_CREATED);
    }

    /**
     * @return order status dictionary
     */
    @Override
    public List<SalesOrderStatus> getSalesOrderStatusDict() {
        return salesOrderStatusRepository.findAll();
    }
}
