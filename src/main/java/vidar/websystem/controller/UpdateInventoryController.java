package vidar.websystem.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import vidar.websystem.constants.Pages;
import vidar.websystem.constants.PathConstants;
import vidar.websystem.domain.*;
import vidar.websystem.dto.request.ContainerRequest;
import vidar.websystem.dto.request.InventoryItemRequest;
import vidar.websystem.dto.request.ProductRequest;
import vidar.websystem.dto.response.MessageResponse;
import vidar.websystem.service.InventoryService;
import vidar.websystem.service.ProductService;
import vidar.websystem.service.UserService;
import vidar.websystem.utils.ControllerUtils;

/**
 * @author yishi.xing
 * @created Mar 6, 2024 - 11:47:06 PM
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(PathConstants.UPDATE)
@PreAuthorize("hasAuthority('ADMIN')")
public class UpdateInventoryController {
	private final ControllerUtils controllerUtils;
	
	private final ProductService productService;
	private final InventoryService inventoryService;
	private final UserService userService;

	@GetMapping
    public String getUpdate(Model model) {
        return Pages.UPDATE;
    }
	
	@GetMapping("/add-new-product")
	public String addNewProduct(Model model) {
		injectAttributesToModel(model);
		return Pages.ADD_NEW_PRODUCT;
	}
	
	@GetMapping("/products")
	public String getProducts(Pageable pageable, Model model) {
		controllerUtils.addPagination(model, productService.getProducts(pageable));
		return Pages.UPDATE_ALL_PRODUCTS;
	}

	@GetMapping("/add-new-container")
	public String addNewContainer(Pageable pageable, Model model) {
		return Pages.ADD_NEW_CONTAINER;
	}
	
	@GetMapping("/product/{productId}")
	public String getProduct(@PathVariable Long productId, Model model) {
		model.addAttribute("product", productService.getProductById(productId));
		injectAttributesToModel(model);
		return Pages.UPDATE_PRODUCT;
	}
	
	@GetMapping("/inventory")
	public String getProductInventory(Model model) {
		injectAttributesToModel(model);
		return Pages.UPDATE_INVENTORY;
	}
	
	@PostMapping("/product")
	public String updateProduct(@Valid ProductRequest product, BindingResult bindingResult, Model model,
			@RequestParam("file") MultipartFile file, RedirectAttributes attributes) {
		if (controllerUtils.validateInputFields(bindingResult, model, "product", product)) {
			return Pages.UPDATE_PRODUCT;
		}
		User user = userService.getAuthenticatedUser();
		return controllerUtils.setAlertFlashMessage(attributes, "/update", productService.updateProduct(user, product, file));
	}
	
	@PostMapping("/add-new-product")
	public String addNewItem(@Valid ProductRequest product, BindingResult bindingResult, Model model,
			@RequestParam("file") MultipartFile file, RedirectAttributes attributes) {
		if (controllerUtils.validateInputFields(bindingResult, model, "product", product)) {
			injectAttributesToModel(model);
			return Pages.ADD_NEW_PRODUCT;
		}
		User user = userService.getAuthenticatedUser();
		return controllerUtils.setAlertFlashMessage(attributes, "/update", productService.addProduct(user, product, file));
	}

	@PostMapping("/add-new-container")
	public String addNewContainer(@RequestBody ContainerRequest container, BindingResult bindingResult, Model model,
								  RedirectAttributes attributes){
		if (controllerUtils.validateInputFields(bindingResult, model, "container", container)){
			return Pages.ADD_NEW_CONTAINER;
		}
		User user = userService.getAuthenticatedUser();

		return controllerUtils.setAlertFlashMessage(attributes, "/update", inventoryService.addContainer(user, container));
	}

	@PostMapping("/add-new-inventory")
	public String addNewInventory(@Valid InventoryItemRequest inventoryItemRequest, BindingResult bindingResult, Model model,
								  RedirectAttributes attributes){
		/**
		 * The inventory item related html fragment is added dynamically by Jquery function,
		 * thus not processed by Thymeleaf engine. Binding result cannot be reflected back to front end.
		 * Validate InventoryItemRequest manually.
		 */

		//Validate the entered location exists
		String location = inventoryItemRequest.getLocation();
		if (!inventoryService.existsLocationTorontoWarehouse(location))
			return controllerUtils.setAlertFlashMessage(attributes, "/update", new MessageResponse("alert-danger", "Please fill in correct location!"));

		BigDecimal quantity = inventoryItemRequest.getQuantity();
		//Validate the entered quantity
		if (quantity.compareTo(BigDecimal.ZERO) < 0)
			return controllerUtils.setAlertFlashMessage(attributes, "/update", new MessageResponse("alert-danger", "Please fill in correct quantity!"));
		Inventory newInventory = new Inventory();
		newInventory.setFloorId(inventoryItemRequest.getProductId());
		newInventory.setInitialQuantity(quantity);
		newInventory.setCurrentQuantity(quantity);
		newInventory.setLocationId(inventoryService.getLocationIdByBayAndWarehouseId(location, 1L));

		User user = userService.getAuthenticatedUser();
		return controllerUtils.setAlertFlashMessage(attributes,
				"/update",
				inventoryService.addInventory(user, newInventory));
	}

	private void injectAttributesToModel(Model model) {
		List<Grade> gradeDict = productService.getGradeDict();
		List<PlankColor> colorDict = productService.getColorDict();
		List<PlankSize> sizeDict = productService.getSizeDict();
		List<WoodSpecies> speciesDict = productService.getSpeciesDict();
		List<PlankType> plankTypeDict = productService.getPlankTypeDict();
		model.addAttribute("gradeDict", gradeDict);
		model.addAttribute("colourDict", colorDict);
		model.addAttribute("sizeDict", sizeDict);
		model.addAttribute("speciesDict", speciesDict);
		model.addAttribute("plankTypeDict", plankTypeDict);
	}
}
