package com.gmail.merikbest2015.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gmail.merikbest2015.ecommerce.domain.FloorOrder;

/**
 * @author yishi.xing
 * @created Feb 19, 2024 - 11:23:35 PM
 */
public interface FloorOrderRepository extends JpaRepository<FloorOrder, Long> {
	List<FloorOrder> findFloorOrdersByOrderId(Long id);
	List<FloorOrder> findFloorOrdersByHardwoodfloorId(Long id);
}
