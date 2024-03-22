package vidar.websystem.service;

import vidar.websystem.domain.FloorColorSize;
import vidar.websystem.domain.HardwoodFloor;
import vidar.websystem.domain.PlankColor;
import vidar.websystem.dto.request.SearchRequest;

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
