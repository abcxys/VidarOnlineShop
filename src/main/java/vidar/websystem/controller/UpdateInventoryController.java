package vidar.websystem.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import vidar.websystem.constants.Pages;
import vidar.websystem.constants.PathConstants;
import vidar.websystem.domain.ColorDict;
import vidar.websystem.domain.GradeDict;
import vidar.websystem.domain.SpeciesDict;
import vidar.websystem.domain.SizeDict;
import vidar.websystem.service.InventoryService;

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
	
	@GetMapping
    public String getUpdate(Model model) {
        return Pages.UPDATE_INVENTORY;
    }
	
	@RequestMapping("/add-new-product")
	public String addNewProduct(Model model) {
		List<GradeDict> gradeDict = inventoryService.getGradeDict();
		List<ColorDict> colorDict = inventoryService.getColorDict();
		List<SizeDict> sizeDict = inventoryService.getSizeDict();
		List<SpeciesDict> speciesDict = inventoryService.getSpeciesDict();
		model.addAttribute("gradeDict", gradeDict.stream().map(grade->grade.getGradeName()).collect(Collectors.toList()));
		model.addAttribute("colourDict", colorDict.stream().map(color->color.getColorName()).collect(Collectors.toList()));
		model.addAttribute("sizeDict", sizeDict.stream().map(size->size.getWidthInInch() + " inch x 3/4inch x " + size.getLength()
					+ " " + size.getsqftPerCarton()).collect(Collectors.toList()));
		model.addAttribute("speciesDict", speciesDict.stream().map(species->species.getName()).collect(Collectors.toList()));
		return Pages.ADD_NEW_PRODUCT;
	}
}
