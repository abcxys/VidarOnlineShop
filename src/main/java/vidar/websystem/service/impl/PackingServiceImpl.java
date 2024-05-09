package vidar.websystem.service.impl;

import formbean.SalesOrderFilterConditionForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import vidar.websystem.constants.ErrorMessage;
import vidar.websystem.constants.SuccessMessage;
import vidar.websystem.domain.*;
import vidar.websystem.dto.request.PackingSlipRequest;
import vidar.websystem.repository.*;
import vidar.websystem.service.PackingService;

/**
 * @author yishi.xing
 * created Feb 15, 2024 - 10:50:47 PM
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PackingServiceImpl implements PackingService{

	private final HardwoodFloorsRepository hardwoodFloorsRepository;
	private final PackingSlipRepository packingSlipRepository;
	private final SalesOrderRepository salesOrderRepository;
	private final SalesOrderProductRepository salesOrderProductRepository;
	private final PackingStatusRepository packingStatusRepository;
	private final DriverRepository driverRepository;
	private final PackingSlipItemRepository packingSlipItemRepository;
	private final DealerRepository dealerRepository;

	/**
	 * @param id of PackingSlip
	 * @return PackingSlip object
	 */
	@Override
	public PackingSlip getPackingSlipById(Long id) {
		return packingSlipRepository.findById(id).orElse(null);
	}

	@Override
	public DatatablesView<SalesOrder> getAllOrders() {
		DatatablesView<SalesOrder> dataView = new DatatablesView<>();
		List<SalesOrder> salesOrderList = salesOrderRepository.findAll();
		
		int count = (int) salesOrderRepository.count();
		dataView.setData(salesOrderList);
		dataView.setRecordsTotal(count);
		return dataView;
	}

	/**
	 * @param packingSlipFilterConditionForm filtering conditions (Note form for sales order is reused here, possibly replace with new form)
	 * @return queried results
	 */
	@Override
	public DatatablesView<PackingSlip> getFilteredPackingSlips(SalesOrderFilterConditionForm packingSlipFilterConditionForm) {
		DatatablesView<PackingSlip> dataView = new DatatablesView<>();
		String orderName = "1";; // Initialize order by clause
		String orderType = "desc";
		// if packingSlipFilterConditionForm.getOrderType() is null, set default sorting.
		if (packingSlipFilterConditionForm.getOrderName() != null){
			orderName = packingSlipFilterConditionForm.getOrderName();
			orderType = packingSlipFilterConditionForm.getOrderType();
		}
		List<Long> statusIds = packingSlipFilterConditionForm.getStatusIdsString().equals("") ? Arrays.asList(1L, 2L, 3L, 4L) :
				Arrays.stream(packingSlipFilterConditionForm.getStatusIdsString().split(","))
						.map(Long::parseLong)
						.collect(Collectors.toList());

		// Create a Pageable object with sorting based on orderName and orderType
		Sort sort = Sort.by(
				orderType.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC,
				orderName.equalsIgnoreCase("1") ? "id" : "packingStatus"
		);
		Pageable pageable = PageRequest.of(packingSlipFilterConditionForm.getStartPos(), packingSlipFilterConditionForm.getPageSize(), sort);

		Date startDate = packingSlipFilterConditionForm.getStartDate();
		Date endDate = packingSlipFilterConditionForm.getEndDate();
		if (startDate == null) startDate = new Date(0);
		if (endDate == null) endDate = new Date();

		List<PackingSlip> packingSlipList = new ArrayList<>();
		if (packingSlipFilterConditionForm.getDealerId() == null){
			packingSlipList = packingSlipRepository.findAllByPackingStatusIdInAndCreateTimeBetween(
					statusIds,
					SalesOrderServiceImpl.getBeginOfDate(startDate, true),
					SalesOrderServiceImpl.getBeginOfDate(endDate, false),
					pageable
			).getContent();
		} else {
			packingSlipList = packingSlipRepository.findAllByDealerIdAndPackingStatusIdInAndCreateTimeBetween(
					packingSlipFilterConditionForm.getDealerId(),
					statusIds,
					SalesOrderServiceImpl.getBeginOfDate(startDate, true),
					SalesOrderServiceImpl.getBeginOfDate(endDate, false),
					pageable
			).getContent();
		}

		dataView.setData(packingSlipList);
		dataView.setRecordsTotal(packingSlipList.size());
		return dataView;
	}

	/**
	 * @return All drivers in database.
	 */
	@Override
	public List<Driver> getDrivers() {
		return driverRepository.findAll();
	}

	/**
	 * @param user Authenticated user.
	 * @param packingSlipRequest packingSlipRequest contains driver info and packingSlipItems.
	 * @return ResponseEntity
	 */
	@Transactional
	@Override
	public ResponseEntity<?> addPackingSlip(User user, PackingSlipRequest packingSlipRequest) {
		// Create packing slip with status 'created'
		PackingSlip packingSlip = new PackingSlip();
		packingSlip.setPackingStatus(packingStatusRepository.findById(packingSlipRequest.getStatusId()).orElse(null));
		packingSlip.setDealer(dealerRepository.findByCompanyName(packingSlipRequest.getDealerCompanyName()));
		packingSlip.setDriver(driverRepository.findById(packingSlipRequest.getDriverId()).orElse(null));
		packingSlip.setDescription(packingSlipRequest.getDescription());
		packingSlip.setCreateUserId(user.getId());
		packingSlip.setCreateTime(new Date());
		try {
			packingSlipRepository.save(packingSlip);
			packingSlipRepository.flush();
			log.info("The inserted packing slip returns id = " + packingSlip.getId());

			insertSalesOrderPackingItems(user, packingSlip, packingSlipRequest);
		} catch (Exception e){
			return ResponseEntity.badRequest().body(ErrorMessage.PACKING_SLIP_CREATED_FAILED);
		}
		return ResponseEntity.ok().body(SuccessMessage.PACKING_SLIP_CREATED);
	}

	@Transactional
	@Override
	public ResponseEntity<?> updatePackingSlip(User user, PackingSlipRequest packingSlipRequest) {
		PackingSlip packingSlip = packingSlipRepository.findById(packingSlipRequest.getId()).orElse(null);
		if (packingSlip == null) {
			return ResponseEntity.notFound().build();
		}
		packingSlip.setPackingStatus(packingStatusRepository.findById(packingSlipRequest.getStatusId()).orElse(null));
		//packingSlip.setDealer(dealerRepository.findByCompanyName(packingSlipRequest.getDealerCompanyName()));
		packingSlip.setUpdateUserId(user.getId());
		packingSlip.setUpdateTime(new Date());
		try {
			packingSlipRepository.save(packingSlip);
			packingSlipRepository.flush();
			log.info("The inserted packing slip returns id = " + packingSlip.getId());

			// Do we need to update the PackingSlip items??
			updateSalesOrderPackingItems(user, packingSlipRequest);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(ErrorMessage.PACKING_SLIP_UPDATED_FAILED);
		}
		return ResponseEntity.ok().body(SuccessMessage.SALES_ORDER_CREATED);
	}

	@Transactional
	@Override
	public void insertSalesOrderPackingItems(User user, PackingSlip packingSlip, PackingSlipRequest packingSlipRequest) {
		// Insert packingSlipItems for packingSlipRequest
		for (PackingSlipItem item : packingSlipRequest.getPackingSlipItems()) {
			item.setPackingSlipId(packingSlip.getId());
			item.setCreateTime(new Date());
			item.setCreateUserId(user.getId());
			packingSlipItemRepository.save(item);
		}
	}

	public void updateSalesOrderPackingItems(User user, PackingSlipRequest packingSlipRequest) {
		for (PackingSlipItem item : packingSlipRequest.getPackingSlipItems()) {
			PackingSlipItem originalItem = packingSlipItemRepository.findById(item.getId()).orElse(null);
			if (originalItem != null) {
				originalItem.setQuantity(item.getQuantity());
				originalItem.setUpdateTime(new Date());
				originalItem.setUpdateUserId(user.getId());
				packingSlipItemRepository.save(originalItem);
			}
		}
	}

	/**
	 * @return All entries of packing_status table.
	 */
	@Override
	public List<PackingStatus> getPackingSlipStatusDict() {
		return packingStatusRepository.findAll();
	}

	/**
	 * @param packingSlipId queried packingSlipId
	 * @return DatatablesView of packingItems
	 */
	@Override
	public DatatablesView<SalesOrderItem> getSalesOrderPackingItemsTableByPackingSlipId(Long packingSlipId) {
		DatatablesView<SalesOrderItem> dataView = new DatatablesView<>();
		List<SalesOrderItem> packingItems = getSalesOrderPackingItemsByPackingSlipId(packingSlipId);
		dataView.setData(packingItems);
		dataView.setRecordsTotal(packingItems.size());
		return dataView;
	}

	/**
	 * @param packingSlipId packing slip id
	 * @return List of Sales order item that includes product info and packing quantity
	 */
	@Override
	public List<SalesOrderItem> getSalesOrderPackingItemsByPackingSlipId(Long packingSlipId) {
		List<PackingSlipItem> packingSlipRawItems = packingSlipItemRepository.findByPackingSlipId(packingSlipId);
        return packingSlipRawItems.stream().map(rawItem -> {
			FloorColorSize floorColorSize = hardwoodFloorsRepository.findFloorColorById(
					Objects.requireNonNull(salesOrderProductRepository.findById(rawItem.getSoProductId()).orElse(null)).getHardwoodfloorId());
			return new SalesOrderItem(rawItem.getId(), floorColorSize, rawItem.getQuantity());
		}).collect(Collectors.toList());
	}

	/**
	 * @param user Authenticated user
	 * @param packingSlip PackingSlip object to be updated
	 * @param statusId id of PackingStatus
	 * @return Success/Failure message
	 */
	@Override
	public ResponseEntity<?> updatePackingSlipStatus(User user, PackingSlip packingSlip, Long statusId) {
		packingSlip.setUpdateTime(new Date());
		packingSlip.setUpdateUserId(user.getId());
		PackingStatus newStatus = packingStatusRepository.findById(statusId).orElse(null);
		if (newStatus == null)
			return ResponseEntity.badRequest().body(ErrorMessage.PACKING_SLIP_UPDATED_FAILED);
		packingSlip.setPackingStatus(newStatus);
		packingSlipRepository.save(packingSlip);
		return ResponseEntity.ok().body(SuccessMessage.PACKING_SLIP_UPDATED);
	}
}
