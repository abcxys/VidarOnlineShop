package vidar.websystem.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import formbean.SalesOrderFilterConditionForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private final WarehouseRepository warehouseRepository;
    private final InventoryRepository inventoryRepository;

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

        List<Long> statusIds = salesOrderFilterConditionForm.getStatusIdsString().equals("") ? null :
                Arrays.stream(salesOrderFilterConditionForm.getStatusIdsString().split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());

        List<SalesOrder> salesOrderList = salesOrderRepository.findFilteredPackableSalesOrders(
                salesOrderFilterConditionForm.getDealerId(),
                statusIds,
                getBeginOfDate(salesOrderFilterConditionForm.getStartDate(), true),
                getBeginOfDate(salesOrderFilterConditionForm.getEndDate(), false)
        );
        dataView.setData(salesOrderList);
        dataView.setRecordsTotal(salesOrderList.size());
        return dataView;
    }

    private Date getBeginOfDate(Date date, boolean isBegin){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, isBegin ? 0 : 23);
        calendar.set(Calendar.MINUTE, isBegin ? 0 : 59);
        calendar.set(Calendar.SECOND, isBegin ? 0 : 59);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * @param salesOrderId salesOrderId field of SalesOrderProduct class
     * @return Datatable view of the sales order products.
     */
    @Override
    public DatatablesView<SalesOrderItem> getSalesOrderProductsBySOId(Long salesOrderId) {
        DatatablesView<SalesOrderItem> dataView = new DatatablesView<>();
        List<SalesOrderProduct> salesOrderProducts = salesOrderProductRepository.findBySalesOrderId(salesOrderId);
        List<SalesOrderItem> salesOrderItems = salesOrderProducts.stream().filter(SalesOrderProduct::isActive).map(item -> {
            FloorColorSize floorColorSize = hardwoodFloorsRepository.findFloorColorById(item.getHardwoodfloorId());
            return new SalesOrderItem(floorColorSize, item.getQuantityOrdered());
        }).collect(Collectors.toList());
        dataView.setData(salesOrderItems);
        dataView.setRecordsTotal(salesOrderItems.size());
        return dataView;
    }

    /**
     * @param ids sales order id's to create packing slip
     * @return table of sales order items
     */
    @Override
    public DatatablesView<SalesOrderItem> getSalesOrderProductsBySOIdsIn(List<Long> ids) {
        DatatablesView<SalesOrderItem> dataView = new DatatablesView<>();
        List<SalesOrderProduct> salesOrderProducts = salesOrderProductRepository.findBySalesOrderIdIn(ids);
        List<SalesOrderItem> salesOrderItems = salesOrderProducts.stream().filter(SalesOrderProduct::isActive).map(item -> {
            SalesOrderItem salesOrderItem = new SalesOrderItem();
            salesOrderItem.setId(item.getId());
            FloorColorSize floorColorSize = hardwoodFloorsRepository.findFloorColorById(item.getHardwoodfloorId());
            salesOrderItem.setFloorColorSize(floorColorSize);
            salesOrderItem.setQuantity(item.getQuantityOrdered());
            salesOrderItem.setQuantity_picked_up(item.getQuantityPickedUp());
            SalesOrder salesOrder = salesOrderRepository.findById(item.getSalesOrderId()).orElse(null);
            if (salesOrder != null) {
                salesOrderItem.setSoDate(salesOrder.getDate());
                salesOrderItem.setSoNumber(salesOrder.getSoNumber());
            }
            return salesOrderItem;
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
    @Transactional
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
        salesOrder.setWarehouse(warehouseRepository.findById(salesOrderRequest.getWarehouseId()).orElse(null));
        salesOrder.setCreateTime(new Date());
        salesOrder.setCreateUserId(user.getId());
        try {
            salesOrderRepository.save(salesOrder);
            salesOrderRepository.flush();
            log.info("The inserted sales order returns id = " + salesOrder.getId());

            insertSalesOrderItems(user, salesOrder, salesOrderRequest);

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

    @Transactional
    @Override
    public void insertSalesOrderItems(User user, SalesOrder salesOrder, SalesOrderRequest salesOrderRequest){
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
    }

    /**
     * @param user Authenticated user
     * @param salesOrderRequest salesOrderRequest from client-side to be updated
     * @return Success/Failure message
     */
    @Transactional
    @Override
    public ResponseEntity<?> updateSalesOrder(User user, SalesOrderRequest salesOrderRequest) {
        SalesOrder salesOrder = salesOrderRepository.findById(salesOrderRequest.getId()).orElse(null);
        //salesOrderProductRepository.deleteBySalesOrderId(salesOrderRequest.getId());
        deactivateSalesOrderProductsBySoId(salesOrderRequest.getId(), user);
        insertSalesOrderItems(user, salesOrder, salesOrderRequest);
        assert salesOrder != null;
        Dealer dealer = dealerRepository.findById(salesOrderRequest.getDealerId()).orElse(null);
        salesOrder.setDate(salesOrderRequest.getDate());
        salesOrder.setDateWanted(salesOrderRequest.getDateWanted());
        salesOrder.setSalesRep(salesRepRepository.findById(salesOrderRequest.getSalesRepId()).orElse(null));
        salesOrder.setWarehouse(warehouseRepository.findById(salesOrderRequest.getWarehouseId()).orElse(null));
        salesOrder.setStatus(salesOrderStatusRepository.findById(salesOrderRequest.getStatusId()).orElse(null));
        salesOrder.setReleaseOk(salesOrderRequest.isReleaseOk());
        salesOrder.setDealer(dealer);
        salesOrder.setUpdateTime(new Date());
        salesOrder.setUpdateUserId(user.getId());
        salesOrderRepository.save(salesOrder);
        return ResponseEntity.ok().body(SuccessMessage.SALES_ORDER_UPDATED);
    }

    @Transactional
    @Override
    public void deactivateSalesOrderProductsBySoId(Long id, User user){
        salesOrderProductRepository.setInActiveForSoId(id, user.getId());
    }

    /**
     * @return order status dictionary
     */
    @Override
    public List<SalesOrderStatus> getSalesOrderStatusDict() {
        return salesOrderStatusRepository.findAll();
    }

    @Override
    public List<Map<String, Object>> getBooleanOptions() {
        List<Map<String, Object>> options = new ArrayList<>();

        Map<String, Object> trueOption = new HashMap<>();
        trueOption.put("key", 1);
        trueOption.put("value", "True");
        options.add(trueOption);

        Map<String, Object> falseOption = new HashMap<>();
        falseOption.put("key", 0);
        falseOption.put("value", "False");
        options.add(falseOption);

        return options;
    }
}