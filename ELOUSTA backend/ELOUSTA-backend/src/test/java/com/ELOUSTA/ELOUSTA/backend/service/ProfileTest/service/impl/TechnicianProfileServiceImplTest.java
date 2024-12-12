package com.ELOUSTA.ELOUSTA.backend.service.ProfileTest.service.impl;

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
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.io.IOException;
import java.util.Optional;

import static com.ELOUSTA.ELOUSTA.backend.service.ProfileTest.ProfileTestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

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

    @Test
    public void testForDeleteProfilePictureById() throws IOException {
        final TechnicianEntity technicianEntity = testTechnicianEntity();

        doNothing().when(technicianRepository).deleteProfilePictureById(technicianEntity.getId());

        technicianProfileService.removeTechnicianProfilePicture(technicianEntity.getId());

        verify(technicianRepository).deleteProfilePictureById(eq(technicianEntity.getId()));
    }

    @Test
    public void testForResetPasswordForExistingClient() {
        final TechnicianEntity technicianEntity = testTechnicianEntity();

        when(technicianRepository.findById(eq(technicianEntity.getId())))
                .thenReturn(Optional.of(technicianEntity));


        String newPassword = "newPassword";
        technicianProfileService.resetTechnicianPassword(
                technicianEntity.getId(),
                newPassword
        );

        verify(technicianRepository).findById(technicianEntity.getId());
        verify(technicianRepository).save(technicianEntity);

        assertEquals(newPassword, technicianEntity.getPassword());
    }

    @Test
    public void testForResetPasswordForNonExistingClient() {
        Integer id = 123;

        when(technicianRepository.findById(id)).thenReturn(Optional.empty());

        String newPassword = "newPassword";
        UsernameNotFoundException exception = assertThrows(
                UsernameNotFoundException.class,
                () -> technicianProfileService.resetTechnicianPassword(id, newPassword)
        );

        assertEquals("Technician with " + id + " not exist", exception.getMessage());

        verify(technicianRepository).findById(id);
        verify(technicianRepository, never()).save(any());
    }

    @Test
    public void testForDeleteExistingPortfolioByPortfolioIdAndTechnicianId() {
        final TechnicianEntity technicianEntity = testTechnicianEntity();

        doNothing().when(technicianRepository).deletePortfolioByIdAndTechnicianId(
                technicianEntity.getId(),
                technicianEntity.getPortfolioEntities().getFirst().getId()
        );

        technicianProfileService.removeTechnicianPortfolio(
                technicianEntity.getId(),
                technicianEntity.getPortfolioEntities().getFirst().getId()
        );

        verify(technicianRepository).deletePortfolioByIdAndTechnicianId(
                technicianEntity.getId(),
                technicianEntity.getPortfolioEntities().getFirst().getId()
        );
    }
}
