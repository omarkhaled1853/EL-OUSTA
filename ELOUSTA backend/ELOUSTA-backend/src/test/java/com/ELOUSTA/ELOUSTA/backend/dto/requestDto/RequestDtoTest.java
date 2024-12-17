package com.ELOUSTA.ELOUSTA.backend.dto.requestDto;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class RequestDtoTest {

    @Test
    void testRequestDto() {
        // Create a new instance of RequestDto
        RequestDto requestDto = new RequestDto();

        // Test userid
        requestDto.setUserid(1);
        assertEquals(1, requestDto.getUserid());

        // Test techid
        requestDto.setTechid(2);
        assertEquals(2, requestDto.getTechid());

        // Test description
        String description = "Fix the air conditioner";
        requestDto.setDescription(description);
        assertEquals(description, requestDto.getDescription());

        // Test location
        String location = "123 Main Street";
        requestDto.setLocation(location);
        assertEquals(location, requestDto.getLocation());

        // Test state
        String state = "Pending";
        requestDto.setState(state);
        assertEquals(state, requestDto.getState());

        // Test startdate
        Date startDate = new Date();
        requestDto.setStartdate(startDate);
        assertEquals(startDate, requestDto.getStartdate());

        // Test enddate
        Date endDate = new Date();
        requestDto.setEnddate(endDate);
        assertEquals(endDate, requestDto.getEnddate());
    }
}
