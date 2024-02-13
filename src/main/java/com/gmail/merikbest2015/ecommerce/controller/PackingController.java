package com.gmail.merikbest2015.ecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gmail.merikbest2015.ecommerce.constants.Pages;
import com.gmail.merikbest2015.ecommerce.constants.PathConstants;

import lombok.RequiredArgsConstructor;

/**
 * @author yishi.xing
 * @created Feb 12, 2024 - 8:23:02 PM
 */
@Controller
@RequiredArgsConstructor
@RequestMapping(PathConstants.PACKING)
public class PackingController {
	
	@GetMapping
    public String getPacking(Model model) {
        //model.addAttribute("perfumes", cartService.getPerfumesInCart());
        return Pages.PACKING;
    }
}
