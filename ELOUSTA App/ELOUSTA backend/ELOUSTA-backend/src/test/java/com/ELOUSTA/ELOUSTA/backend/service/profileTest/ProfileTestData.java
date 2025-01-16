package com.ELOUSTA.ELOUSTA.backend.service.profileTest;


import com.ELOUSTA.ELOUSTA.backend.dto.DomainDTO;
import com.ELOUSTA.ELOUSTA.backend.dto.PortfolioDTO;
import com.ELOUSTA.ELOUSTA.backend.dto.profileDto.ClientProfileDTO;
import com.ELOUSTA.ELOUSTA.backend.dto.profileDto.TechnicianProfileDTO;
import com.ELOUSTA.ELOUSTA.backend.entity.ClientEntity;
import com.ELOUSTA.ELOUSTA.backend.entity.DomainEntity;
import com.ELOUSTA.ELOUSTA.backend.entity.PortfolioEntity;
import com.ELOUSTA.ELOUSTA.backend.entity.TechnicianEntity;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import static com.ELOUSTA.ELOUSTA.backend.utils.ImageHandler.getProfilePhoto;


public final class ProfileTestData {
    public ProfileTestData() {
    }
    private static final String profilePath = "C:\\images\\profile\\";
    private static final String domainPath = "C:\\images\\domain\\";
    private static final String portfolioPath = "C:\\images\\portfolio\\";



    public static ClientProfileDTO testClientProfileDTO() throws IOException {
        byte[] profilePhoto = getProfilePhoto("johndoe.png", profilePath);
        return ClientProfileDTO.builder()
                .id(1)
                .firstName("John")
                .lastName("Doe")
                .profilePicture(profilePhoto)
                .pending(0)
                .inProgress(0)
                .completed(0)
                .dob(Date.valueOf("1990-01-15"))
                .userName("johndoe")
                .phoneNumber("1234567890")
                .email("johndoe@example.com")
                .city("Cairo")
                .build();
    }

    public static ClientEntity testClientEntity() {
        return ClientEntity.builder()
                .id(1)
                .dob(Date.valueOf("1990-01-15"))
                .emailAddress("johndoe@example.com")
                .firstName("John")
                .lastName("Doe")
                .password("securepassword123")
                .phoneNumber("1234567890")
                .signUpDate(Date.valueOf("2024-12-03"))
                .username("johndoe")
                .city("Cairo")
                .profilePicture("johndoe.png")
                .build();

    }

    public static TechnicianEntity testTechnicianEntity() {
        return TechnicianEntity.builder()
                .id(1)
                .firstName("John")
                .lastName("Doe")
                .dob(Date.valueOf("1985-07-15"))
                .username("johndoe")
                .phoneNumber("123-456-7890")
                .emailAddress("john.doe@email.com")
                .password("password123")
                .signUpDate(Date.valueOf("2010-04-20"))
                .city("Alexandria")
                .profilePicture("john.png")
                .rate(4.5)
                .jobStartDate(Date.valueOf("2010-05-01"))
                .description("This is description")
                .portfolioEntities(testPortfolioEntityList())
                .domainEntity(testDomainEntity())
                .build();
    }

    public static DomainEntity testDomainEntity() {
        return DomainEntity.builder()
                .id(1)
                .name("Electrician")
                .photo("electrician.png")
                .build();
    }

    public static PortfolioEntity testPortfolioEntity() {
        return PortfolioEntity.builder()
                .id(3)
                .technicianEntity(TechnicianEntity.builder().id(1).build())
                .photo("portfolio1.png")
                .build();
    }

    public static List<PortfolioEntity> testPortfolioEntityList() {
        return List.of(
                PortfolioEntity.builder()
                        .id(3)
                        .technicianEntity(TechnicianEntity.builder().id(1).build())
                        .photo("portfolio1.png")
                        .build(),
                PortfolioEntity.builder()
                        .id(4)
                        .technicianEntity(TechnicianEntity.builder().id(1).build())
                        .photo("portfolio2.png")
                        .build()
        );
    }


    public static TechnicianProfileDTO testTechnicianProfileDto() throws IOException {
        byte[] profilePhoto = getProfilePhoto("john.png", profilePath);
        return TechnicianProfileDTO.builder()
                .id(1)
                .firstName("John")
                .lastName("Doe")
                .dob(Date.valueOf("1985-07-15"))
                .userName("johndoe")
                .phoneNumber("123-456-7890")
                .email("john.doe@email.com")
                .city("Alexandria")
                .profilePicture(profilePhoto)
                .rate(4.5)
                .description("This is description")
                .portfolioDto(testPortfolioDtoList())
                .domainDTO(testDomainDto())
                .build();
    }

    public static DomainDTO testDomainDto() throws IOException {
        byte[] photo = getProfilePhoto("electrician.png", domainPath);
        return DomainDTO.builder()
                .id(1)
                .name("Electrician")
                .photo(photo)
                .build();
    }

    public static PortfolioDTO testPortfolioDto() throws IOException {
        byte[] photo = getProfilePhoto("portfolio1.png", portfolioPath);
        return PortfolioDTO.builder()
                .id(3)
                .photo(photo)
                .build();
    }

    public static List<PortfolioDTO> testPortfolioDtoList() throws IOException {
        byte[] photo1 = getProfilePhoto("portfolio1.png", portfolioPath);
        byte[] photo2 = getProfilePhoto("portfolio2.png", portfolioPath);
        return List.of(
                PortfolioDTO.builder()
                        .id(3)
                        .photo(photo1)
                        .build(),
                PortfolioDTO.builder()
                        .id(4)
                        .photo(photo2)
                        .build()
        );
    }
}
