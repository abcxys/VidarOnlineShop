package vidar.websystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vidar.websystem.constants.Pages;
import vidar.websystem.constants.PathConstants;

/**
 * @author yishi.xing
 * create datetime 5/21/2024 10:34 PM
 * description
 */@Controller
@RequiredArgsConstructor
@RequestMapping(PathConstants.QUEUE)
public class QueuingController {

     @GetMapping
     public String getQueuing(Model model){
         return Pages.QUEUING;
     }
}
