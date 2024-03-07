package com.gmail.merikbest2015.ecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gmail.merikbest2015.ecommerce.constants.Pages;
import com.gmail.merikbest2015.ecommerce.constants.PathConstants;

import lombok.RequiredArgsConstructor;

/**
 * @author yishi.xing
 * @created Mar 6, 2024 - 11:47:06 PM
 */
@Controller
@CrossOrigin(origins = "http://localhost:8080/")
@RequiredArgsConstructor
@RequestMapping(PathConstants.UPDATEINVENTORY)
public class UpdateInventoryController {
	@GetMapping
    public String getPacking(Model model) {
        return Pages.UPDATE_INVENTORY;
    }
}
