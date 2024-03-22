package vidar.websystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import net.sf.json.JSONObject;
import vidar.websystem.constants.Pages;
import vidar.websystem.constants.PathConstants;

/**
 * @author yishi.xing
 * @created Feb 16, 2024 - 11:50:10 PM
 */
@Controller
@CrossOrigin(origins = "http://localhost:8080/")
@RequiredArgsConstructor
@RequestMapping(PathConstants.CREATEORDER)
public class CreateOrderController {
	
	@GetMapping
    public String getPacking(Model model) {
        //model.addAttribute("perfumes", cartService.getPerfumesInCart());
        return Pages.CREATE_ORDER;
    }
}
