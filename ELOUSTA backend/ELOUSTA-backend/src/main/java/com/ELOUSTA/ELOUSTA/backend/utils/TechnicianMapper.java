package com.ELOUSTA.ELOUSTA.backend.utils;

import com.ELOUSTA.ELOUSTA.backend.dto.DomainDTO;
import com.ELOUSTA.ELOUSTA.backend.dto.homeDto.HomeTechnicianDTO;
import com.ELOUSTA.ELOUSTA.backend.dto.PortfolioDto;
import com.ELOUSTA.ELOUSTA.backend.dto.profileDto.TechnicianProfileProfileDTO;
import com.ELOUSTA.ELOUSTA.backend.entity.DomainEntity;
import com.ELOUSTA.ELOUSTA.backend.entity.PortfolioEntity;
import com.ELOUSTA.ELOUSTA.backend.entity.TechnicianEntity;
import com.ELOUSTA.ELOUSTA.backend.model.Technician;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.ELOUSTA.ELOUSTA.backend.utils.ImageHandler.getProfilePhoto;

public final class TechnicianMapper {

    private static final String profilePath = "C:\\images\\profile\\";
    private static final String domainPath = "C:\\images\\domain\\";
    private static final String portfolioPath = "C:\\images\\portfolio\\";

    public static HomeTechnicianDTO technicainToHomeTechnicianDTO(Technician technician) {
        LocalDate start = technician.getStartDate().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate now = LocalDate.now();
        return HomeTechnicianDTO.builder()
                .id(technician.getId())
                .firstName(technician.getFirstName())
                .lastName(technician.getLastName())
                .city(technician.getCity())
                .rate(technician.getRate())
                .experience((int) ChronoUnit.YEARS.between(start, now))
                .build();
    }

    public static Technician technicianEntityToTechnician(TechnicianEntity technicianEntity) throws IOException {
        DomainDTO domainDTO = domainEntityToDomainDto(technicianEntity.getDomainEntity());
        List<PortfolioDto> portfolioDtoList = portfolioEntityListToPortfolioDtoList(technicianEntity.getPortfolioEntities());
        byte[] profilePhoto = getProfilePhoto(technicianEntity.getProfilePicture(), profilePath);
        return Technician.builder()
                .id(technicianEntity.getId())
                .firstName(technicianEntity.getFirstName())
                .lastName(technicianEntity.getLastName())
                .profilePicture(profilePhoto)
                .dob(technicianEntity.getDob())
                .userName(technicianEntity.getUsername())
                .phoneNumber(technicianEntity.getPhoneNumber())
                .email(technicianEntity.getEmailAddress())
                .rate(technicianEntity.getRate())
                .portfolioDto(portfolioDtoList)
                .city(technicianEntity.getCity())
                .startDate(technicianEntity.getJobStartDate())
                .domainDTO(domainDTO)
                .build();
    }


    public static TechnicianProfileProfileDTO technicianEntityToTechnicianDto(TechnicianEntity technicianEntity) throws IOException {
        DomainDTO domainDTO = domainEntityToDomainDto(technicianEntity.getDomainEntity());
        List<PortfolioDto> portfolioDtoList = portfolioEntityListToPortfolioDtoList(technicianEntity.getPortfolioEntities());
        byte[] profilePhoto = getProfilePhoto(technicianEntity.getProfilePicture(), profilePath);
        return TechnicianProfileProfileDTO.builder()
                .id(technicianEntity.getId())
                .firstName(technicianEntity.getFirstName())
                .lastName(technicianEntity.getLastName())
                .profilePicture(profilePhoto)
                .dob(technicianEntity.getDob())
                .userName(technicianEntity.getUsername())
                .phoneNumber(technicianEntity.getPhoneNumber())
                .email(technicianEntity.getEmailAddress())
                .city(technicianEntity.getCity())
                .rate(technicianEntity.getRate())
                .description(technicianEntity.getDescription())
                .domainDTO(domainDTO)
                .portfolioDto(portfolioDtoList)
                .build();
    }

    public static DomainDTO domainEntityToDomainDto(DomainEntity domainEntity) throws IOException {
        byte[] domainPhoto = getProfilePhoto(domainEntity.getPhoto(), domainPath);
        return DomainDTO.builder()
                .id(domainEntity.getId())
                .name(domainEntity.getName())
                .photo(domainPhoto)
                .build();
    }

    public static List<PortfolioDto> portfolioEntityListToPortfolioDtoList(List<PortfolioEntity> portfolioEntityList) {
        return portfolioEntityList.stream()
                .map(portfolioEntity -> {
                    byte[] photoBytes = null;
                    try {
                        photoBytes = getProfilePhoto(portfolioEntity.getPhoto(), portfolioPath);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    return PortfolioDto.builder()
                            .id(portfolioEntity.getId())
                            .photo(photoBytes)
                            .build();
                })
                .collect(Collectors.toList());
    }

}
