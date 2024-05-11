package vidar.websystem.restController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vidar.websystem.constants.PathConstants;
import vidar.websystem.domain.FactoryInventory;
import vidar.websystem.domain.User;
import vidar.websystem.service.InventoryService;
import vidar.websystem.service.UserService;

import java.math.BigDecimal;

/**
 * @author yishi.xing
 * create datetime 5/11/2024 2:47 PM
 * description
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(PathConstants.FACTORYINVENTORY)
public class FactoryInventoryRestController {
    private final InventoryService inventoryService;
    private final UserService userService;

    @PutMapping(value = "/update")
    public ResponseEntity<?> updateFactoryInventoryItem(@RequestParam("id") Long id,
                                                 @RequestParam("location") String location,
                                                 @RequestParam("quantity") BigDecimal quantity){
        log.info("updating inventory id = " + id);
        log.info("updating inventory location = " + location);
        log.info("updating inventory quantity = " + quantity);
        // Ignore the location input for factory inventory
//        if (!inventoryService.existsLocationTorontoWarehouse(location))
//            return ResponseEntity.badRequest().body("Location does not exist!");
        //TODO: do we need to validate the value of quantity?
        FactoryInventory inventory = inventoryService.getFactoryInventoryById(id);
        Long locationId = inventoryService.getLocationIdByBayAndWarehouseId("factory",4L);
        // if content of inventory item does not change at all.
        if (locationId.equals(inventory.getLocationId()) && quantity.compareTo(inventory.getCurrentQuantity()) == 0)
            return ResponseEntity.badRequest().body("No update to the inventory!");
        // still only working on Toronto warehouse
        inventory.setLocationId(locationId);
        inventory.setCurrentQuantity(quantity);
        User user = userService.getAuthenticatedUser();
        return ResponseEntity.ok(inventoryService.updateFactoryInventory(user, inventory));
    }
}
