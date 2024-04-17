package vidar.websystem.restController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import net.sf.json.JSONObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vidar.websystem.constants.PathConstants;
import vidar.websystem.domain.Container;
import vidar.websystem.domain.DatatablesView;
import vidar.websystem.domain.ProductContainerItem;
import vidar.websystem.domain.User;
import vidar.websystem.dto.request.SearchRequest;
import vidar.websystem.service.InventoryService;
import vidar.websystem.service.UserService;

/**
 * @author yishi.xing
 * create datetime 4/13/2024 2:39 PM
 * description
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(PathConstants.CONTAINER)
public class ContainerRestController {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    private final InventoryService inventoryService;
    private final UserService userService;

    @PostMapping(value = "/getFilteredContainers")
    public String getFilteredContainers(@RequestParam("searchType") String searchType,
            @RequestParam("searchValue") String searchValue, Model model, Pageable pageable){
        log.info("returning filtered containers' info");
        DatatablesView<Container> datatablesView = inventoryService.getFilteredContainers(searchType, searchValue, pageable);
        return JSONObject.fromObject(datatablesView).toString();
    }

    @PostMapping(value = "/getItemsByContainerId")
    public String getItemsByContainerId(@RequestParam("containerId") Long containerId,
                                        Model model, Pageable pageable){
        DatatablesView<ProductContainerItem> productContainerItem = inventoryService.getContainerItemsById(containerId);
        return JSONObject.fromObject(productContainerItem).toString();
    }

    @PutMapping(value = "/update")
    public ResponseEntity<?> updateContainer(@RequestParam("id") Long id,
                                             @RequestParam("containerNumber") String containerNumber,
                                             @RequestParam("billOfLandingNumber") String billOfLandingNumber,
                                             @RequestParam("estimatedArrivalDate") String estimatedArrivalDate,
                                             @RequestParam("containerStatusId") Long containerStatusId){
        User user = userService.getAuthenticatedUser();
        Container container = inventoryService.getContainerById(id);
        container.setContainerNumber(containerNumber);
        container.setBillOfLandingNumber(billOfLandingNumber);
        try {
            container.setEstimatedArrivalDate(formatter.parse(estimatedArrivalDate));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        container.setContainerStatusId(containerStatusId);
        return ResponseEntity.ok(inventoryService.updateContainer(user, container));
    }

    @PutMapping(value = "updateItem")
    public ResponseEntity<?> updateContainerItem(@RequestParam("id") Long id,
                                                 @RequestParam("skid") Integer skid,
                                                 @RequestParam("box") BigDecimal box){
        return ResponseEntity.ok("ok");
    }
}
