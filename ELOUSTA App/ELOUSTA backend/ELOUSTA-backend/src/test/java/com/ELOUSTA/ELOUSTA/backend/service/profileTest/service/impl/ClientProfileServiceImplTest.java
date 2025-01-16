package com.ELOUSTA.ELOUSTA.backend.service.profileTest.service.impl;

import com.ELOUSTA.ELOUSTA.backend.dto.profileDto.ClientProfileDTO;
import com.ELOUSTA.ELOUSTA.backend.entity.ClientEntity;
import com.ELOUSTA.ELOUSTA.backend.repository.ClientRepository;
import com.ELOUSTA.ELOUSTA.backend.service.profile.impl.ClientProfileProfileServiceImpl;
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

import static com.ELOUSTA.ELOUSTA.backend.service.profileTest.ProfileTestData.testClientEntity;
import static com.ELOUSTA.ELOUSTA.backend.service.profileTest.ProfileTestData.testClientProfileDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
public class ClientProfileServiceImplTest {
    @InjectMocks
    private ClientProfileProfileServiceImpl clientProfileProfileService;

    @Mock
    private ClientRepository clientRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testThatFindByIdReturnsClientWhenExists() throws IOException {
        final ClientProfileDTO clientProfileDTO = testClientProfileDTO();
        final ClientEntity clientEntity = testClientEntity();

        when(clientRepository.findById(eq(clientProfileDTO.getId()))).thenReturn(Optional.of(clientEntity));

        final Optional<ClientProfileDTO> result = clientProfileProfileService.getClient(clientProfileDTO.getId());

        assertEquals(Optional.of(clientProfileDTO), result);
    }

    @Test
    public void testThatFindByIdReturnsEmptyWhenNoClient() {
        final Integer id = 20;

        when(clientRepository.findById(eq(id))).thenReturn(Optional.empty());

        final Optional<ClientProfileDTO> result = clientProfileProfileService.getClient(id);

        assertEquals(Optional.empty(), result);
    }

    @Test
    public void testForDeleteProfilePictureById() throws IOException {
        final ClientEntity clientEntity = testClientEntity();

        doNothing().when(clientRepository).deleteProfilePictureById(clientEntity.getId());

        clientProfileProfileService.removeClientProfilePicture(clientEntity.getId());

        verify(clientRepository).deleteProfilePictureById(eq(clientEntity.getId()));
    }

    @Test
    public void testForResetPasswordForExistingClient() {
        final ClientEntity clientEntity = testClientEntity();

        when(clientRepository.findById(eq(clientEntity.getId()))).thenReturn(Optional.of(clientEntity));


        String newPassword = "newPassword";
        clientProfileProfileService.resetClientPassword(
                clientEntity.getId(),
                newPassword
                );

        verify(clientRepository).findById(clientEntity.getId());
        verify(clientRepository).save(clientEntity);

        assertEquals(newPassword, clientEntity.getPassword());
    }

    @Test
    public void testForResetPasswordForNonExistingClient() {
        Integer id = 123;

        when(clientRepository.findById(id)).thenReturn(Optional.empty());

        String newPassword = "newPassword";
        UsernameNotFoundException exception = assertThrows(
                UsernameNotFoundException.class,
                () -> clientProfileProfileService.resetClientPassword(id, newPassword)
        );

        assertEquals("Client with " + id + " not exist", exception.getMessage());

        verify(clientRepository).findById(id);
        verify(clientRepository, never()).save(any());
    }
}
