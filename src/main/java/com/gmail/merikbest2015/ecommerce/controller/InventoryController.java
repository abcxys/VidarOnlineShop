package com.gmail.merikbest2015.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

import com.gmail.merikbest2015.ecommerce.constants.Pages;
import com.gmail.merikbest2015.ecommerce.constants.PathConstants;
import com.gmail.merikbest2015.ecommerce.domain.DatatablesView;
import com.gmail.merikbest2015.ecommerce.domain.InventoryItem;
import com.gmail.merikbest2015.ecommerce.domain.WidthDict;
import com.gmail.merikbest2015.ecommerce.domain.ColorDict;
import com.gmail.merikbest2015.ecommerce.service.InventoryService;

import lombok.RequiredArgsConstructor;
import net.sf.json.JSONObject;

/**
 * @author yishi.xing
 * @created Feb 20, 2024 - 10:33:26 PM
 */
@Controller
@RequiredArgsConstructor
@RequestMapping(PathConstants.INVENTORY)
public class InventoryController {
	
	@Autowired
	private InventoryService inventoryService;
	
	@GetMapping
	public String getInventory(Model model) {
		List<ColorDict> colorDict =  inventoryService.getColorDict();
		List<WidthDict> widthDict = inventoryService.getWidthDict();
		model.addAttribute("colorDict", colorDict);
		model.addAttribute("widthDict", widthDict);
		return Pages.INVENTORY;
	}
	
	@RequestMapping(value = "/getAllStocks", method = RequestMethod.POST)
	@ResponseBody
	public String getAllInventoryItems() {
		DatatablesView<InventoryItem> datatablesView = inventoryService.getAllInventoryItems();
		
		return JSONObject.fromObject(datatablesView).toString();
	}
}