package com.ELOUSTA.ELOUSTA.backend.service.rate;

import com.ELOUSTA.ELOUSTA.backend.entity.FeedbackEntity;
import com.ELOUSTA.ELOUSTA.backend.entity.TechnicianEntity;
import com.ELOUSTA.ELOUSTA.backend.repository.FeedbackRepository;
import com.ELOUSTA.ELOUSTA.backend.repository.TechnicianRepository;
import com.ELOUSTA.ELOUSTA.backend.service.rating.FeedbackService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class FeedbackServiceTest {

    @InjectMocks
    private FeedbackService feedbackService;

    @Mock
    private FeedbackRepository feedbackRepository;

    @Mock
    private TechnicianRepository technicianRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveFeedback_WithExistingTechnician() {
        // Mock data
        FeedbackEntity feedback = FeedbackEntity.builder()
                .clientId(1)
                .techId(2)
                .rate(4.5)
                .commentBody("Great service!")
                .build();

        TechnicianEntity technician = TechnicianEntity.builder()
                .id(2)
                .rate(4.0)
                .build();

        when(feedbackRepository.save(feedback)).thenReturn(feedback);
        when(feedbackRepository.countByTechId(2)).thenReturn(5L);
        when(technicianRepository.findById(2)).thenReturn(Optional.of(technician));

        // Execute the method
        FeedbackEntity result = feedbackService.saveFeedback(feedback);

        // Verify interactions and assert
        verify(feedbackRepository).save(feedback);
        verify(feedbackRepository).countByTechId(2);
        verify(technicianRepository).findById(2);
        verify(technicianRepository).updateTechnicianRateById(2, 4.1); // New rate calculated as (4.0 * 4 + 4.5) / 5

        assertNotNull(result);
        assertEquals(feedback, result);
    }

    @Test
    public void testSaveFeedback_WithNewTechnician() {
        // Mock data
        FeedbackEntity feedback = FeedbackEntity.builder()
                .clientId(1)
                .techId(3)
                .rate(5.0)
                .commentBody("Excellent service!")
                .build();

        when(feedbackRepository.save(feedback)).thenReturn(feedback);
        when(feedbackRepository.countByTechId(3)).thenReturn(1L);
        when(technicianRepository.findById(3)).thenReturn(Optional.empty());

        // Execute the method
        FeedbackEntity result = feedbackService.saveFeedback(feedback);

        // Verify interactions and assert
        verify(feedbackRepository).save(feedback);
        verify(feedbackRepository).countByTechId(3);
        verify(technicianRepository).findById(3);
        verify(technicianRepository).updateTechnicianRateById(3, 5.0); // New rate is simply the feedback rate

        assertNotNull(result);
        assertEquals(feedback, result);
    }

    @Test
    public void testFindAllByTechId() {
        // Mock data
        FeedbackEntity feedback1 = FeedbackEntity.builder()
                .clientId(1)
                .techId(2)
                .rate(4.5)
                .commentBody("Great service!")
                .build();

        FeedbackEntity feedback2 = FeedbackEntity.builder()
                .clientId(2)
                .techId(2)
                .rate(4.0)
                .commentBody("Good job!")
                .build();

        List<FeedbackEntity> feedbackList = List.of(feedback1, feedback2);

        when(feedbackRepository.findAllByTechId(2)).thenReturn(feedbackList);

        // Execute the method
        List<FeedbackEntity> result = feedbackService.getFeedbacksByTechId(2);

        // Verify interactions and assert
        verify(feedbackRepository).findAllByTechId(2);
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(feedback1));
        assertTrue(result.contains(feedback2));
    }
}
