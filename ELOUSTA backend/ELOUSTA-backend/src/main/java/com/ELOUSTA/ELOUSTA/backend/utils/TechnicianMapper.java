package com.ELOUSTA.ELOUSTA.backend.utils;

import com.ELOUSTA.ELOUSTA.backend.dto.DomainDTO;
import com.ELOUSTA.ELOUSTA.backend.dto.PortfolioDto;
import com.ELOUSTA.ELOUSTA.backend.dto.TechnicianDTO;
import com.ELOUSTA.ELOUSTA.backend.entity.DomainEntity;
import com.ELOUSTA.ELOUSTA.backend.entity.PortfolioEntity;
import com.ELOUSTA.ELOUSTA.backend.entity.TechnicianEntity;
import com.ELOUSTA.ELOUSTA.backend.service.home.Technician;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.ELOUSTA.ELOUSTA.backend.utils.ImageHandler.getProfilePhoto;

@Service
public final class TechnicianMapper {

    private static final String profilePath = "C:\\images\\profile\\";
    private static final String domainPath = "C:\\images\\domain\\";
    private static final String portfolioPath = "C:\\images\\portfolio\\";

    public static TechnicianDTO technicainToTechnicianDTO(Technician technician) {
        return TechnicianDTO.builder()
                .id(technician.getId())
                .firstName(technician.getFirstName())
                .lastName(technician.getLastName())
                .profilePicture(technician.getProfilePicture())
                .requests(technician.getRequests())
                .cancelled(technician.getCancelled())
                .accepted(technician.getAccepted())
                .dob(technician.getDob())
                .userName(technician.getUserName())
                .phoneNumber(technician.getPhoneNumber())
                .email(technician.getEmail())
                .location(technician.getLocation())
                .rate(technician.getRate())
                .experience(technician.getExperience())
                .portfolioDto(technician.getPortfolioDto())
                .domainDTO(technician.getDomainDTO())
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
                .userName(technicianEntity.getUserName())
                .phoneNumber(technicianEntity.getPhoneNumber())
                .email(technicianEntity.getEmail())
                .rate(technicianEntity.getRate())
                .portfolioDto(portfolioDtoList)
                .domainDTO(domainDTO)
                .build();
    }


    private static TechnicianDTO technicianEntityToTechnicianDto(TechnicianEntity technicianEntity) throws IOException {
        DomainDTO domainDTO = domainEntityToDomainDto(technicianEntity.getDomainEntity());
        List<PortfolioDto> portfolioDtoList = portfolioEntityListToPortfolioDtoList(technicianEntity.getPortfolioEntities());
        byte[] profilePhoto = getProfilePhoto(technicianEntity.getProfilePicture(), profilePath);
        return TechnicianDTO.builder()
                .id(technicianEntity.getId())
                .firstName(technicianEntity.getFirstName())
                .lastName(technicianEntity.getLastName())
                .profilePicture(profilePhoto)
                .dob(technicianEntity.getDob())
                .userName(technicianEntity.getUserName())
                .phoneNumber(technicianEntity.getPhoneNumber())
                .email(technicianEntity.getEmail())
                .location(technicianEntity.getGovernorate() + " "
                        + technicianEntity.getDistrict())
                .rate(technicianEntity.getRate())
                .description(technicianEntity.getDescription())                .domainDTO(domainDTO)
                .portfolioDto(portfolioDtoList)
                .build();
    }

    private static DomainDTO domainEntityToDomainDto(DomainEntity domainEntity) throws IOException {
        byte[] domainPhoto = getProfilePhoto(domainEntity.getPhoto(), domainPath);
        return DomainDTO.builder()
                .id(domainEntity.getId())
                .name(domainEntity.getName())
                .photo(domainPhoto)
                .build();
    }

    private static List<PortfolioDto> portfolioEntityListToPortfolioDtoList(List<PortfolioEntity> portfolioEntityList) {
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
