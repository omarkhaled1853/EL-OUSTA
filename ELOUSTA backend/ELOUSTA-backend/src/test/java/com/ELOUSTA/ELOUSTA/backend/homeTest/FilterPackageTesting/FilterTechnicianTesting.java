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
//        delete technicians first
        technicianRepository.deleteAll();
//        delete domains
        domainRepository.deleteAll();
//        create domains data test
        List<DomainEntity> domainEntities = List.of(
                FilterTechnicianTestData.domainOneTest(),
                FilterTechnicianTestData.domainTwoTest(),
                FilterTechnicianTestData.domainThreeTest(),
                FilterTechnicianTestData.domainFourTest()
        );
//        save domains data test
        domainRepository.saveAll(domainEntities);
//        get the saved domains data test
        List<DomainEntity> savedDomainEntities = domainRepository.findAll();
//        create technicians data test
        List<TechnicianEntity> technicianEntities = List.of(
                FilterTechnicianTestData.technicianOneTest(savedDomainEntities.get(0)),
                FilterTechnicianTestData.technicianTwoTest(savedDomainEntities.get(1)),
                FilterTechnicianTestData.technicianThreeTest(savedDomainEntities.get(2)),
                FilterTechnicianTestData.technicianFourTest(savedDomainEntities.get(1)),
                FilterTechnicianTestData.technicianFiveTest(savedDomainEntities.get(3))
        );
//        save technicians data test
        technicianRepository.saveAll(technicianEntities);
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
