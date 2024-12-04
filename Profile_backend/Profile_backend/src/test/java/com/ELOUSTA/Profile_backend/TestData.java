package com.ELOUSTA.Profile_backend;

import com.ELOUSTA.Profile_backend.dto.ClientDTO;
import com.ELOUSTA.Profile_backend.dto.PortfolioDto;
import com.ELOUSTA.Profile_backend.dto.TechnicianDTO;
import com.ELOUSTA.Profile_backend.entity.ClientEntity;
import com.ELOUSTA.Profile_backend.entity.DomainEntity;
import com.ELOUSTA.Profile_backend.entity.PortfolioEntity;
import com.ELOUSTA.Profile_backend.entity.TechnicianEntity;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Date;
import java.util.List;

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

    public TechnicianEntity testTechnicianEntity() {
        return TechnicianEntity.builder()
                .id(3)
                .firstName("John")
                .lastName("Doe")
                .dob(Date.valueOf("1985-07-15"))
                .userName("johndoe")
                .phoneNumber("123-456-7890")
                .email("john.doe@email.com")
                .password("password123")
                .signUpDate(Date.valueOf("2010-04-20"))
                .governorate("Cairo")
                .district("Downtown")
                .profilePicture("john.png")
                .rate(4.5)
                .jobStartDate(Date.valueOf("2010-05-01"))
                .description("This is description")
                .portfolioEntities(testPortfolioEntityList())
                .domainEntity(testDomainEntity())
                .build();
    }

    public DomainEntity testDomainEntity() {
        return DomainEntity.builder()
                .id(1)
                .name("Electrician")
                .photo("electrician.png")
                .build();
    }

    public PortfolioEntity testPortfolioEntity() {
        return PortfolioEntity.builder()
                .id(7) // Assuming the portfolio ID is 1
                .technicianEntity(TechnicianEntity.builder().id(3).build())
                .photo("portfolio1.png")
                .build();
    }

    public List<PortfolioEntity> testPortfolioEntityList() {
        return List.of(
                PortfolioEntity.builder()
                        .id(7) // Assuming the portfolio ID is 1
                        .technicianEntity(TechnicianEntity.builder().id(3).build())
                        .photo("portfolio1.png")
                        .build(),
                PortfolioEntity.builder()
                        .id(8) // Assuming the portfolio ID is 1
                        .technicianEntity(TechnicianEntity.builder().id(3).build())
                        .photo("portfolio2.png")
                        .build()
        );
    }


}
