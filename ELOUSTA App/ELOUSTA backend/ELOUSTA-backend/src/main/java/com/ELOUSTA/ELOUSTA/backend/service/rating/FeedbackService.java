package com.ELOUSTA.ELOUSTA.backend.service.rating;


import com.ELOUSTA.ELOUSTA.backend.entity.FeedbackEntity;
import com.ELOUSTA.ELOUSTA.backend.entity.TechnicianEntity;
import com.ELOUSTA.ELOUSTA.backend.repository.FeedbackRepository;
import com.ELOUSTA.ELOUSTA.backend.repository.TechnicianRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeedbackService {
    @Autowired
    FeedbackRepository feedbackRepository;

    @Autowired
    TechnicianRepository technicianRepository;

    @Transactional
    public FeedbackEntity saveFeedback(FeedbackEntity feedback) {
        feedbackRepository.save(feedback);
        long count = feedbackRepository.countByTechId(feedback.getTechId());
        double ratingSum = 0.0;
        Optional<TechnicianEntity> technician = technicianRepository.findById(feedback.getTechId());
        if(technician.isPresent()) {
            System.out.println(technician.get().getRate());
            ratingSum = (count - 1) * technician.get().getRate();
        }
        ratingSum += feedback.getRate();
        double averageRate = ratingSum / count;
        technicianRepository.updateTechnicianRateById(feedback.getTechId(), averageRate);
        return feedback;
    }

    public List<FeedbackEntity> getFeedbacksByTechId(int techId) {
        return feedbackRepository.findAllByTechId(techId);
    }
}
