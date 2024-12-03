package com.ELOUSTA.Profile_backend.service.impl;

import com.ELOUSTA.Profile_backend.dto.ClientDTO;
import com.ELOUSTA.Profile_backend.entity.ClientEntity;
import com.ELOUSTA.Profile_backend.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.ELOUSTA.Profile_backend.TestData.testClientDTO;
import static com.ELOUSTA.Profile_backend.TestData.testClientEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl underTest;

    @Test
    public void testThatFindByIdReturnsClientWhenExists() {
        final ClientDTO clientDTO = testClientDTO();
        final ClientEntity clientEntity = testClientEntity();

        when(clientRepository.findById(eq(clientDTO.getId()))).thenReturn(Optional.of(clientEntity));

        final Optional<ClientDTO> result = underTest.getClient(clientDTO.getId());

        assertEquals(Optional.of(clientDTO), result);
    }
}
