package com.gmail.merikbest2015.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gmail.merikbest2015.ecommerce.domain.PlankSize;

/**
 * @author yishi.xing
 * @created Feb 2, 2024 - 10:36:40 PM
 */
public interface PlankSizeRepository extends JpaRepository<PlankSize, Long> {
	List<PlankSize> findByIdIn(List<Long> plank_size_ids);
}
