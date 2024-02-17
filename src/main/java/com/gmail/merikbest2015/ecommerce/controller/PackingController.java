package com.gmail.merikbest2015.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gmail.merikbest2015.ecommerce.constants.Pages;
import com.gmail.merikbest2015.ecommerce.constants.PathConstants;
import com.gmail.merikbest2015.ecommerce.domain.DatatablesView;
import com.gmail.merikbest2015.ecommerce.service.PackingService;
import com.gmail.merikbest2015.ecommerce.domain.Order;

import lombok.RequiredArgsConstructor;
import net.sf.json.JSONObject;

/**
 * @author yishi.xing
 * @created Feb 12, 2024 - 8:23:02 PM
 */
@Controller
@CrossOrigin(origins = "http://localhost:8080/")
@RequiredArgsConstructor
@RequestMapping(PathConstants.PACKING)
public class PackingController {
	
	@Autowired
	private PackingService packingService;
	
	@GetMapping
    public String getPacking(Model model) {
        //model.addAttribute("perfumes", cartService.getPerfumesInCart());
        return Pages.PACKING;
    }
	
	@CrossOrigin(origins = {"http://localhost:8080/","http://cas.baidu.com","http://do.baidu.com"},maxAge = 3600)
	@RequestMapping(value = "/showOrders", method = RequestMethod.POST)
	@ResponseBody
	public String getAllOrders() {
		DatatablesView<Order> datatablesView = packingService.getAllOrders();
		
		return JSONObject.fromObject(datatablesView).toString();
	}
}
