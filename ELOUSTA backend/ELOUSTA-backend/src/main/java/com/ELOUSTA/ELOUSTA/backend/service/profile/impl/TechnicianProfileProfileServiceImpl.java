package com.ELOUSTA.ELOUSTA.backend.service.profile.impl;

import com.ELOUSTA.Profile_backend.dto.DomainDTO;
import com.ELOUSTA.Profile_backend.dto.PortfolioDto;
import com.ELOUSTA.Profile_backend.dto.TechnicianDTO;
import com.ELOUSTA.Profile_backend.entity.DomainEntity;
import com.ELOUSTA.Profile_backend.entity.PortfolioEntity;
import com.ELOUSTA.Profile_backend.entity.TechnicianEntity;
import com.ELOUSTA.Profile_backend.repository.TechnicianRepository;
import com.ELOUSTA.Profile_backend.service.TechnicianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ELOUSTA.Profile_backend.utils.ImageHandler.getProfilePhoto;

@Service
public class TechnicianProfileProfileServiceImpl implements TechnicianService {

    @Autowired
    private TechnicianRepository technicianRepository;

    private final String profilePath = "C:\\images\\profile\\";
    private final String domainPath = "C:\\images\\domain\\";
    private final String portfolioPath = "C:\\images\\portfolio\\";

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

    private TechnicianDTO technicianEntityToTechnicianDto(TechnicianEntity technicianEntity) throws IOException {
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

    private DomainDTO domainEntityToDomainDto(DomainEntity domainEntity) throws IOException {
        byte[] domainPhoto = getProfilePhoto(domainEntity.getPhoto(), domainPath);
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
