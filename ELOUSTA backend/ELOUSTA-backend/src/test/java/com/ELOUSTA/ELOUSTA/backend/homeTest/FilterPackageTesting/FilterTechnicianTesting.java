package com.ELOUSTA.ELOUSTA.backend.homeTest.FilterPackageTesting;


import com.ELOUSTA.ELOUSTA.backend.dto.HomeTechnicianDTO;
import com.ELOUSTA.ELOUSTA.backend.entity.DomainEntity;
import com.ELOUSTA.ELOUSTA.backend.entity.TechnicianEntity;
import com.ELOUSTA.ELOUSTA.backend.repository.DomainRepository;
import com.ELOUSTA.ELOUSTA.backend.repository.TechnicianRepository;
import com.ELOUSTA.ELOUSTA.backend.service.home.impl.FilterTechnicianService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

@SpringBootTest
public class FilterTechnicianTesting {

    //Arrange and initialization

    @Autowired
    private FilterTechnicianService filterTechnicianService;

    @Autowired
    private TechnicianRepository technicianRepository;

    @Autowired
    private DomainRepository domainRepository;

    @BeforeEach
    public void setup()
    {
        domainRepository.deleteAll();
        domainRepository.flush();
        technicianRepository.deleteAll();
        technicianRepository.flush();

        List<DomainEntity> domainEntities = List.of(
                FilterTechnicianTestData.domainOneTest(),
                FilterTechnicianTestData.domainTwoTest(),
                FilterTechnicianTestData.domainThreeTest(),
                FilterTechnicianTestData.domainFourTest()
        );

        domainRepository.saveAll(domainEntities);


        List<TechnicianEntity> technicians = List.of(
                FilterTechnicianTestData.technicianOneTest(),
                FilterTechnicianTestData.technicianTwoTest(),
                FilterTechnicianTestData.technicianThreeTest(),
                FilterTechnicianTestData.technicianFourTest(),
                FilterTechnicianTestData.technicianFiveTest()
        );

        technicianRepository.saveAll(technicians);
    }

    @Test
    void filterByRate() throws IOException {
        List<HomeTechnicianDTO>DTOs=filterTechnicianService.filterTechnician("Rate","4");
        Assertions.assertEquals(DTOs.get(0).getFirstName(),"Mahmoud");
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
