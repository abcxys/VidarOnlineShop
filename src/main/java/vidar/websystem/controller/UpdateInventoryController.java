package vidar.websystem.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import vidar.websystem.constants.Pages;
import vidar.websystem.constants.PathConstants;
import vidar.websystem.domain.*;
import vidar.websystem.dto.request.ProductRequest;
import vidar.websystem.service.ProductService;
import vidar.websystem.service.UserService;
import vidar.websystem.utils.ControllerUtils;

/**
 * @author yishi.xing
 * @created Mar 6, 2024 - 11:47:06 PM
 */
@Controller
@RequiredArgsConstructor
@RequestMapping(PathConstants.UPDATE)
@PreAuthorize("hasAuthority('ADMIN')")
public class UpdateInventoryController {
	private final ControllerUtils controllerUtils;
	
	@Autowired
	private final ProductService productService;

	@Autowired
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
	
	@GetMapping("/product/{productId}")
	public String getProduct(@PathVariable Long productId, Model model) {
		model.addAttribute("product", productService.getProductById(productId));
		injectAttributesToModel(model);
		return Pages.UPDATE_PRODUCT;
	}
	
	@GetMapping("/inventory")
	public String getProductInventory(Model model) {
		List<PlankColor> colorDict = productService.getColorDict();
		List<PlankSize> sizeDict = productService.getSizeDict();
		List<WoodSpecies> speciesDict = productService.getSpeciesDict();
		List<Grade> gradeDict = productService.getGradeDict();
		model.addAttribute("colorDict", colorDict);
		model.addAttribute("sizeDict", sizeDict);
		model.addAttribute("speciesDict", speciesDict);
		model.addAttribute("gradeDict", gradeDict);
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
