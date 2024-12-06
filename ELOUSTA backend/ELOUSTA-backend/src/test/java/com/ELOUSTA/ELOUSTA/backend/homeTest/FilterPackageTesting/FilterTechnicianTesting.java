package com.ELOUSTA.ELOUSTA.backend.homeTest.FilterPackageTesting;


import com.ELOUSTA.ELOUSTA.backend.dto.HomeTechnicianDTO;
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

        TechnicianEntity tech1 = FilterTechnicianTestData.technicianOneTest();
        TechnicianEntity tech2 = FilterTechnicianTestData.technicianTwoTest();
        TechnicianEntity tech3 = FilterTechnicianTestData.technicianThreeTest();
        TechnicianEntity tech4 = FilterTechnicianTestData.technicianThreeTest();
        TechnicianEntity tech5 = FilterTechnicianTestData.technicianFourTest();

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
