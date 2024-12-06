package com.ELOUSTA.ELOUSTA.backend.homeTest.FilterPackageTesting;


import com.ELOUSTA.ELOUSTA.backend.dto.HomeTechnicianDTO;
import com.ELOUSTA.ELOUSTA.backend.dto.TechnicianDTO;
import com.ELOUSTA.ELOUSTA.backend.entity.DomainEntity;
import com.ELOUSTA.ELOUSTA.backend.entity.TechnicianEntity;
import com.ELOUSTA.ELOUSTA.backend.repository.TechnicianRepository;
import com.ELOUSTA.ELOUSTA.backend.service.home.impl.FilterTechnicianService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class FilterTechnicianTesting {

    //Arrange and initialization

    @Autowired
    private FilterTechnicianService filterTechnicianService;

    @Autowired
    private TechnicianRepository Repository;

    @BeforeEach
    public void setup()
    {
        Repository.deleteAll();
        List<TechnicianEntity> technicians = new ArrayList<>();

        DomainEntity domain1 = DomainEntity.builder()
                .id(1)
                .name("Carpentry")
                .build();

        DomainEntity domain2 = DomainEntity.builder()
                .id(2)
                .name("Electrical")
                .build();

        DomainEntity domain3 = DomainEntity.builder()
                .id(3)
                .name("Cleaning")
                .build();

        DomainEntity domain4 = DomainEntity.builder()
                .id(4)
                .name("Building")
                .build();



        TechnicianEntity tech1 = TechnicianEntity.builder()
            .id(1)
            .userName("joe44")
            .password("12345678")
            .domainEntity(domain1)
            .email("john.doe@gmail.com")
            .firstName("youssef")
            .lastName("Mahmoud")
            .dob(new Date(1985, 7, 20))
            .phoneNumber("5551234567")
            .city("Alexandria")
            .jobStartDate(new Date(2020, 1, 15))
            .rate(1.0)
            .build();


        TechnicianEntity tech2 = TechnicianEntity.builder()
                .id(2)
                .userName("Omar123")
                .password("safePassword321")
                .domainEntity(domain2)
                .email("omar@gmail.com")
                .firstName("Omar")
                .lastName("Khaled")
                .dob(new Date(1990, 3, 25))
                .phoneNumber("5559876543")
                .city("Cairo")
                .jobStartDate(new Date(2021, 5, 10))
                .rate(2.0)
                .build();

        TechnicianEntity tech3 = TechnicianEntity.builder()
                .id(3)
                .userName("Nada312")
                .password("pass456Secure")
                .domainEntity(domain3)
                .email("Nada@gmail.com")
                .firstName("Nada")
                .lastName("Fouad")
                .dob(new Date(1990, 11, 12))
                .phoneNumber("5552223333")
                .city("AlBehira")
                .jobStartDate(new Date(2019, 8, 1))
                .rate(3.0)
                .build();

        TechnicianEntity tech4 = TechnicianEntity.builder()
                .id(4)
                .userName("Mahmoud123")
                .password("strongPass789")
                .domainEntity(domain2)
                .email("Mahmoud515@gmail.com")
                .firstName("Mahmoud")
                .lastName("Adds")
                .dob(new Date(1995, 1, 15))
                .phoneNumber("01003207216")
                .city("Alexandria")
                .jobStartDate(new Date(2022, 3, 20))
                .rate(4.0)
                .build();

        TechnicianEntity tech5 = TechnicianEntity.builder()
                .id(5)
                .userName("Meedo")
                .password("simplePass101")
                .domainEntity(domain4)
                .email("Meedo@gmail.com")
                .firstName("mohammed")
                .lastName("mounir")
                .dob(new Date(1992, 6, 30))
                .phoneNumber("5556667777")
                .city("Cairo")
                .jobStartDate(new Date(2021, 7, 5))
                .rate(5.0)
                .build();

        technicians.add(tech1);
        technicians.add(tech2);
        technicians.add(tech3);
        technicians.add(tech4);
        technicians.add(tech5);
        Repository.saveAll(technicians);
    }

    @Test
    void filterByRate() throws IOException {
        List<HomeTechnicianDTO>DTOs=filterTechnicianService.filterTechnician("Rate","4");
        Assertions.assertEquals(DTOs.getFirst().getFirstName(),"Mahmoud");
    }

    @Test
    void filterByDomain() throws IOException {
        List<HomeTechnicianDTO>DTOs=filterTechnicianService.filterTechnician("Domain","Electrical");
        Assertions.assertEquals(DTOs.size(),2);
    }

    @Test
    void shouldNotReturnAnyOne() throws IOException {
        List<HomeTechnicianDTO>DTOs=filterTechnicianService.filterTechnician("City","elRaml2");
        Assertions.assertEquals(DTOs.size(),0);
    }

    @Test
    void nullFilterTest() throws IOException {
        List<HomeTechnicianDTO>DTOs=filterTechnicianService.filterTechnician("auth","data");
        Assertions.assertEquals(DTOs.size(),5);
    }

}
