package vidar.websystem.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import vidar.websystem.constants.Pages;
import vidar.websystem.constants.PathConstants;
import vidar.websystem.dto.request.ColourRequest;
import vidar.websystem.dto.request.SearchRequest;
import vidar.websystem.service.InventoryService;
import vidar.websystem.service.ProductService;
import vidar.websystem.service.UserService;
import vidar.websystem.utils.ControllerUtils;
import vidar.websystem.domain.PlankColor;
import vidar.websystem.domain.WoodSpecies;
import vidar.websystem.domain.User;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.joda.time.LocalDateTime;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(PathConstants.PRODUCT)
public class ProductController {

    private final ProductService productService;
    private final InventoryService inventoryService;
    private final ControllerUtils controllerUtils;
    private final UserService userService;

    @GetMapping("/{productId}")
    public String getPerfumeById(@PathVariable Long productId, Model model) {
        model.addAttribute("product", productService.getProductById(productId));
        model.addAttribute("quantity", inventoryService.getStockByFloorId(productId));
        return Pages.PRODUCT;
    }

    @GetMapping
    public String getPerfumesByFilterParams(SearchRequest request, Model model, Pageable pageable) {
        controllerUtils.addPagination(request, model, productService.getProductsByFilterParams(request, pageable));
        return Pages.PRODUCTS;
    }

    @GetMapping("/search")
    public String searchPerfumes(SearchRequest request, Model model, Pageable pageable) {
        controllerUtils.addPagination(request, model, productService.searchProducts(request, pageable));
        return Pages.PRODUCTS;
    }
    
    @RequestMapping(value="/checkProductStock", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> checkProductStock(@RequestParam("productId") Long productId, @RequestParam("quantity") Long quantity) {
    	log.info("Checking product stock");
    	if (quantity <= 0) {
    		return ResponseEntity.badRequest().body("Enter quantity for stock check");
    	}
    	if (quantity > 658)
    		return ResponseEntity.badRequest().body("Please contact customer service");
    	return ResponseEntity.ok().body("The stock is available on " + new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
    }
    
    @RequestMapping(value="/addNewColour", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> addNewColour(@RequestParam("colourName") String colourName, @RequestParam("colourAlias") String colourAlias,
    		@RequestParam("description") String description){
    	User user = userService.getAuthenticatedUser();
    	PlankColor plankColor = new PlankColor();
    	plankColor.setName(colourName);
    	plankColor.setAlias(colourAlias);
    	plankColor.setDescription(description);
    	productService.postPlankColor(user, plankColor);
    	return ResponseEntity.ok().body("New colour added successfully");
    }
    
    @RequestMapping(value="/addNewSpecies", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> addNewSpecies(@RequestParam("speciesName") String speciesName,
    		@RequestParam("speciesCountry") String speciesCountry,
    		@RequestParam("speciesDescription") String speciesDescription){
    	User user = userService.getAuthenticatedUser();
    	WoodSpecies species = new WoodSpecies();
    	species.setName(speciesName);
    	species.setCountry(speciesCountry);
    	species.setDescription(speciesDescription);
    	return ResponseEntity.ok().body(productService.postWoodSpecies(user, species));
    }
}
