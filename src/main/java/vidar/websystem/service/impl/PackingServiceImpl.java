package vidar.websystem.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import vidar.websystem.domain.DatatablesView;
import vidar.websystem.domain.Order;
import vidar.websystem.repository.OrderRepository;
import vidar.websystem.service.PackingService;

/**
 * @author yishi.xing
 * @created Feb 15, 2024 - 10:50:47 PM
 */
@Service
@RequiredArgsConstructor
public class PackingServiceImpl implements PackingService{
	
	private final OrderRepository orderRepository;

	@Override
	public DatatablesView<Order> getAllOrders() {
		DatatablesView<Order> dataView = new DatatablesView<Order>();
		List<Order> orderList = orderRepository.findAll();
		
		int count = (int) orderRepository.count();
		dataView.setData(orderList);
		dataView.setRecordsTotal(count);
		return dataView;
	}

}
