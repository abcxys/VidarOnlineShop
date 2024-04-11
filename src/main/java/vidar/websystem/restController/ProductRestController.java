package vidar.websystem.restController;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;
import vidar.websystem.domain.FloorColorSize;
import vidar.websystem.service.ProductService;

import java.util.List;

/**
 * @author yishi.xing
 * @created Mar 25, 2024 - 8:43:35 AM
 */
@RestController
@RequestMapping("/productApi")
@RequiredArgsConstructor
@Slf4j
public class ProductRestController {
	private final ProductService productService;

	/**
	 * Get list of active products' summary
	 * @param pageable frontend argument
	 * @return list of product's summary
	 */
	@GetMapping("/getProductDict")
	public List<FloorColorSize> getActiveProductDict(Pageable pageable){
		return productService.getActiveProducts(pageable).getContent();
	}
}
