package vidar.websystem.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import com.fasterxml.jackson.databind.ObjectMapper;
import vidar.websystem.constants.Pages;
import vidar.websystem.constants.PathConstants;
import vidar.websystem.domain.DatatablesView;
import vidar.websystem.domain.SalesOrder;
import vidar.websystem.service.CartService;
import vidar.websystem.service.PackingService;

/**
 * @author yishi.xing
 * created Feb 12, 2024 - 8:23:02 PM
 */
@Controller
@CrossOrigin(origins = "http://localhost:8080/")
@RequiredArgsConstructor
@RequestMapping(PathConstants.PACKING)
public class PackingController {
	
	private final PackingService packingService;
	private final CartService cartService;
	
	@GetMapping
    public String getPacking(Model model) {
		model.addAttribute("dealer_dict", cartService.getDealers());
		model.addAttribute("packingSlipStatus_dict", packingService.getPackingSlipStatusDict());
		return Pages.PACKINGS;
    }

	@GetMapping("/{packingSlipId}")
	public String getPackingSlip(@PathVariable("packingSlipId") Long packingSlipId, Model model) {
		model.addAttribute("dealer_dict", cartService.getDealers());
		model.addAttribute("driver_dict", packingService.getDrivers());
		model.addAttribute("packingSlipStatus_dict", packingService.getPackingSlipStatusDict());
		model.addAttribute("packingSlip", packingService.getPackingSlipById(packingSlipId));
		return Pages.PACKING;
	}
	
	@RequestMapping(value = "/showOrders", method = RequestMethod.POST)
	@ResponseBody
	public String getAllOrders() {
		DatatablesView<SalesOrder> datatablesView = packingService.getAllOrders();
		ObjectMapper mapper = new ObjectMapper();
		try{
			return mapper.writeValueAsString(datatablesView);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
		/*
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes( new String[]{ "data:hardwoodfloors"} );

		return JSONObject.fromObject(datatablesView, jsonConfig).toString();
		 */
	}
}
