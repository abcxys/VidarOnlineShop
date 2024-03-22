package vidar.websystem.service.impl;

import lombok.RequiredArgsConstructor;
import vidar.websystem.constants.ErrorMessage;
import vidar.websystem.domain.FloorColorSize;
import vidar.websystem.domain.FloorOrder;
import vidar.websystem.domain.HardwoodFloor;
import vidar.websystem.domain.PlankColor;
import vidar.websystem.dto.request.SearchRequest;
import vidar.websystem.repository.FloorOrderRepository;
import vidar.websystem.repository.HardwoodFloorsRepository;
import vidar.websystem.repository.PlankColorRepository;
import vidar.websystem.repository.PlankSizeRepository;
import vidar.websystem.service.ProductService;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final HardwoodFloorsRepository perfumeRepository;
    private final FloorOrderRepository floorOrderRepository;
    private final PlankSizeRepository plankSizeRepository;
    private final PlankColorRepository plankColorRepository;
    private final ModelMapper modelMapper;

    @Override
    public FloorColorSize getProductById(Long perfumeId) {
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
    public List<FloorColorSize> getPopularProducts() {
        List<Long> perfumeIds = Arrays.asList(1L, 2L, 3L, 4L, 5L);
        return perfumeRepository.findFloorColorByIdIn(perfumeIds);
    }

    @Override
    public Page<FloorColorSize> getProductsByFilterParams(SearchRequest request, Pageable pageable) {
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
    public Page<FloorColorSize> searchProducts(SearchRequest request, Pageable pageable) {
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
