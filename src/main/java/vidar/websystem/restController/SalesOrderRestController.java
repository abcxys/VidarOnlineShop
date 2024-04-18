package vidar.websystem.restController;

import lombok.RequiredArgsConstructor;
import net.sf.json.JSONObject;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vidar.websystem.constants.PathConstants;
import vidar.websystem.domain.CartItem;
import vidar.websystem.domain.DatatablesView;
import vidar.websystem.service.CartService;
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

    @GetMapping(value = "/getItemsInCart")
    public String getItemsInCart(Model model, Pageable pageable){
        DatatablesView<CartItem> cartItem = cartService.getCartItemsTable();
        return JSONObject.fromObject(cartItem).toString();
    }
}
