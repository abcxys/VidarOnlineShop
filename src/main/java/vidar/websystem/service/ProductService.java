package vidar.websystem.service;

import vidar.websystem.domain.FloorColorSize;
import vidar.websystem.domain.HardwoodFloor;
import vidar.websystem.domain.PlankColor;
import vidar.websystem.domain.PlankSize;
import vidar.websystem.domain.User;
import vidar.websystem.domain.WoodSpecies;
import vidar.websystem.dto.request.SearchRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

	FloorColorSize getProductById(Long productId);
    
    PlankColor getHardwoodColorById(Long productId);

    List<FloorColorSize> getPopularProducts();
    
    List<PlankColor> getPopularHardwoodFloorColors();

    Page<FloorColorSize> getProductsByFilterParams(SearchRequest searchRequest, Pageable pageable);

    Page<FloorColorSize> searchProducts(SearchRequest searchRequest, Pageable pageable);
    
    List<PlankColor> getColorDict();
	
	List<PlankSize> getSizeDict();
	
	List<WoodSpecies> getSpeciesDict();
    
    Long postPlankColor(User user, PlankColor plankColor);
}
