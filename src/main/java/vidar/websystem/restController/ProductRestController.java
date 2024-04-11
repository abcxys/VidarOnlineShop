package vidar.websystem.restController;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;
import vidar.websystem.domain.FloorColorSize;
import vidar.websystem.domain.HardwoodFloor;
import vidar.websystem.repository.HardwoodFloorsRepository;
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

	@GetMapping("/getProductDict")
	public List<FloorColorSize> getProductDict(Pageable pageable){
		return productService.getProducts(pageable).getContent();
	}
}
