package com.ELOUSTA.Profile_backend.service.impl;

import com.ELOUSTA.Profile_backend.dto.ClientDTO;
import com.ELOUSTA.Profile_backend.dto.DomainDTO;
import com.ELOUSTA.Profile_backend.dto.PortfolioDto;
import com.ELOUSTA.Profile_backend.dto.TechnicianDTO;
import com.ELOUSTA.Profile_backend.entity.ClientEntity;
import com.ELOUSTA.Profile_backend.entity.DomainEntity;
import com.ELOUSTA.Profile_backend.entity.PortfolioEntity;
import com.ELOUSTA.Profile_backend.entity.TechnicianEntity;
import com.ELOUSTA.Profile_backend.repository.TechnicianRepository;
import com.ELOUSTA.Profile_backend.service.TechnicianService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TechnicianServiceImpl implements TechnicianService {

    @Autowired
    private TechnicianRepository technicianRepository;

    private final String path = "C:\\images\\profile\\";

    @Override
    public Optional<TechnicianDTO> getTechnician(Integer id) {
        Optional<TechnicianEntity> technicianEntity =  technicianRepository.findTechnicianWithDomainAndPortfolio(id);
        return technicianEntity.map(entity -> {
            try {
                return technicianEntityToTechnicianDto(entity);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private byte[] getProfilePhoto (String filename) throws IOException {
        String filePath = path + filename;
        return Files.readAllBytes(new File(filePath).toPath());
    }


    private TechnicianDTO technicianEntityToTechnicianDto(TechnicianEntity technicianEntity) throws IOException {
        DomainDTO domainDTO = domainEntityToDomainDto(technicianEntity.getDomainEntity());
        List<PortfolioDto> portfolioDtoList = portfolioEntityListToPortfolioDtoList(technicianEntity.getPortfolioEntities());
        byte[] profilePhoto = getProfilePhoto(technicianEntity.getProfilePicture());
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
                .domainDTO(domainDTO)
                .portfolioDto(portfolioDtoList)
                .build();
    }

    private DomainDTO domainEntityToDomainDto(DomainEntity domainEntity) throws IOException {
        byte[] domainPhoto = getProfilePhoto(domainEntity.getPhoto());
        return DomainDTO.builder()
                .id(domainEntity.getId())
                .name(domainEntity.getName())
                .photo(domainPhoto)
                .build();
    }

    private List<PortfolioDto> portfolioEntityListToPortfolioDtoList(List<PortfolioEntity> portfolioEntityList) {
        return portfolioEntityList.stream()
                .map(portfolioEntity -> {
                    byte[] photoBytes = null;
                    try {
                        photoBytes = getProfilePhoto(portfolioEntity.getPhoto());
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
