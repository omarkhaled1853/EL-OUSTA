package com.ELOUSTA.Profile_backend.service.impl;

import com.ELOUSTA.Profile_backend.dto.ClientDTO;
import com.ELOUSTA.Profile_backend.dto.TechnicianDTO;
import com.ELOUSTA.Profile_backend.entity.ClientEntity;
import com.ELOUSTA.Profile_backend.entity.TechnicianEntity;
import com.ELOUSTA.Profile_backend.repository.TechnicianRepository;
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

import static com.ELOUSTA.Profile_backend.TestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
public class TechnicianServiceImplTest {
    @InjectMocks
    private TechnicianServiceImpl technicianService;

    @Mock
    private TechnicianRepository technicianRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testThatFindByIdReturnsTechnicianWhenExists() throws IOException {
        final TechnicianDTO technicianDTO = testTechnicianDto();
        final TechnicianEntity technicianEntity = testTechnicianEntity();

        when(technicianRepository.findTechnicianWithDomainAndPortfolio(eq(technicianDTO.getId()))).thenReturn(Optional.of(technicianEntity));

        final Optional<TechnicianDTO> result = technicianService.getTechnician(technicianDTO.getId());

        assertEquals(Optional.of(technicianDTO), result);
    }

    @Test
    public void testThatFindByIdReturnsEmptyWhenNoTechnician() {
        final Integer id = 20;

        when(technicianRepository.findTechnicianWithDomainAndPortfolio(eq(id))).thenReturn(Optional.empty());

        final Optional<TechnicianDTO> result = technicianService.getTechnician(id);

        assertEquals(Optional.empty(), result);
    }
}
