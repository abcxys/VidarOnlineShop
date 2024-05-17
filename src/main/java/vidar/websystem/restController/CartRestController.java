package vidar.websystem.restController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vidar.websystem.constants.PathConstants;
import vidar.websystem.service.CartService;

import java.math.BigDecimal;

/**
 * @author yishi.xing
 * create datetime 5/17/2024 3:58 PM
 * description
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(PathConstants.CART)
public class CartRestController {

    private final CartService cartService;

    @PostMapping("/remove-with-quantity")
    public ResponseEntity<?> removeProductWithQuantityFromCart(@RequestParam("productId") Long productId,
                                                               @RequestParam("quantity") Long quantity) {
        cartService.removeProductWithQuantityFromCart(productId, quantity);
        return ResponseEntity.ok().body("Removed product with quantity: " + quantity);
    }
}
