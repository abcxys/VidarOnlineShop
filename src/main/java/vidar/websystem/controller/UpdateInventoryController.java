package vidar.websystem.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import vidar.websystem.constants.Pages;
import vidar.websystem.constants.PathConstants;
import vidar.websystem.domain.GradeDict;
import vidar.websystem.domain.PlankColor;
import vidar.websystem.domain.PlankSize;
import vidar.websystem.domain.WoodSpecies;
import vidar.websystem.service.InventoryService;
import vidar.websystem.service.ProductService;

/**
 * @author yishi.xing
 * @created Mar 6, 2024 - 11:47:06 PM
 */
@Controller
@RequiredArgsConstructor
@RequestMapping(PathConstants.UPDATE)
public class UpdateInventoryController {
	
	@Autowired
	private InventoryService inventoryService;
	
	@Autowired
	private ProductService productService;
	
	@GetMapping
    public String getUpdate(Model model) {
        return Pages.UPDATE_INVENTORY;
    }
	
	@RequestMapping("/add-new-product")
	public String addNewProduct(Model model) {
		List<GradeDict> gradeDict = inventoryService.getGradeDict();
		List<PlankColor> colorDict = productService.getColorDict();
		List<PlankSize> sizeDict = productService.getSizeDict();
		List<WoodSpecies> speciesDict = productService.getSpeciesDict();
		model.addAttribute("gradeDict", gradeDict.stream().map(grade->grade.getGradeName()).collect(Collectors.toList()));
		model.addAttribute("colourDict", colorDict.stream().map(color->color.getName()).collect(Collectors.toList()));
		model.addAttribute("sizeDict", sizeDict.stream().map(size->size.getWidth_in_inch() + " inch x 3/4inch x " + size.getLength()
					+ " " + size.getSquarefoot_per_carton()).collect(Collectors.toList()));
		model.addAttribute("speciesDict", speciesDict.stream().map(species->species.getName()).collect(Collectors.toList()));
		return Pages.ADD_NEW_PRODUCT;
	}
}
