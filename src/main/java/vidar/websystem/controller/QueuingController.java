package vidar.websystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vidar.websystem.constants.Pages;
import vidar.websystem.constants.PathConstants;
import vidar.websystem.domain.QueueItem;
import vidar.websystem.service.QueueService;

import java.util.List;

/**
 * @author yishi.xing
 * create datetime 5/21/2024 10:34 PM
 * description
 */@Controller
@RequiredArgsConstructor
@RequestMapping(PathConstants.QUEUE)
public class QueuingController {

     private final QueueService queueService;

     @GetMapping
     public String getQueuing(Model model){
         List<QueueItem> queueItems = queueService.getWaitingQueueItemsCreatedToday();
         model.addAttribute("queueItems1", queueItems);
         model.addAttribute("queueItems2", queueService.getPreparingQueueItemsCreatedToday());
         model.addAttribute("queueItems3", queueService.getCompletedQueueItemsCreatedToday());
         return Pages.QUEUING;
     }
}
