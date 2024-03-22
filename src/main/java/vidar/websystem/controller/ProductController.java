package vidar.websystem.controller;

import lombok.RequiredArgsConstructor;
import vidar.websystem.constants.Pages;
import vidar.websystem.constants.PathConstants;
import vidar.websystem.dto.request.SearchRequest;
import vidar.websystem.service.InventoryService;
import vidar.websystem.service.ProductService;
import vidar.websystem.utils.ControllerUtils;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping(PathConstants.PRODUCT)
public class ProductController {

    private final ProductService perfumeService;
    private final InventoryService inventoryService;
    private final ControllerUtils controllerUtils;

    @GetMapping("/{productId}")
    public String getPerfumeById(@PathVariable Long productId, Model model) {
        model.addAttribute("perfume", perfumeService.getProductById(productId));
        model.addAttribute("quantity", inventoryService.getStockByFloorId(productId));
        return Pages.PRODUCT;
    }

    @GetMapping
    public String getPerfumesByFilterParams(SearchRequest request, Model model, Pageable pageable) {
        controllerUtils.addPagination(request, model, perfumeService.getProductsByFilterParams(request, pageable));
        return Pages.PRODUCTS;
    }

    @GetMapping("/search")
    public String searchPerfumes(SearchRequest request, Model model, Pageable pageable) {
        controllerUtils.addPagination(request, model, perfumeService.searchProducts(request, pageable));
        return Pages.PRODUCTS;
    }
}
