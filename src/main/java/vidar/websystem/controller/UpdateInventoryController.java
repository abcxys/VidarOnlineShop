package vidar.websystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import vidar.websystem.constants.Pages;
import vidar.websystem.constants.PathConstants;

/**
 * @author yishi.xing
 * @created Mar 6, 2024 - 11:47:06 PM
 */
@Controller
@RequiredArgsConstructor
@RequestMapping(PathConstants.UPDATE)
public class UpdateInventoryController {
	@GetMapping
    public String getUpdate(Model model) {
        return Pages.UPDATE_INVENTORY;
    }
}
