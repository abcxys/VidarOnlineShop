package vidar.websystem.restController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vidar.websystem.constants.PathConstants;
import vidar.websystem.domain.Container;
import vidar.websystem.domain.DatatablesView;
import vidar.websystem.dto.request.SearchRequest;
import vidar.websystem.service.InventoryService;

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
    private final InventoryService inventoryService;

    @PostMapping(value = "/getFilteredContainers")
    public String getFilteredContainers(SearchRequest searchRequest, Model model, Pageable pageable){
        log.info("returning filtered containers' info");
        DatatablesView<Container> datatablesView = inventoryService.getFilteredContainers();
        return JSONObject.fromObject(datatablesView).toString();
    }
}
