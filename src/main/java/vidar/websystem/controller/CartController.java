package vidar.websystem.controller;

import lombok.RequiredArgsConstructor;
import vidar.websystem.constants.Pages;
import vidar.websystem.constants.PathConstants;
import vidar.websystem.service.CartService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping(PathConstants.CART)
public class CartController {

    private final CartService cartService;

    @GetMapping
    public String getCart(Model model) {
        model.addAttribute("perfumes", cartService.getPerfumesInCart());
        model.addAttribute("floors_quantities", cartService.getFloorQuantitesInCart());
        return Pages.CART;
    }

    @PostMapping("/add")
    public String addPerfumeToCart(@RequestParam("productId") Long perfumeId, @RequestParam("txtEstimatedCarton") Long quantity) {
        //cartService.addPerfumeToCart(perfumeId);
        cartService.addHardwoodWithQuantityToCart(perfumeId, quantity);
        return "redirect:" + PathConstants.CART;
    }

    @PostMapping("/remove")
    public String removePerfumeFromCart(@RequestParam("perfumeId") Long perfumeId) {
        cartService.removePerfumeFromCart(perfumeId);
        return "redirect:" + PathConstants.CART;
    }
    
    @PostMapping("/update")
    public String updatePerfumeToCart(@RequestParam("perfumeId") Long perfumeId, @RequestParam("quantity") Long quantity) {
        //cartService.addPerfumeToCart(perfumeId);
        cartService.updateHardwoodWithQuantityToCart(perfumeId, quantity);
        return "redirect:" + PathConstants.CART;
    }
}
