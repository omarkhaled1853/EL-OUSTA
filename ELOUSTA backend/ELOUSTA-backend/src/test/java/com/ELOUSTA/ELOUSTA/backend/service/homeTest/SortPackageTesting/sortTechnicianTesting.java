package com.ELOUSTA.ELOUSTA.backend.service.homeTest.SortPackageTesting;


import com.ELOUSTA.ELOUSTA.backend.dto.homeDto.HomeTechnicianDTO;
import com.ELOUSTA.ELOUSTA.backend.entity.DomainEntity;
import com.ELOUSTA.ELOUSTA.backend.entity.TechnicianEntity;
import com.ELOUSTA.ELOUSTA.backend.service.homeTest.HomeTechnicianTestData;
import com.ELOUSTA.ELOUSTA.backend.repository.DomainRepository;
import com.ELOUSTA.ELOUSTA.backend.repository.TechnicianRepository;
import com.ELOUSTA.ELOUSTA.backend.service.home.SortTechnicianService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@SpringBootTest
public class sortTechnicianTesting {

    @Autowired
    private SortTechnicianService sortService;
    @Autowired
    private TechnicianRepository technicianRepository;
    @Autowired
    private DomainRepository domainRepository;

    @BeforeEach
    public void setup() throws ParseException {
//        delete technicians first
        technicianRepository.deleteAll();
//        delete domains
        domainRepository.deleteAll();
//        create domains data test
        List<DomainEntity> domainEntities = List.of(
                HomeTechnicianTestData.domainOneTest(),
                HomeTechnicianTestData.domainTwoTest(),
                HomeTechnicianTestData.domainThreeTest(),
                HomeTechnicianTestData.domainFourTest()
        );
//        save domains data test
        domainRepository.saveAll(domainEntities);
//        get the saved domains data test
        List<DomainEntity> savedDomainEntities = domainRepository.findAll();
//        create technicians data test
        List<TechnicianEntity> technicianEntities = List.of(
                HomeTechnicianTestData.technicianOneTest(savedDomainEntities.get(0)),
                HomeTechnicianTestData.technicianTwoTest(savedDomainEntities.get(1)),
                HomeTechnicianTestData.technicianThreeTest(savedDomainEntities.get(2)),
                HomeTechnicianTestData.technicianFourTest(savedDomainEntities.get(1)),
                HomeTechnicianTestData.technicianFiveTest(savedDomainEntities.get(3))
        );
//        save technicians data test
        technicianRepository.saveAll(technicianEntities);

    }

    @Test
    void sortByExperience() throws IOException {
        List<HomeTechnicianDTO>DTOs=sortService.sortTechnicians("experience");
        Assertions.assertEquals(DTOs.size(),5);
        Assertions.assertEquals(DTOs.get(0).getFirstName(),"Omar");
    }
    @Test
    void sortByRate() throws IOException {
        List<HomeTechnicianDTO>DTOs=sortService.sortTechnicians("rate");
        Assertions.assertEquals(DTOs.size(),5);
        Assertions.assertEquals(DTOs.get(0).getFirstName(),"mohammed");
    }
    @Test
    void NullSort() throws IOException {
        List<HomeTechnicianDTO>DTOs=sortService.sortTechnicians("password");
        Assertions.assertEquals(DTOs.size(),5);
        Assertions.assertEquals(DTOs.get(0).getFirstName(),"youssef"); //they came in same order
    }

    @Test
    void sortElectricalTechsByRate() throws IOException {
        List<HomeTechnicianDTO>DTOs=sortService.sortTechniciansOfASpecificProfession("rate",domainRepository.getDomainByName("Electrical").getId());

        Assertions.assertEquals(2,DTOs.size());
        Assertions.assertEquals("Mahmoud",DTOs.get(0).getFirstName());

    }

    @Test
    void sortElectricalTechsByExperience() throws IOException {
        List<HomeTechnicianDTO>DTOs=sortService.sortTechniciansOfASpecificProfession("experience",domainRepository.getDomainByName("Electrical").getId());

        Assertions.assertEquals(2,DTOs.size());
        Assertions.assertEquals("Omar",DTOs.get(0).getFirstName());
    }

}
