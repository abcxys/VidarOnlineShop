package vidar.websystem.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import vidar.websystem.constants.ErrorMessage;
import vidar.websystem.constants.SuccessMessage;
import vidar.websystem.domain.*;
import vidar.websystem.dto.request.PackingSlipRequest;
import vidar.websystem.repository.DriverRepository;
import vidar.websystem.repository.PackingSlipRepository;
import vidar.websystem.repository.PackingStatusRepository;
import vidar.websystem.repository.SalesOrderRepository;
import vidar.websystem.service.PackingService;

/**
 * @author yishi.xing
 * @created Feb 15, 2024 - 10:50:47 PM
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PackingServiceImpl implements PackingService{

	private final PackingSlipRepository packingSlipRepository;
	private final SalesOrderRepository salesOrderRepository;
	private final PackingStatusRepository packingStatusRepository;
	private final DriverRepository driverRepository;

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
		packingSlip.setPackingStatus(packingStatusRepository.findById(1L));
		packingSlip.setDriver(driverRepository.findById(packingSlipRequest.getDriverId()).orElse(null));
		packingSlip.setCreateUserId(user.getId());
		packingSlip.setCreateTime(new Date());
		try {
			packingSlipRepository.save(packingSlip);
			packingSlipRepository.flush();
			log.info("The inserted packing slip returns id = " + packingSlip.getId());
		} catch (Exception e){
			return ResponseEntity.badRequest().body(ErrorMessage.PACKING_SLIP_CREATED_FAILED);
		}
		return ResponseEntity.ok().body(SuccessMessage.PACKING_SLIP_CREATED);
	}

}
