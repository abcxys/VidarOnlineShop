package vidar.websystem.restController;

import lombok.RequiredArgsConstructor;
import net.sf.json.JSONObject;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vidar.websystem.constants.PathConstants;
import vidar.websystem.domain.*;
import vidar.websystem.dto.request.SalesOrderRequest;
import vidar.websystem.dto.response.MessageResponse;
import vidar.websystem.service.CartService;
import vidar.websystem.service.SalesOrderService;
import vidar.websystem.service.UserService;

/**
 * @author yishi.xing
 * create datetime 4/18/2024 10:34 AM
 * description
 */
@RestController
@RequiredArgsConstructor
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
        return JSONObject.fromObject(salesOrderItem).toString();
    }

    @GetMapping(value = "/getDealerInfoById")
    public Dealer getDealerInfoById(@RequestParam("id") Long id){
        return cartService.getDealerById(id);
    }

    @PostMapping("/add-new-order")
    public MessageResponse addNewOrder(@RequestBody SalesOrderRequest salesOrderRequest,
                                       BindingResult bindingResult,
                                       Model model,
                                       RedirectAttributes attributes){
        User user = userService.getAuthenticatedUser();
        return salesOrderService.addSalesOrder(user, salesOrderRequest);
    }
}
