package vidar.websystem.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import formbean.InventoryFilterConditionForm;
import lombok.RequiredArgsConstructor;
import net.sf.json.JSONObject;
import vidar.websystem.constants.PathConstants;
import vidar.websystem.domain.DatatablesView;
import vidar.websystem.domain.InventoryItem;
import vidar.websystem.service.InventoryService;

/**
 * @author yishi.xing
 * @created Apr 4, 2024 - 4:19:14 PM
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(PathConstants.INVENTORY)
public class InventoryRestController {
	@Autowired
	private InventoryService inventoryService;
	
	@RequestMapping(value = "/getFilteredProductStocks", method = RequestMethod.POST)
	@ResponseBody
	public String getAllInventoryItems(InventoryFilterConditionForm inventoryFilterConditionForm) {
		//DatatablesView<InventoryItem> datatablesView = inventoryService.getAllInventoryItems();
		int colourId = inventoryFilterConditionForm.getColour().equals("") ? -1 : Integer.valueOf(inventoryFilterConditionForm.getColour());
		int widthId = inventoryFilterConditionForm.getWidth().equals("") ? -1 : Integer.valueOf(inventoryFilterConditionForm.getWidth());
		int speciesId = inventoryFilterConditionForm.getSpecies().equals("") ? -1 : Integer.valueOf(inventoryFilterConditionForm.getSpecies());
		int gradeId = inventoryFilterConditionForm.getGrade().equals("") ? -1 : Integer.valueOf(inventoryFilterConditionForm.getGrade());
		DatatablesView<InventoryItem> datatablesView = inventoryService.getFilteredProductInventoryItems(colourId,
				widthId,
				gradeId);
		return JSONObject.fromObject(datatablesView).toString();
	}
}
