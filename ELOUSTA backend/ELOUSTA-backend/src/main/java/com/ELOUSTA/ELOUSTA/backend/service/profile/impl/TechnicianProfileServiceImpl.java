package com.ELOUSTA.ELOUSTA.backend.service.profile.impl;

import com.ELOUSTA.ELOUSTA.backend.dto.profileDto.TechnicianProfileProfileDTO;
import com.ELOUSTA.ELOUSTA.backend.entity.TechnicianEntity;
import com.ELOUSTA.ELOUSTA.backend.repository.TechnicianRepository;
import com.ELOUSTA.ELOUSTA.backend.service.profile.TechnicianProfileService;
import com.ELOUSTA.ELOUSTA.backend.utils.TechnicianMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;


@Service
public class TechnicianProfileServiceImpl implements TechnicianProfileService {

    @Autowired
    private TechnicianRepository technicianRepository;

    @Override
    public Optional<TechnicianProfileProfileDTO> getTechnician(Integer id) {
        Optional<TechnicianEntity> technicianEntity =  technicianRepository.findTechnicianWithDomainAndPortfolio(id);
        return technicianEntity.map(entity -> {
            try {
                return TechnicianMapper.technicianEntityToTechnicianDto(entity);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
