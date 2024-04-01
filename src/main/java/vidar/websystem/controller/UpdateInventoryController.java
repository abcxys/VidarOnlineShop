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
import vidar.websystem.domain.Grade;
import vidar.websystem.domain.PlankColor;
import vidar.websystem.domain.PlankSize;
import vidar.websystem.domain.PlankType;
import vidar.websystem.domain.WoodSpecies;
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
	private ProductService productService;
	
	@GetMapping
    public String getUpdate(Model model) {
        return Pages.UPDATE_INVENTORY;
    }
	
	@GetMapping("/add-new-product")
	public String addNewProduct(Model model) {
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
		return Pages.ADD_NEW_PRODUCT;
	}
}
