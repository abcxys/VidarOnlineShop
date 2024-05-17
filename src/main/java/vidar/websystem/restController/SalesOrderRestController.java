package vidar.websystem.restController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import formbean.SalesOrderFilterConditionForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vidar.websystem.constants.PathConstants;
import vidar.websystem.domain.*;
import vidar.websystem.dto.request.SalesOrderRequest;
import vidar.websystem.service.CartService;
import vidar.websystem.service.SalesOrderService;
import vidar.websystem.service.UserService;

import java.util.List;

/**
 * @author yishi.xing
 * create datetime 4/18/2024 10:34 AM
 * description
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(PathConstants.SALESORDER)
public class SalesOrderRestController {

    private final UserService userService;

    private final CartService cartService;
    private final SalesOrderService salesOrderService;

    @GetMapping(value = "/getItemsInCart")
    public String getItemsInCart(Model model, Pageable pageable){
        DatatablesView<CartItem> cartItem = cartService.getCartItemsTable();
        return JSONObject.fromObject(cartItem).toString();
    }

    @GetMapping(value = "/getSalesOrderItems")
    public String getSalesOrderItems(Model model, Pageable pageable){
        DatatablesView<SalesOrderItem> salesOrderItem = cartService.getSalesOrderItemsTable();
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(salesOrderItem);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping(value = "/getDealerInfoById")
    public Dealer getDealerInfoById(@RequestParam("id") Long id){
        return cartService.getDealerById(id);
    }

    @GetMapping(value = "/getSalesOrderProducts")
    public String getSalesOrderProducts(@RequestParam("so_id") Long id, Model model, RedirectAttributes redirectAttributes){
        DatatablesView<SalesOrderItem> salesOrderProducts = salesOrderService.getSalesOrderProductsBySOId(id);
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(salesOrderProducts);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping(value = "/getSalesOrderProductsList")
    @ResponseBody
    public String getSalesOrderProductsList(@RequestParam List<Long> ids){
        DatatablesView<SalesOrderItem> salesOrderProducts = salesOrderService.getSalesOrderProductsBySOIdsIn(ids);
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(salesOrderProducts);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping(value = "/getFilteredSalesOrders")
    @ResponseBody
    public String getFilteredSalesOrders(SalesOrderFilterConditionForm salesOrderFilterConditionForm) {
        DatatablesView<SalesOrder> datatablesView = salesOrderService.getFilteredSalesOrders(salesOrderFilterConditionForm);
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(datatablesView);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/add-new-order")
    public ResponseEntity<?> addNewOrder(@RequestBody SalesOrderRequest salesOrderRequest,
                                      BindingResult bindingResult,
                                      Model model,
                                      RedirectAttributes attributes){
        User user = userService.getAuthenticatedUser();
        return salesOrderService.addSalesOrder(user, salesOrderRequest);
    }

    @PostMapping("/update-order")
    public ResponseEntity<?> updateOrder(@RequestBody SalesOrderRequest salesOrderRequest,
                                       BindingResult bindingResult,
                                       Model model,
                                       RedirectAttributes attributes){
        User user = userService.getAuthenticatedUser();
        return salesOrderService.updateSalesOrder(user, salesOrderRequest);
    }
}
