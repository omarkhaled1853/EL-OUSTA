package com.ELOUSTA.Profile_backend.service.impl;

import com.ELOUSTA.Profile_backend.dto.TechnicianDTO;
import com.ELOUSTA.Profile_backend.entity.TechnicianEntity;
import com.ELOUSTA.Profile_backend.repository.TechnicianRepository;
import com.ELOUSTA.Profile_backend.service.TechnicianService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class TechnicianServiceImpl implements TechnicianService {

    @Override
    public Optional<TechnicianDTO> getTechnician(Integer id) {
        return Optional.empty();
    }
}
