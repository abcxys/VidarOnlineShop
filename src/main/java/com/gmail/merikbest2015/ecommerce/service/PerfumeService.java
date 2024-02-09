package com.gmail.merikbest2015.ecommerce.service;

import com.gmail.merikbest2015.ecommerce.domain.FloorColorSize;
import com.gmail.merikbest2015.ecommerce.domain.HardwoodFloor;
import com.gmail.merikbest2015.ecommerce.domain.PlankColor;
import com.gmail.merikbest2015.ecommerce.dto.request.SearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PerfumeService {

	FloorColorSize getPerfumeById(Long perfumeId);
    
    PlankColor getHardwoodColorById(Long perfumeId);

    List<FloorColorSize> getPopularPerfumes();
    
    List<PlankColor> getPopularHardwoodFloorColors();

    Page<FloorColorSize> getPerfumesByFilterParams(SearchRequest searchRequest, Pageable pageable);

    Page<HardwoodFloor> searchPerfumes(SearchRequest searchRequest, Pageable pageable);
}
