package vidar.websystem.service.impl;

import formbean.SalesOrderFilterConditionForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vidar.websystem.constants.ErrorMessage;
import vidar.websystem.constants.SuccessMessage;
import vidar.websystem.domain.*;
import vidar.websystem.dto.request.ReturnSlipRequest;
import vidar.websystem.dto.request.SalesOrderItemRequest;
import vidar.websystem.repository.*;
import vidar.websystem.service.ReturnService;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yishi.xing
 * create datetime 5/10/2024 8:44 AM
 * description
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ReturnServiceImpl implements ReturnService {

    private final ReturnSlipRepository returnSlipRepository;
    private final ReturnStatusRepository returnStatusRepository;
    private final ReturnItemRepository returnItemRepository;
    private final PackingSlipRepository packingSlipRepository;
    private final HardwoodFloorsRepository hardwoodFloorsRepository;

    /**
     * @return dictionary of return status
     */
    @Override
    public List<ReturnStatus> getReturnStatusDict() {
        return returnStatusRepository.findAll();
    }

    /**
     * @param returnSlipId queried returnSlipId
     * @return DatatablesView of returnItems
     */
    @Override
    public List<SalesOrderItem> getSalesOrderReturnItemsByReturnSlipId(Long returnSlipId) {
        List<ReturnItem> returnRawItems = returnItemRepository.findByReturnSlipId(returnSlipId);
        return returnRawItems.stream().map(rawItem -> {
            FloorColorSize floorColorSize = hardwoodFloorsRepository.findFloorColorById(rawItem.getProduct().getId());
            return new SalesOrderItem(rawItem.getId(), floorColorSize, rawItem.getQuantity());
        }).collect(Collectors.toList());
    }

    /**
     * @param user Authenticated user
     * @param returnSlipRequest returnSlipRequest contains packingSlipId, status and returnItems info
     * @return ResponseEntity
     */
    @Transactional
    @Override
    public ResponseEntity<?> addReturnSlip(User user, ReturnSlipRequest returnSlipRequest) {
        // Create return slip with status 'created'
        ReturnSlip returnSlip = new ReturnSlip();
        returnSlip.setReturnStatus(returnStatusRepository.findById(returnSlipRequest.getReturnStatusId()).orElse(null));
        returnSlip.setPackingSlip(packingSlipRepository.findById(returnSlipRequest.getPackingSlipId()).orElse(null));
        returnSlip.setDescription(returnSlipRequest.getDescription());
        returnSlip.setCreateUserId(user.getId());
        returnSlip.setCreateTime(new Date());
        try{
            returnSlipRepository.save(returnSlip);
            returnSlipRepository.flush();
            log.info("The inserted return slip returns id = " + returnSlip.getId());

            insertReturnItems(user, returnSlip, returnSlipRequest);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(ErrorMessage.RETURN_SLIP_CREATED_FAILED);
        }
        return ResponseEntity.ok().body(SuccessMessage.RETURN_SLIP_CREATED);
    }

    /**
     * @param returnSlipFilterConditionForm filtering conditions (dealer, return status)
     * @return queried results
     */
    @Override
    public DatatablesView<ReturnSlip> getFilteredReturnSlips(SalesOrderFilterConditionForm returnSlipFilterConditionForm) {
        DatatablesView<ReturnSlip> dataView = new DatatablesView<>();

        List<Long> statusIds = returnSlipFilterConditionForm.getStatusIdsString().equals("") ? Arrays.asList(1L, 2L) :
                Arrays.stream(returnSlipFilterConditionForm.getStatusIdsString().split(","))
                        .map(Long::parseLong)
                        .collect(Collectors.toList());

        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(returnSlipFilterConditionForm.getStartPos(),
                returnSlipFilterConditionForm.getPageSize(), sort);

        Date startDate = returnSlipFilterConditionForm.getStartDate();
        Date endDate = returnSlipFilterConditionForm.getEndDate();
        if (startDate == null) startDate = new Date(0);
        if (endDate == null) endDate = new Date();

        List<ReturnSlip> returnSlipList;
        if (returnSlipFilterConditionForm.getDealerId() == null) {
            returnSlipList = returnSlipRepository.findAllByReturnStatusIdInAndCreateTimeBetween(
                statusIds,
                SalesOrderServiceImpl.getBeginOfDate(startDate, true),
                SalesOrderServiceImpl.getBeginOfDate(endDate, false),
                pageable
            ).getContent();
        } else {
            returnSlipList = returnSlipRepository.findAllByDealerIdAndReturnStatusIdInAndCreateTimeBetween(
                returnSlipFilterConditionForm.getDealerId(),
                statusIds,
                SalesOrderServiceImpl.getBeginOfDate(startDate, true),
                SalesOrderServiceImpl.getBeginOfDate(endDate, false),
                pageable
            ).getContent();
        }

        dataView.setData(returnSlipList);
        dataView.setRecordsTotal(returnSlipList.size());
        return dataView;
    }

    public void insertReturnItems(User user, ReturnSlip returnSlip, ReturnSlipRequest returnSlipRequest) {
        // Insert salesOrderItemRequest as ReturnItem
        for (SalesOrderItemRequest item : returnSlipRequest.getReturnItems()){
            ReturnItem returnItem = new ReturnItem();
            returnItem.setReturnSlip(returnSlip);
            returnItem.setProduct(hardwoodFloorsRepository.findById(item.getProductId()).orElse(null));
            returnItem.setQuantity(item.getQuantity());
            returnItem.setCreateTime(new Date());
            returnItem.setCreateUserId(user.getId());
            returnItemRepository.save(returnItem);
        }
    }
}
