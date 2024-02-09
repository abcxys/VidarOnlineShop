package com.gmail.merikbest2015.ecommerce.service.impl;

import com.gmail.merikbest2015.ecommerce.constants.ErrorMessage;
import com.gmail.merikbest2015.ecommerce.domain.FloorColorSize;
import com.gmail.merikbest2015.ecommerce.domain.HardwoodFloor;
import com.gmail.merikbest2015.ecommerce.domain.PlankColor;
import com.gmail.merikbest2015.ecommerce.domain.PlankSize;
import com.gmail.merikbest2015.ecommerce.dto.request.SearchRequest;
import com.gmail.merikbest2015.ecommerce.repository.HardwoodFloorsRepository;
import com.gmail.merikbest2015.ecommerce.repository.PlankColorRepository;
import com.gmail.merikbest2015.ecommerce.repository.PlankSizeRepository;
import com.gmail.merikbest2015.ecommerce.service.PerfumeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PerfumeServiceImpl implements PerfumeService {

    private final HardwoodFloorsRepository perfumeRepository;
    private final PlankSizeRepository plankSizeRepository;
    private final PlankColorRepository plankColorRepository;
    private final ModelMapper modelMapper;

    @Override
    public FloorColorSize getPerfumeById(Long perfumeId) {
    	FloorColorSize result = perfumeRepository.findFloorColorById(perfumeId);
    	if (result == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessage.PERFUME_NOT_FOUND);
        return result;
    }
    
    @Override
    public PlankColor getHardwoodColorById(Long hardwoodId) {
    	return plankColorRepository.findById(perfumeRepository.getPlankColorIdsByIds(Arrays.asList(hardwoodId), null).get(0))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessage.PERFUME_NOT_FOUND));
    }

    @Override
    public List<FloorColorSize> getPopularPerfumes() {
        List<Long> perfumeIds = Arrays.asList(1L, 2L, 3L, 4L, 5L);
//        List<HardwoodFloor> floors = perfumeRepository.findByIdIn(perfumeIds);
//        List<Long> plank_size_ids = perfumeRepository.getPlankSizeIdsByIds(perfumeIds, null);
//        List<PlankSize> plank_sizes = plankSizeRepository.findByIdIn(plank_size_ids);
//        FloorColorSize object = perfumeRepository.findFloorColorById(1L);
//        return perfumeRepository.findByIdIn(perfumeIds);
        return perfumeRepository.findFloorColorByIdIn(perfumeIds);
    }

    @Override
    public Page<FloorColorSize> getPerfumesByFilterParams(SearchRequest request, Pageable pageable) {
        Integer startingPrice = request.getPrice();
        Integer endingPrice = startingPrice + (startingPrice == 0 ? 500 : 50);
        return perfumeRepository.getPerfumesByFilterParams(
                request.getPerfumers(),
                request.getGenders(),
                startingPrice,
                endingPrice,
                pageable);
    }

    @Override
    public Page<HardwoodFloor> searchPerfumes(SearchRequest request, Pageable pageable) {
        return perfumeRepository.searchPerfumes(request.getSearchType(), request.getText(), pageable);
    }

	@Override
	public List<PlankColor> getPopularHardwoodFloorColors() {
		List<Long> perfumeIds = Arrays.asList(1L, 2L, 3L, 4L, 5L);
        List<Long> plank_color_ids = perfumeRepository.getPlankColorIdsByIds(perfumeIds, null);
        return plank_color_ids.stream().map(plank_color_id->
    		plankColorRepository.findById(plank_color_id).get()).collect(Collectors.toList());
	}
}
