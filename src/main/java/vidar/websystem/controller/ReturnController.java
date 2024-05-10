package vidar.websystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vidar.websystem.constants.Pages;
import vidar.websystem.constants.PathConstants;
import vidar.websystem.service.CartService;
import vidar.websystem.service.ReturnService;

/**
 * @author yishi.xing
 * create datetime 5/10/2024 10:41 AM
 * description
 */
@Controller
@RequiredArgsConstructor
@RequestMapping(PathConstants.RETURN)
public class ReturnController {

    private final CartService cartService;
    private final ReturnService returnService;

    @GetMapping
    public String getReturn(Model model) {
        model.addAttribute("dealer_dict", cartService.getDealers());
        model.addAttribute("returnSlipStatus_dict", returnService.getReturnStatusDict());
        return Pages.RETURNS;
    }
}
