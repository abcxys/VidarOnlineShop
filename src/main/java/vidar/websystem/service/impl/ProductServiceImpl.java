package vidar.websystem.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import vidar.websystem.constants.ErrorMessage;
import vidar.websystem.constants.SuccessMessage;
import vidar.websystem.domain.FloorColorSize;
import vidar.websystem.domain.Grade;
import vidar.websystem.domain.HardwoodFloor;
import vidar.websystem.domain.PlankColor;
import vidar.websystem.domain.PlankSize;
import vidar.websystem.domain.PlankType;
import vidar.websystem.domain.User;
import vidar.websystem.domain.WoodSpecies;
import vidar.websystem.dto.request.ProductRequest;
import vidar.websystem.dto.request.SearchRequest;
import vidar.websystem.dto.response.MessageResponse;
import vidar.websystem.repository.SalesOrderProductRepository;
import vidar.websystem.repository.GradeRepository;
import vidar.websystem.repository.HardwoodFloorsRepository;
import vidar.websystem.repository.PlankColorRepository;
import vidar.websystem.repository.PlankSizeRepository;
import vidar.websystem.repository.PlankTypeRepository;
import vidar.websystem.repository.WoodSpeciesRepository;
import vidar.websystem.service.ProductService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
	
	@Value("${upload.path}")
	private String uploadPath;

    private final HardwoodFloorsRepository hardwoodRepository;
    private final SalesOrderProductRepository salesOrderProductRepository;
    private final PlankSizeRepository plankSizeRepository;
    private final PlankColorRepository plankColorRepository;
    private final WoodSpeciesRepository woodSpeciesRepository;
    private final PlankTypeRepository plankTypeRepository;
    private final GradeRepository gradeRepository;
    private final ModelMapper modelMapper;

    @Override
    public FloorColorSize getProductInfoById(Long perfumeId) {
    	FloorColorSize result = hardwoodRepository.findFloorColorById(perfumeId);
    	if (result == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessage.PRODUCT_NOT_FOUND);
        return result;
    }
    
    @Override
    public PlankColor getHardwoodColorById(Long hardwoodId) {
    	return plankColorRepository.findById(hardwoodRepository.getPlankColorIdsByIds(Arrays.asList(hardwoodId), null).get(0))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessage.PRODUCT_NOT_FOUND));
    }

    @Override
    public List<FloorColorSize> getPopularProducts() {
        List<Long> perfumeIds = Arrays.asList(1L, 2L, 3L, 4L, 5L);
        return hardwoodRepository.findFloorColorByIdIn(perfumeIds);
    }

    @Override
    public Page<FloorColorSize> getProductsByFilterParams(SearchRequest request, Pageable pageable) {
        Integer startingPrice = request.getPrice();
        Integer endingPrice = startingPrice + (startingPrice == 0 ? 500 : 50);
        return hardwoodRepository.getPerfumesByFilterParams(
                request.getColours(),
                request.getWidths(),
                startingPrice,
                endingPrice,
                pageable);
    }
    
    @Override
    public Page<FloorColorSize> getProducts(Pageable pageable){
    	return hardwoodRepository.findAllFloorColor(pageable);
    }

	@Override
	public Page<FloorColorSize> getActiveProducts(Pageable pageable){
		return hardwoodRepository.findActiveFloorColor(pageable);
	}

    @Override
    public Page<FloorColorSize> searchProducts(SearchRequest request, Pageable pageable) {
        return hardwoodRepository.searchPerfumes(request.getSearchType(), request.getText(), pageable);
    }

	@Override
	public List<PlankColor> getPopularHardwoodFloorColors() {
		List<Long> perfumeIds = Arrays.asList(1L, 2L, 3L, 4L, 5L);
        List<Long> plank_color_ids = hardwoodRepository.getPlankColorIdsByIds(perfumeIds, null);
        return plank_color_ids.stream().map(plank_color_id->
    		plankColorRepository.findById(plank_color_id).orElse(null)).collect(Collectors.toList());
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

	@Override
	@SneakyThrows
	@Transactional
	public MessageResponse addProduct(User user, ProductRequest productRequest, MultipartFile file) {
		return saveProduct(user, productRequest, file, SuccessMessage.PRODUCT_ADDED);
	}

	@Override
	@SneakyThrows
	@Transactional
	public MessageResponse updateProduct(User user, ProductRequest productRequest, MultipartFile file) {
		return saveProduct(user, productRequest, file, SuccessMessage.PRODUCT_UPDATED);
	}
	
	private MessageResponse saveProduct(User user, ProductRequest productRequest, MultipartFile file, String message) throws IOException {
		HardwoodFloor floor = modelMapper.map(productRequest, HardwoodFloor.class);
		if (floor.getId() == null){
			// adding new product
			// if the floor exists, we will update it and keep the existing file
			List<HardwoodFloor> queriedList = hardwoodRepository.findByColorIdAndSizeIdAndTypeIdAndGradeIdAndSpeciesIdAndBatchNumber(floor.getColorId(),
					floor.getSizeId(),
					floor.getTypeId(),
					floor.getGradeId(),
					floor.getSpeciesId(),
					floor.getBatchNumber());
			if (queriedList.size() > 0) {
				log.info("The queried entry already exists");
				return new MessageResponse("alert-danger", "The product already exists!");
			}
			floor.setCreateTime(new Date());
			floor.setCreateUserId(user.getId());
		} else {
			HardwoodFloor queryById = hardwoodRepository.findById(floor.getId()).orElse(null);
            floor.setFilename(queryById == null ? null : queryById.getFilename());
			floor.setUpdateTime(new Date());
			floor.setUpdateUserId(user.getId());
		}

		if (file != null && file.getOriginalFilename() != null && !file.getOriginalFilename().isEmpty()) {
			File uploadDir = new File(uploadPath);

			if (!uploadDir.exists()) {
				uploadDir.mkdir();
			}
			String uuidFile = UUID.randomUUID().toString();
			String resultFilename = uuidFile + "." + file.getOriginalFilename();
			file.transferTo(new File(uploadPath + "/" + resultFilename));
			floor.setFilename(resultFilename);
		}
		hardwoodRepository.save(floor);
		return new MessageResponse("alert-success", message);
	}

	@Override
	public HardwoodFloor getProductById(Long productId) {
		return hardwoodRepository.findById(productId).orElse(null);
	}
}
