package com.ELOUSTA.ELOUSTA.backend.service.ProfileTest.service.impl;

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

import java.io.IOException;
import java.util.Optional;

import static com.ELOUSTA.ELOUSTA.backend.service.ProfileTest.ProfileTestData.testClientProfileDTO;
import static com.ELOUSTA.ELOUSTA.backend.service.ProfileTest.ProfileTestData.testClientEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
public class ClientProfileServiceImplTTest {
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
}
