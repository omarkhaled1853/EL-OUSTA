package com.ELOUSTA.Profile_backend;

import com.ELOUSTA.Profile_backend.dto.ClientDTO;
import com.ELOUSTA.Profile_backend.entity.ClientEntity;

import java.sql.Date;

public final class TestData {
    public TestData() {
    }

    public static ClientDTO testClientDTO() {
        return ClientDTO.builder()
                .id(4)
                .name("John Doe")
                .profilePicture("/images/profile/johndoe.png")
                .requests(3)
                .cancelled(1)
                .accepted(4)
                .dob(Date.valueOf("1990-01-15"))
                .userName("johndoe")
                .phoneNumber("1234567890")
                .email("johndoe@example.com")
                .location("California Los Angeles")
                .build();
    }

    public static ClientEntity testClientEntity() {
        return ClientEntity.builder()
                .id(4)
                .dob(Date.valueOf("1990-01-15"))
                .email("johndoe@example.com")
                .firstName("John")
                .lastName("Doe")
                .password("securepassword123")
                .phoneNumber("1234567890")
                .signUpDate(Date.valueOf("2024-12-03"))
                .userName("johndoe")
                .district("Los Angeles")
                .governorate("California")
                .profilePicture("/images/profile/johndoe.png")
                .roles("USER")
                .build();

    }
}
