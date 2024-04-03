package vidar.websystem.service;

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

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

	FloorColorSize getProductInfoById(Long productId);
	
	HardwoodFloor getProductById(Long productId);
    
    PlankColor getHardwoodColorById(Long productId);

    List<FloorColorSize> getPopularProducts();
    
    List<PlankColor> getPopularHardwoodFloorColors();

    Page<FloorColorSize> getProductsByFilterParams(SearchRequest searchRequest, Pageable pageable);
    
    Page<FloorColorSize> getProducts(Pageable pageable);

    Page<FloorColorSize> searchProducts(SearchRequest searchRequest, Pageable pageable);
    
    List<PlankColor> getColorDict();
	
	List<PlankSize> getSizeDict();
	
	List<WoodSpecies> getSpeciesDict();
	
	List<PlankType> getPlankTypeDict();
	
	List<Grade> getGradeDict();
    
    String postPlankColor(User user, PlankColor plankColor);
    
    String postPlankSize(User user, PlankSize plankSize);
    
    String postWoodSpecies(User user, WoodSpecies species);
    
    String postPlankType(User user, PlankType plankType);
    
    String postGrade(User user, Grade grade);
    
    MessageResponse addProduct(ProductRequest productRequest, MultipartFile file);
    
    MessageResponse updateProduct(ProductRequest productRequest, MultipartFile file);
}
