package com.ELOUSTA.Profile_backend.service;

import com.ELOUSTA.Profile_backend.dto.TechnicianDTO;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface TechnicianService {
    Optional<TechnicianDTO> getTechnician(Integer id);
}
