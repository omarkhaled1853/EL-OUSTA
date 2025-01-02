package com.ELOUSTA.ELOUSTA.backend.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class RequestEntityTest {

    @Test
    void testRequestEntity() {
        // Create a new instance of RequestEntity
        RequestEntity requestEntity = new RequestEntity();

        // Test userId
        requestEntity.setUserId(1);
        assertEquals(1, requestEntity.getUserId());

        // Test techId
        requestEntity.setTechId(2);
        assertEquals(2, requestEntity.getTechId());

        // Test id
        requestEntity.setId(100);
        assertEquals(100, requestEntity.getId());

        // Test state
        String state = "In Progress";
        requestEntity.setState(state);
        assertEquals(state, requestEntity.getState());

        // Test description
        String description = "Install a new air conditioner";
        requestEntity.setDescription(description);
        assertEquals(description, requestEntity.getDescription());

        // Test location
        String location = "456 Elm Street";
        requestEntity.setLocation(location);
        assertEquals(location, requestEntity.getLocation());

        // Test startDate
        Date startDate = new Date();
        requestEntity.setStartDate(startDate);
        assertEquals(startDate, requestEntity.getStartDate());

        // Test endDate
        Date endDate = new Date();
        requestEntity.setEndDate(endDate);
        assertEquals(endDate, requestEntity.getEndDate());
    }
}
