package vidar.websystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vidar.websystem.constants.Pages;
import vidar.websystem.constants.PathConstants;
import vidar.websystem.service.CartService;

/**
 * @author yishi.xing
 * create datetime 7/2/2024 10:55 PM
 * description
 */
@Controller
@RequiredArgsConstructor
@RequestMapping(PathConstants.CHECKOUT)
public class CheckOutController {

    private final CartService cartService;

    @GetMapping
    public String checkout(Model model) {
        model.addAttribute("products", cartService.getPerfumesInCart());
        model.addAttribute("floor_quantities", cartService.getFloorQuantitesInCart());
        return Pages.CHECKOUT_INFORMATION;
    }
}
