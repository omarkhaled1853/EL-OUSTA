package com.ELOUSTA.ELOUSTA.backend.service.Request.tech;

import com.ELOUSTA.ELOUSTA.backend.dto.requestDto.ViewRequestDTO;
import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;
import com.ELOUSTA.ELOUSTA.backend.repository.RequestRepository;
import com.ELOUSTA.ELOUSTA.backend.service.request.impl.tech.TechnicianRequestSortService;
import com.ELOUSTA.ELOUSTA.backend.service.request.payload.RequestPayload;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class TechnicianRequestSortServiceTest {

    @Autowired
    private RequestRepository repository;
    @Autowired
    private TechnicianRequestSortService service;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    @BeforeEach
    void setup() throws ParseException {

        this.repository.deleteAll();

        // Create the list of RequestEntity objects
        List<RequestEntity> requestEntities = new ArrayList<>();

        // Entity 1
        RequestEntity entity1 = new RequestEntity();
        entity1.setUserId(1);
        entity1.setTechId(3);
        entity1.setState("PENDING");
        entity1.setDescription("Fixing server issues");
        entity1.setLocation("New York");
        entity1.setStartDate(dateFormat.parse("2/2/2024"));
        entity1.setEndDate(dateFormat.parse("5/1/2024"));
        requestEntities.add(entity1);

        // Entity 2
        RequestEntity entity2 = new RequestEntity();
        entity2.setUserId(7);
        entity2.setTechId(3);
        entity2.setState("COMPLETED");
        entity2.setDescription("Testing security patches");
        entity2.setLocation("Seattle");
        entity2.setStartDate(dateFormat.parse("20/03/2024"));
        entity2.setEndDate(dateFormat.parse("25/03/2024"));
        requestEntities.add(entity2);

        // Entity 3
        RequestEntity entity3 = new RequestEntity();
        entity3.setUserId(2);
        entity3.setTechId(3);
        entity3.setState("IN-PROGRESS");
        entity3.setDescription("Installing new hardware");
        entity3.setLocation("Los Angeles");
        entity3.setStartDate(dateFormat.parse("02/02/2024"));
        entity3.setEndDate(dateFormat.parse("10/02/2024"));
        requestEntities.add(entity3);

        // Entity 4
        RequestEntity entity4 = new RequestEntity();
        entity4.setUserId(3);
        entity4.setTechId(1);
        entity4.setState("COMPLETED");
        entity4.setDescription("Updating software");
        entity4.setLocation("San Francisco");
        entity4.setStartDate(dateFormat.parse("15/01/2024"));
        entity4.setEndDate(dateFormat.parse("20/01/2024"));
        requestEntities.add(entity4);

        // Entity 5
        RequestEntity entity5 = new RequestEntity();
        entity5.setUserId(4);
        entity5.setTechId(2);
        entity5.setState("PENDING");
        entity5.setDescription("Maintenance check");
        entity5.setLocation("Chicago");
        entity5.setStartDate(dateFormat.parse("20/02/2024"));
        entity5.setEndDate(dateFormat.parse("25/02/2024"));
        requestEntities.add(entity5);

        // Entity 6
        RequestEntity entity6 = new RequestEntity();
        entity6.setUserId(5);
        entity6.setTechId(3);
        entity6.setState("IN-PROGRESS");
        entity6.setDescription("Troubleshooting network issues");
        entity6.setLocation("Houston");
        entity6.setStartDate(dateFormat.parse("05/03/2024"));
        entity6.setEndDate(dateFormat.parse("15/03/2024"));
        requestEntities.add(entity6);

        // Entity 7
        RequestEntity entity7 = new RequestEntity();
        entity7.setUserId(6);
        entity7.setTechId(4);
        entity7.setState("PENDING");
        entity7.setDescription("Database optimization");
        entity7.setLocation("Miami");
        entity7.setStartDate(dateFormat.parse("10/01/2024"));
        entity7.setEndDate(dateFormat.parse("15/01/2024"));
        requestEntities.add(entity7);

        // Entity 8
        RequestEntity entity8 = new RequestEntity();
        entity8.setUserId(8);
        entity8.setTechId(5);
        entity8.setState("CANCELLED");
        entity8.setDescription("System audit");
        entity8.setLocation("Atlanta");
        entity8.setStartDate(dateFormat.parse("05/02/2024"));
        entity8.setEndDate(dateFormat.parse("10/02/2024"));
        requestEntities.add(entity8);

        // Entity 9
        RequestEntity entity9 = new RequestEntity();
        entity9.setUserId(9);
        entity9.setTechId(7);
        entity9.setState("IN-PROGRESS");
        entity9.setDescription("Cloud migration setup");
        entity9.setLocation("Boston");
        entity9.setStartDate(dateFormat.parse("12/02/2024"));
        entity9.setEndDate(dateFormat.parse("18/02/2024"));
        requestEntities.add(entity9);

        // Entity 10
        RequestEntity entity10 = new RequestEntity();
        entity10.setUserId(10);
        entity10.setTechId(2);
        entity10.setState("COMPLETED");
        entity10.setDescription("Server backup configuration");
        entity10.setLocation("Denver");
        entity10.setStartDate(dateFormat.parse("25/02/2024"));
        entity10.setEndDate(dateFormat.parse("28/02/2024"));
        requestEntities.add(entity10);

        // Save all the entities to the repository
        this.repository.saveAll(requestEntities);
    }


    @Test
    void test1() throws ParseException {

        RequestPayload requestPayload = RequestPayload.builder()
                .id(3)
                .state("PENDING")
                .query("startDate")
                .build();

        List<ViewRequestDTO> answer = service.sortRequests(requestPayload);

        Assertions.assertEquals(answer.size(),1);
        Assertions.assertEquals(dateFormat.parse("2/2/2024"),answer.get(0).getStartDate());
    }
    @Test
    void test2() throws ParseException {

        RequestPayload requestPayload = RequestPayload.builder()
                .id(3)
                .state("IN-PROGRESS")
                .query("endDate")
                .build();

        List<ViewRequestDTO> answer = service.sortRequests(requestPayload);

        Assertions.assertEquals(answer.size(),2);
        Assertions.assertEquals(dateFormat.parse("10/2/2024"),answer.get(0).getEndDate());
    }

}
