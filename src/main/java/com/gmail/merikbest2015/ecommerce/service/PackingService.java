package com.gmail.merikbest2015.ecommerce.service;

import com.gmail.merikbest2015.ecommerce.domain.DatatablesView;
import com.gmail.merikbest2015.ecommerce.domain.Order;

/**
 * @author yishi.xing
 * @created Feb 15, 2024 - 10:47:30 PM
 */
public interface PackingService {
	DatatablesView<Order> getAllOrders();
}
