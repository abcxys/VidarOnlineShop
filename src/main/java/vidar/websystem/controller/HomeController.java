package vidar.websystem.controller;

import lombok.RequiredArgsConstructor;
import vidar.websystem.constants.Pages;
import vidar.websystem.service.ProductService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ProductService productService;

    @GetMapping
    public String home(Model model) {
        model.addAttribute("perfumes", productService.getPopularProducts());
        return Pages.HOME;
    }
}
