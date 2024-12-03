package com.ELOUSTA.Profile_backend.service.impl;

import com.ELOUSTA.Profile_backend.repository.TechnicianRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
public class TechnicianServiceImplTest {
    @InjectMocks
    private TechnicianServiceImplTest technicianService;

    @Mock
    private TechnicianRepository technicianRepository;

}
