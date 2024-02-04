package com.gmail.merikbest2015.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gmail.merikbest2015.ecommerce.domain.PlankColor;

/**
 * @author yishi.xing
 * @created Feb 3, 2024 - 10:26:19 PM
 */
public interface PlankColorRepository extends JpaRepository<PlankColor, Long> {
	List<PlankColor> findByIdIn(List<Long> plank_color_ids);
}
