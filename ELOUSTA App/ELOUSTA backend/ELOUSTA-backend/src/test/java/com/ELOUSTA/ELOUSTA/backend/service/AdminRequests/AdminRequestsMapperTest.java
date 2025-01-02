package com.ELOUSTA.ELOUSTA.backend.service.AdminRequests;


import com.ELOUSTA.ELOUSTA.backend.dto.AdminRequestsDTO;
import com.ELOUSTA.ELOUSTA.backend.entity.ClientEntity;
import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;
import com.ELOUSTA.ELOUSTA.backend.entity.TechnicianEntity;
import com.ELOUSTA.ELOUSTA.backend.repository.ClientRepository;
import com.ELOUSTA.ELOUSTA.backend.repository.TechnicianRepository;
import com.ELOUSTA.ELOUSTA.backend.service.adminRequests.AdminRequestsMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class AdminRequestsMapperTest {
    @InjectMocks
    private AdminRequestsMapper adminRequestsMapper;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private TechnicianRepository technicianRepository;

    private final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    @Test
    void shouldBeSameDates() throws ParseException {
        // Setup test data
        List<RequestEntity> entityList = new ArrayList<>();
        entityList.add(new RequestEntity(1, 1, 1, "IN-PROGRESS", "solving Leakage", "NEW YORK",
                dateFormat.parse("2/2/2024"), dateFormat.parse("2/2/2024")));
        entityList.add(new RequestEntity(1, 1, 2, "COMPLETED", "solving Leakage", "NEW YORK",
                dateFormat.parse("2/2/2024"), dateFormat.parse("3/3/2024")));

        ClientEntity client = new ClientEntity();
        client.setId(1);
        client.setUsername("ALI");

        TechnicianEntity technician = new TechnicianEntity();
        technician.setId(1);
        technician.setUsername("Mahmoud");

        when(clientRepository.findById(eq(1))).thenReturn(Optional.of(client));
        when(technicianRepository.findById(eq(1))).thenReturn(Optional.of(technician));

        // Execute and verify
        List<AdminRequestsDTO> result = adminRequestsMapper.mapRequestEntityToAdminRequestDTO(entityList);

        // Assertions
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(dateFormat.parse("2/2/2024"), result.get(0).getStartDate());
        assertEquals(dateFormat.parse("3/3/2024"), result.get(1).getEndDate());


        verify(clientRepository, times(2)).findById(1);
        verify(technicianRepository, times(2)).findById(1);
    }

    @Test
    void shouldBeEmptyList() {
        List<RequestEntity> entityList = new ArrayList<>();
        List<AdminRequestsDTO> result = adminRequestsMapper.mapRequestEntityToAdminRequestDTO(entityList);
        assertEquals(0, result.size());
    }
}
