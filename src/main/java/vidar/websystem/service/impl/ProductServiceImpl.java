package vidar.websystem.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import vidar.websystem.constants.ErrorMessage;
import vidar.websystem.domain.FloorColorSize;
import vidar.websystem.domain.Grade;
import vidar.websystem.domain.PlankColor;
import vidar.websystem.domain.PlankSize;
import vidar.websystem.domain.PlankType;
import vidar.websystem.domain.User;
import vidar.websystem.domain.WoodSpecies;
import vidar.websystem.dto.request.SearchRequest;
import vidar.websystem.repository.FloorOrderRepository;
import vidar.websystem.repository.GradeRepository;
import vidar.websystem.repository.HardwoodFloorsRepository;
import vidar.websystem.repository.PlankColorRepository;
import vidar.websystem.repository.PlankSizeRepository;
import vidar.websystem.repository.PlankTypeRepository;
import vidar.websystem.repository.WoodSpeciesRepository;
import vidar.websystem.service.ProductService;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final HardwoodFloorsRepository perfumeRepository;
    private final FloorOrderRepository floorOrderRepository;
    private final PlankSizeRepository plankSizeRepository;
    private final PlankColorRepository plankColorRepository;
    private final WoodSpeciesRepository woodSpeciesRepository;
    private final PlankTypeRepository plankTypeRepository;
    private final GradeRepository gradeRepository;
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
	
	@Override
	public List<PlankColor> getColorDict() {
		return plankColorRepository.findAll();
	}

	@Override
	public List<PlankSize> getSizeDict() {
		return plankSizeRepository.findAll();
	}

	@Override
	public List<WoodSpecies> getSpeciesDict() {
		return woodSpeciesRepository.findAll();
	}

	@Override
	public List<PlankType> getPlankTypeDict() {
		return plankTypeRepository.findAll();
	}
	
	@Override
	public List<Grade> getGradeDict(){
		return gradeRepository.findAll();
	}
	
	@Override
	@Transactional
	public String postPlankColor(User user, PlankColor plankColor) {
		PlankColor queryByName = plankColorRepository.findOneByName(plankColor.getName());
		if (queryByName!=null) {
			log.info("Color name duplicated, update entry...");
			queryByName.setName(plankColor.getName());
			queryByName.setAlias(plankColor.getAlias());
			queryByName.setDescription(plankColor.getDescription());
			queryByName.setUpdate_time(new Date());
			queryByName.setUpdate_user_id(user.getId());
			return "Colour updated successfully";
		}
		plankColor.setCreate_user_id(user.getId());
		plankColor.setCreate_time(new Date());
		plankColorRepository.save(plankColor);
		return "New colour added successfully";
	}
	
	@Override
	@Transactional
	public String postPlankSize(User user, PlankSize size) {
		String width = size.getWidthInInch();
		String length = size.getLength();
		String thickness = size.getThicknessInInch();
		BigDecimal sqft = size.getSquarefootPerCarton();
		String description = size.getDescription();
		PlankSize queryByMultiple = plankSizeRepository.findByWidthInInchAndLengthAndThicknessInInchAndSquarefootPerCarton(width,
				length, 
				thickness, 
				sqft);
		// if the plank size does not exist already
		if (queryByMultiple!=null) {
			log.info("Plank size duplicated, update entry...");
			queryByMultiple.setWidthInInch(width);
			queryByMultiple.setLength(length);
			queryByMultiple.setThicknessInInch(thickness);
			queryByMultiple.setSquarefootPerCarton(sqft);
			queryByMultiple.setDescription(description);
			queryByMultiple.setUpdate_time(new Date());
			queryByMultiple.setUpdate_user_id(user.getId());
			plankSizeRepository.save(queryByMultiple);
			return "Plank size updated successfully";
		}
		size.setCreate_time(new Date());
		size.setCreate_user_id(user.getId());
		plankSizeRepository.save(size);
		return "New plank size added successfully";
	}

	@Override
	@Transactional
	public String postWoodSpecies(User user, WoodSpecies species) {
		WoodSpecies queryByName = woodSpeciesRepository.findOneByName(species.getName());
		// if the species name does not exist already
		if (queryByName!=null) {
			log.info("Species name duplicated, update entry...");
			queryByName.setName(species.getName());
			queryByName.setCountry(species.getCountry());
			queryByName.setDescription(species.getDescription());
			queryByName.setUpdate_time(new Date());
			queryByName.setUpdate_user_id(user.getId());
			woodSpeciesRepository.save(queryByName);
			return "Species updated successfully";
		}
		species.setCreate_user_id(user.getId());
		species.setCreate_time(new Date());
		woodSpeciesRepository.save(species);
		return "New species added successfully";
	}
	
	@Override
	@Transactional
	public String postPlankType(User user, PlankType plankType) {
		PlankType queryByName = plankTypeRepository.findOneByName(plankType.getName());
		// if the plankType name does not exist
		if (queryByName != null) {
			log.info("Plank type duplicated, update entry...");
			queryByName.setName(plankType.getName());
			queryByName.setAlias(plankType.getAlias());
			queryByName.setDescription(plankType.getDescription());
			queryByName.setUpdate_time(new Date());
			queryByName.setUpdate_user_id(user.getId());
			plankTypeRepository.save(queryByName);
			return "Plank type updated successfully";
		}
		plankType.setCreate_user_id(user.getId());
		plankType.setCreate_time(new Date());
		plankTypeRepository.save(plankType);
		return "New plank type added successfully";
	}
	
	@Override
	@Transactional
	public String postGrade(User user, Grade grade) {
		Grade queryByName = gradeRepository.findOneByName(grade.getName());
		if (queryByName != null) {
			log.info("Plank type duplicated, update entry...");
			queryByName.setName(grade.getName());
			queryByName.setAlias(grade.getAlias());
			queryByName.setDescription(grade.getDescription());
			queryByName.setUpdate_time(new Date());
			queryByName.setUpdate_user_id(user.getId());
			gradeRepository.save(queryByName);
			return "Grade updated successfully";
		}
		grade.setCreate_time(new Date());
		grade.setCreate_user_id(user.getId());
		gradeRepository.save(grade);
		return "New grade added successfully";
	}
}
