package vidar.websystem.controller;

import lombok.RequiredArgsConstructor;
import vidar.websystem.constants.Pages;
import vidar.websystem.constants.PathConstants;
import vidar.websystem.domain.User;
import vidar.websystem.dto.request.OrderRequest;
import vidar.websystem.service.CartService;
import vidar.websystem.service.OrderService;
import vidar.websystem.service.UserService;
import vidar.websystem.utils.ControllerUtils;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping(PathConstants.SALESORDER)
public class SalesOrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final CartService cartService;
    private final ControllerUtils controllerUtils;

    @GetMapping("/{orderId}")
    public String getOrder(@PathVariable Long orderId, Model model) {
        model.addAttribute("order", orderService.getOrder(orderId));
        return Pages.ORDER;
    }

    @GetMapping("/salesOrdering")
    public String getSalesOrdering(Model model, Pageable pageable) {

        controllerUtils.addPagination(model, orderService.getUserOrdersList(pageable));
        model.addAttribute("dealer_dict", cartService.getDealers());
        return Pages.SALES_ORDERING;
    }


    @GetMapping
    public String getOrdering(Model model) {
        model.addAttribute("perfumes", orderService.getOrdering());
        return Pages.ORDERING;
    }

    @GetMapping("/user/salesOrders")
    public String getUserOrdersList(Model model, Pageable pageable) {
        controllerUtils.addPagination(model, orderService.getUserOrdersList(pageable));
        return Pages.ORDERS;
    }

    @PostMapping
    public String postOrder(@Valid OrderRequest orderRequest, BindingResult bindingResult, Model model) {
        User user = userService.getAuthenticatedUser();
        if (controllerUtils.validateInputFields(bindingResult, model, "perfumes", user.getPerfumeList())) {
            return Pages.ORDERING;
        }
        model.addAttribute("orderId", orderService.postOrder(user, orderRequest));
        return Pages.ORDER_FINALIZE;
    }
}
