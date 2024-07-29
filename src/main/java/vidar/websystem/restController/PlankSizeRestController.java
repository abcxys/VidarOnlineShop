package vidar.websystem.restController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vidar.websystem.domain.PlankSize;
import vidar.websystem.service.PlankSizeService;

import java.util.List;

/**
 * @author yishi.xing
 * create datetime 7/23/2024 11:13 PM
 * description
 */

@RestController
@RequestMapping("/plankSize")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
public class PlankSizeRestController {

    private final PlankSizeService plankSizeService;

    @GetMapping("/getAll")
    public List<PlankSize> getAllPlankSizes(){
        return plankSizeService.getAllPlankSizes();
    }
}
