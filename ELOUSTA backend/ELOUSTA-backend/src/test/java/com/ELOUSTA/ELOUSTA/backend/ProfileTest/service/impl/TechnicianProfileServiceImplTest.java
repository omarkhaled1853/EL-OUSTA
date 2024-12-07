package com.ELOUSTA.ELOUSTA.backend.ProfileTest.service.impl;

import com.ELOUSTA.ELOUSTA.backend.dto.profileDto.TechnicianProfileProfileDTO;
import com.ELOUSTA.ELOUSTA.backend.entity.TechnicianEntity;
import com.ELOUSTA.ELOUSTA.backend.repository.TechnicianRepository;
import com.ELOUSTA.ELOUSTA.backend.service.profile.impl.TechnicianProfileServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.io.IOException;
import java.util.Optional;

import static com.ELOUSTA.ELOUSTA.backend.ProfileTest.service.impl.ProfileTestData.testTechnicianEntity;
import static com.ELOUSTA.ELOUSTA.backend.ProfileTest.service.impl.ProfileTestData.testTechnicianProfileDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
public class TechnicianProfileServiceImplTest {
    @InjectMocks
    private TechnicianProfileServiceImpl technicianProfileService;

    @Mock
    private TechnicianRepository technicianRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testThatFindByIdReturnsTechnicianWhenExists() throws IOException {
        final TechnicianProfileProfileDTO technicianProfileDTO = testTechnicianProfileDto();
        final TechnicianEntity technicianEntity = testTechnicianEntity();

        when(technicianRepository.findTechnicianWithDomainAndPortfolio(eq(technicianProfileDTO.getId()))).thenReturn(Optional.of(technicianEntity));

        final Optional<TechnicianProfileProfileDTO> result = technicianProfileService.getTechnician(technicianProfileDTO.getId());

        assertEquals(Optional.of(technicianProfileDTO), result);
    }

    @Test
    public void testThatFindByIdReturnsEmptyWhenNoTechnician() {
        final Integer id = 20;

        when(technicianRepository.findTechnicianWithDomainAndPortfolio(eq(id))).thenReturn(Optional.empty());

        final Optional<TechnicianProfileProfileDTO> result = technicianProfileService.getTechnician(id);

        assertEquals(Optional.empty(), result);
    }
}
