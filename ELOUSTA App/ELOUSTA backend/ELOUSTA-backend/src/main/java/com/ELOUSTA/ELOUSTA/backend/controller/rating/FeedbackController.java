package com.ELOUSTA.ELOUSTA.backend.controller.rating;


import com.ELOUSTA.ELOUSTA.backend.entity.FeedbackEntity;
import com.ELOUSTA.ELOUSTA.backend.service.rating.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class FeedbackController {


    @Autowired
    FeedbackService feedbackService;


    @PostMapping("/client/feedback")
    public FeedbackEntity saveFeedback(@RequestBody FeedbackEntity feedback) {
        return feedbackService.saveFeedback(feedback);
    }

    @PostMapping("/client/getFeedbackByTechId")
    public List<FeedbackEntity> getFeedback(@RequestParam int techId) {
        return feedbackService.getFeedbacksByTechId(techId);
    }
}
