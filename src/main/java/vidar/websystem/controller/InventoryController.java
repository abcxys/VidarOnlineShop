package vidar.websystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

import formbean.InventoryFilterConditionForm;
import lombok.RequiredArgsConstructor;
import net.sf.json.JSONObject;
import vidar.websystem.constants.Pages;
import vidar.websystem.constants.PathConstants;
import vidar.websystem.domain.DatatablesView;
import vidar.websystem.domain.Grade;
import vidar.websystem.domain.ProductInventoryItem;
import vidar.websystem.domain.PlankColor;
import vidar.websystem.domain.PlankSize;
import vidar.websystem.domain.WoodSpecies;
import vidar.websystem.service.InventoryService;
import vidar.websystem.service.ProductService;

/**
 * @author yishi.xing
 * @created Feb 20, 2024 - 10:33:26 PM
 */
@Controller
@RequiredArgsConstructor
@RequestMapping(PathConstants.INVENTORY)
public class InventoryController {
	
	@Autowired
	private InventoryService inventoryService;
	
	@Autowired
	private ProductService productService;
	
	@GetMapping
	public String getInventory(Model model) {
		List<PlankColor> colorDict =  productService.getColorDict();
		List<PlankSize> sizeDict = productService.getSizeDict();
		List<WoodSpecies> speciesDict = productService.getSpeciesDict();
		List<Grade> gradeDict = productService.getGradeDict();
		model.addAttribute("colorDict", colorDict);
		model.addAttribute("sizeDict", sizeDict);
		model.addAttribute("speciesDict", speciesDict);
		model.addAttribute("gradeDict", gradeDict);
		return Pages.INVENTORY;
	}
	
	@RequestMapping(value = "/getFilteredStocks", method = RequestMethod.POST)
	@ResponseBody
	public String getAllInventoryItems(InventoryFilterConditionForm inventoryFilterConditionForm) {
		//DatatablesView<ProductInventoryItem> datatablesView = inventoryService.getAllInventoryItems();
		int colourId = inventoryFilterConditionForm.getColour().equals("") ? -1 : Integer.valueOf(inventoryFilterConditionForm.getColour());
		int widthId = inventoryFilterConditionForm.getWidth().equals("") ? -1 : Integer.valueOf(inventoryFilterConditionForm.getWidth());
		int speciesId = inventoryFilterConditionForm.getSpecies().equals("") ? -1 : Integer.valueOf(inventoryFilterConditionForm.getSpecies());
		int gradeId = inventoryFilterConditionForm.getGrade().equals("") ? -1 : Integer.valueOf(inventoryFilterConditionForm.getGrade());
		DatatablesView<ProductInventoryItem> datatablesView = inventoryService.getFilteredInventoryItems(colourId,
				widthId,
				speciesId,
				gradeId);
		return JSONObject.fromObject(datatablesView).toString();
	}
	
	@GetMapping("/warehouse")
	public String getWarehouse(Model model) {
		return "warehouse";
	}
	
	@GetMapping("/csvUrls.html")
	public String getCsvUrls(Model model) {
		return "csvUrls";
	}
	
	@GetMapping("/fileUpload.html")
	public String getFileUpload(Model model) {
		return "fileUpload";
	}
	
	@GetMapping("/demoInstructions.html")
	public String getDemoInstructions(Model model) {
		return "demoInstructions";
	}
}
