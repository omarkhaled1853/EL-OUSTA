package com.ELOUSTA.ELOUSTA.backend.homeTest.SearchPackageTesting;

import com.ELOUSTA.ELOUSTA.backend.dto.homeDto.HomeTechnicianDTO;
import com.ELOUSTA.ELOUSTA.backend.entity.DomainEntity;
import com.ELOUSTA.ELOUSTA.backend.entity.TechnicianEntity;
import com.ELOUSTA.ELOUSTA.backend.homeTest.HomeTechnicianTestData;
import com.ELOUSTA.ELOUSTA.backend.repository.DomainRepository;
import com.ELOUSTA.ELOUSTA.backend.repository.TechnicianRepository;
import com.ELOUSTA.ELOUSTA.backend.service.home.SearchTechnicianService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

@SpringBootTest
public class searchTechnicianTesting {



    @Autowired
    private SearchTechnicianService searchTechService ;

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
    void searchQuery1() throws IOException {
        boolean found=false;
        List<HomeTechnicianDTO>DTOs=searchTechService.searchTechnician("you");
        for (HomeTechnicianDTO dto:DTOs) {
            if(dto.getFirstName().equals("youssef")) {
                found = true;
                break;
            }
        }
        Assertions.assertEquals(found,true);
    }


    @Test
    void searchQuery2() throws IOException {
        boolean found=false;
        List<HomeTechnicianDTO>DTOs=searchTechService.searchTechnician("lec");
        for (HomeTechnicianDTO dto:DTOs) {
            if(dto.getDomainDTO().getName().equals("Electrical")) {
                found = true;
                break;
            }
        }
        Assertions.assertEquals(found,true);
    }
    @Test
    void searchQuery3() throws IOException {
        boolean found=false;
        List<HomeTechnicianDTO>DTOs=searchTechService.searchTechnician("Alex");
        for (HomeTechnicianDTO dto:DTOs) {
            if(dto.getCity().equals("Alexandria")) {
                found = true;
                break;
            }
        }
        Assertions.assertEquals(found,true);
    }


}
