package com.ELOUSTA.ELOUSTA.backend.service.adminRequests;

import com.ELOUSTA.ELOUSTA.backend.dto.AdminRequestsDTO;
import com.ELOUSTA.ELOUSTA.backend.entity.ClientEntity;
import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;
import com.ELOUSTA.ELOUSTA.backend.entity.TechnicianEntity;
import com.ELOUSTA.ELOUSTA.backend.repository.ClientRepository;
import com.ELOUSTA.ELOUSTA.backend.repository.TechnicianRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminRequestsMapper {



    private  TechnicianRepository technicianRepository;
    private ClientRepository clientRepository;

    @Autowired
    public AdminRequestsMapper(TechnicianRepository technicinanRepository, ClientRepository clientRepository) {

        this.technicianRepository = technicinanRepository;
        this.clientRepository = clientRepository;
    }

    public List<AdminRequestsDTO> mapRequestEntityToAdminRequestDTO(List<RequestEntity> entities)
    {
        List<AdminRequestsDTO>adminRequestDTOs=new ArrayList<>();
        for (RequestEntity entity:entities) {
            ClientEntity client = clientRepository.findById(entity.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("Client not found with ID: " + entity.getUserId()));

            TechnicianEntity technician = technicianRepository.findById(entity.getTechId())
                    .orElseThrow(() -> new EntityNotFoundException("Technician not found with ID: " + entity.getTechId()));
            adminRequestDTOs.add(new AdminRequestsDTO(technician.getUsername(), client.getUsername(), entity.getStartDate(),entity.getEndDate()));
        }
        return adminRequestDTOs;
    }
}
