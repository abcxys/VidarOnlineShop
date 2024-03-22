package vidar.websystem.controller;

import lombok.RequiredArgsConstructor;
import vidar.websystem.constants.Pages;
import vidar.websystem.constants.PathConstants;
import vidar.websystem.dto.request.SearchRequest;
import vidar.websystem.service.InventoryService;
import vidar.websystem.service.PerfumeService;
import vidar.websystem.utils.ControllerUtils;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping(PathConstants.PERFUME)
public class PerfumeController {

    private final PerfumeService perfumeService;
    private final InventoryService inventoryService;
    private final ControllerUtils controllerUtils;

    @GetMapping("/{perfumeId}")
    public String getPerfumeById(@PathVariable Long perfumeId, Model model) {
        model.addAttribute("perfume", perfumeService.getPerfumeById(perfumeId));
        model.addAttribute("quantity", inventoryService.getStockByFloorId(perfumeId));
        return Pages.PERFUME;
    }

    @GetMapping
    public String getPerfumesByFilterParams(SearchRequest request, Model model, Pageable pageable) {
        controllerUtils.addPagination(request, model, perfumeService.getPerfumesByFilterParams(request, pageable));
        return Pages.PERFUMES;
    }

    @GetMapping("/search")
    public String searchPerfumes(SearchRequest request, Model model, Pageable pageable) {
        controllerUtils.addPagination(request, model, perfumeService.searchPerfumes(request, pageable));
        return Pages.PERFUMES;
    }
}
