package vidar.websystem.restController;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import formbean.InventoryFilterConditionForm;
import lombok.RequiredArgsConstructor;
import net.sf.json.JSONObject;
import vidar.websystem.constants.PathConstants;
import vidar.websystem.domain.*;
import vidar.websystem.service.InventoryService;
import vidar.websystem.service.UserService;

import java.math.BigDecimal;

/**
 * @author yishi.xing
 * create datetime Apr 4, 2024 - 4:19:14 PM
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(PathConstants.INVENTORY)
public class InventoryRestController {
	private final InventoryService inventoryService;
	private final UserService userService;

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
		if (productId == null)
			return "Error";
		DatatablesView<InventoryItem> datatablesView = inventoryService.getInventoryItemsByProductId(productId);
		return JSONObject.fromObject(datatablesView).toString();
	}

	@PutMapping(value = "/update")
	public ResponseEntity<?> updateInventoryItem(@RequestParam("id") Long id,
												 @RequestParam("location") String location,
												 @RequestParam("quantity") BigDecimal quantity){
		log.info("updating inventory id = " + String.valueOf(id));
		log.info("updating inventory location = " + location);
		log.info("updating inventory quantity = " + String.valueOf(quantity));
		if (!inventoryService.existsLocationTorontoWarehouse(location))
			return ResponseEntity.badRequest().body("Location does not exist!");
		//TODO: do we need to validate the value of quantity?
		Inventory inventory = inventoryService.getInventoryById(id);
		Long locationId = inventoryService.getLocationIdByBayAndWarehouseId(location,1L);
		// if content of inventory item does not change at all.
		if (locationId.equals(inventory.getLocationId()) && quantity.compareTo(inventory.getCurrentQuantity()) == 0)
			return ResponseEntity.badRequest().body("No update to the inventory!");
		// still only working on Toronto warehouse
		inventory.setLocationId(locationId);
		inventory.setCurrentQuantity(quantity);
		User user = userService.getAuthenticatedUser();
		return ResponseEntity.ok(inventoryService.updateInventory(user, inventory));
	}
}
