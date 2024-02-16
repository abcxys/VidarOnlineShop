package com.gmail.merikbest2015.ecommerce.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.gmail.merikbest2015.ecommerce.domain.DatatablesView;
import com.gmail.merikbest2015.ecommerce.domain.Order;
import com.gmail.merikbest2015.ecommerce.repository.OrderRepository;
import com.gmail.merikbest2015.ecommerce.service.PackingService;
import java.util.List;

import lombok.RequiredArgsConstructor;

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
