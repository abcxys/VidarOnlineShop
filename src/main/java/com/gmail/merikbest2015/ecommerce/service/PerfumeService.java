package com.gmail.merikbest2015.ecommerce.service;

import com.gmail.merikbest2015.ecommerce.domain.HardwoodFloor;
import com.gmail.merikbest2015.ecommerce.dto.request.SearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PerfumeService {

    HardwoodFloor getPerfumeById(Long perfumeId);

    List<HardwoodFloor> getPopularPerfumes();

    Page<HardwoodFloor> getPerfumesByFilterParams(SearchRequest searchRequest, Pageable pageable);

    Page<HardwoodFloor> searchPerfumes(SearchRequest searchRequest, Pageable pageable);
}
