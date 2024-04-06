package vidar.websystem.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import formbean.InventoryFilterConditionForm;
import lombok.RequiredArgsConstructor;
import net.sf.json.JSONObject;
import vidar.websystem.constants.PathConstants;
import vidar.websystem.domain.DatatablesView;
import vidar.websystem.domain.InventoryItem;
import vidar.websystem.domain.ProductInventoryItem;
import vidar.websystem.service.InventoryService;

/**
 * @author yishi.xing
 * create datetime Apr 4, 2024 - 4:19:14 PM
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(PathConstants.INVENTORY)
public class InventoryRestController {
	@Autowired
	private InventoryService inventoryService;
	
	@RequestMapping(value = "/getFilteredProductStocks", method = RequestMethod.POST)
	public String getAllInventoryItems(InventoryFilterConditionForm inventoryFilterConditionForm) {
		//DatatablesView<ProductInventoryItem> datatablesView = inventoryService.getAllInventoryItems();
		int colourId = inventoryFilterConditionForm.getColour().equals("") ? -1 : Integer.parseInt(inventoryFilterConditionForm.getColour());
		int widthId = inventoryFilterConditionForm.getWidth().equals("") ? -1 : Integer.parseInt(inventoryFilterConditionForm.getWidth());
		int speciesId = inventoryFilterConditionForm.getSpecies().equals("") ? -1 : Integer.parseInt(inventoryFilterConditionForm.getSpecies());
		int gradeId = inventoryFilterConditionForm.getGrade().equals("") ? -1 : Integer.parseInt(inventoryFilterConditionForm.getGrade());
		DatatablesView<ProductInventoryItem> datatablesView = inventoryService.getFilteredProductInventoryItems(colourId,
				widthId,
				speciesId,
				gradeId,
				inventoryFilterConditionForm.getBatch());
		return JSONObject.fromObject(datatablesView).toString();
	}

	@PostMapping(value = "/getInventoryByProductId")
	public String getInventoryByProductId(@RequestParam("productId") Integer productId) {
		DatatablesView<InventoryItem> datatablesView = inventoryService.getInventoryItemsByProductId(productId);
		return JSONObject.fromObject(datatablesView).toString();
	}
}
