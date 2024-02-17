package com.gmail.merikbest2015.ecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gmail.merikbest2015.ecommerce.constants.Pages;
import com.gmail.merikbest2015.ecommerce.constants.PathConstants;

import lombok.RequiredArgsConstructor;
import net.sf.json.JSONObject;

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
