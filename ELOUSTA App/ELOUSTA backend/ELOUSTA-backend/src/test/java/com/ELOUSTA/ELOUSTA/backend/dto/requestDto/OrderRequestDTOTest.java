package com.ELOUSTA.ELOUSTA.backend.dto.requestDto;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class OrderRequestDTOTest {

    @Test
    void testRequestDto() {
        // Create a new instance of RequestDto
        OrderRequestDTO orderRequestDto = new OrderRequestDTO();

        // Test userid
        orderRequestDto.setClientId(1);
        assertEquals(1, orderRequestDto.getClientId());

        // Test techid
        orderRequestDto.setTechId(2);
        assertEquals(2, orderRequestDto.getTechId());

        // Test description
        String description = "Fix the air conditioner";
        orderRequestDto.setDescription(description);
        assertEquals(description, orderRequestDto.getDescription());

        // Test location
        String location = "123 Main Street";
        orderRequestDto.setLocation(location);
        assertEquals(location, orderRequestDto.getLocation());

        // Test state
        String state = "Pending";
        orderRequestDto.setState(state);
        assertEquals(state, orderRequestDto.getState());

        // Test startdate
        Date startDate = new Date();
        orderRequestDto.setStartDate(startDate);
        assertEquals(startDate, orderRequestDto.getStartDate());

        // Test enddate
        Date endDate = new Date();
        orderRequestDto.setEndDate(endDate);
        assertEquals(endDate, orderRequestDto.getEndDate());
    }
}
