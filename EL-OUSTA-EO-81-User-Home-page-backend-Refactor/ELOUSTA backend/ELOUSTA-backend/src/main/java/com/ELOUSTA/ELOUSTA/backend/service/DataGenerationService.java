package com.ELOUSTA.ELOUSTA.backend.service;

import com.ELOUSTA.ELOUSTA.backend.entity.DomainEntity;
import com.ELOUSTA.ELOUSTA.backend.entity.TechnicianEntity;
import com.ELOUSTA.ELOUSTA.backend.repository.DomainRepository;
import com.ELOUSTA.ELOUSTA.backend.repository.TechnicianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DataGenerationService {

    @Autowired
    private DomainRepository domainRepository;

    @Autowired
    private TechnicianRepository technicianRepository;

    public void generateTestData() {
        // Create sample domains
        DomainEntity domain1 = DomainEntity.builder()
                .name("Electric")
                .photo("electric_photo_url")
                .build();
        DomainEntity domain2 = DomainEntity.builder()
                .name("Plumbing")
                .photo("plumbing_photo_url")
                .build();

        // Save domains to DB
        domainRepository.save(domain1);
        domainRepository.save(domain2);

        // Create sample technicians for each domain
        TechnicianEntity technician1 = TechnicianEntity.builder()
                .firstName("John")
                .lastName("Doe")
                .dob(new Date())
                .username("john.doe")
                .phoneNumber("1234567890")
                .emailAddress("john.doe@example.com")
                .password("password")
                .signUpDate(new Date())
                .city("City A")
                .rate(4.5)
                .jobStartDate(new Date())
                .description("Electrician with 5 years experience.")
                .domainEntity(domain1)
                .build();

        TechnicianEntity technician2 = TechnicianEntity.builder()
                .firstName("Jane")
                .lastName("Smith")
                .dob(new Date())
                .username("jane.smith")
                .phoneNumber("0987654321")
                .emailAddress("jane.smith@example.com")
                .password("password")
                .signUpDate(new Date())
                .city("City B")
                .rate(4.0)
                .jobStartDate(new Date())
                .description("Expert in Plumbing services.")
                .domainEntity(domain2)
                .build();

        // Save technicians to DB
        technicianRepository.save(technician1);
        technicianRepository.save(technician2);

        // Output to confirm data generation
        System.out.println("Test data generated successfully.");
    }
}
