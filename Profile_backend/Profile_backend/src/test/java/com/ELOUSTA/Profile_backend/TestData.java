package com.ELOUSTA.Profile_backend;

import com.ELOUSTA.Profile_backend.dto.ClientDTO;
import com.ELOUSTA.Profile_backend.entity.ClientEntity;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Date;

import static com.ELOUSTA.Profile_backend.utils.ImageHandler.getProfilePhoto;

public final class TestData {
    public TestData() {
    }
    private static final String path = "C:\\images\\profile\\";


    public static ClientDTO testClientDTO() throws IOException {
        byte[] profilePhoto = getProfilePhoto("johndoe.png", path);
        return ClientDTO.builder()
                .id(1)
                .firstName("John")
                .lastName("Doe")
                .profilePicture(profilePhoto)
                .requests(0)
                .cancelled(0)
                .accepted(0)
                .dob(Date.valueOf("1990-01-15"))
                .userName("johndoe")
                .phoneNumber("1234567890")
                .email("johndoe@example.com")
                .location("California Los Angeles")
                .build();
    }

    public static ClientEntity testClientEntity() {
        return ClientEntity.builder()
                .id(1)
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
                .profilePicture("johndoe.png")
                .build();

    }
}
