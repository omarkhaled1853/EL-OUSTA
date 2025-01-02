package com.ELOUSTA.ELOUSTA.backend.service.homeTest;

import com.ELOUSTA.ELOUSTA.backend.dto.DomainDTO;
import com.ELOUSTA.ELOUSTA.backend.entity.DomainEntity;
import com.ELOUSTA.ELOUSTA.backend.repository.DomainRepository;
import com.ELOUSTA.ELOUSTA.backend.repository.TechnicianRepository;
import com.ELOUSTA.ELOUSTA.backend.service.home.DomainService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class DomainServiceTest {

    @Autowired
    private DomainService service;
    @Autowired
    private DomainRepository repository;
    @Autowired
    private TechnicianRepository technicianRepository;

    @BeforeEach
    void setUP()
    {
        technicianRepository.deleteAll();
        repository.deleteAll();
        List<DomainEntity>entities=new ArrayList<>();
        DomainEntity entity1= DomainEntity.builder().photo("C:\\Users\\20100\\Desktop\\my code.jpg").name("Building").build();
        DomainEntity entity2= DomainEntity.builder().photo("C:\\Users\\20100\\Desktop\\my code.jpg").name("Electrical").build();
        DomainEntity entity3= DomainEntity.builder().photo("C:\\Users\\20100\\Desktop\\my code.jpg").name("Cleaning").build();
        DomainEntity entity4= DomainEntity.builder().photo("C:\\Users\\20100\\Desktop\\my code.jpg").name("Plumbing").build();
        entities.add(entity1);
        entities.add(entity2);
        entities.add(entity3);
        entities.add(entity4);
        repository.saveAll(entities);

    }

    @Test
    void testReturningDTOs() throws IOException {
        List<DomainDTO>dtos=service.getDomains();
        Assertions.assertEquals(4,dtos.size());
        Assertions.assertEquals("Plumbing",dtos.get(3).getName());
    }

}
