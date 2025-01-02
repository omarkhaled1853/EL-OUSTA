package com.ELOUSTA.ELOUSTA.backend.service.adminRequests;

import com.ELOUSTA.ELOUSTA.backend.dto.AdminRequestsDTO;
import com.ELOUSTA.ELOUSTA.backend.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminRequestsService {


    private  RequestRepository repository;
    private  AdminRequestsMapper mapper;

    @Autowired
    public AdminRequestsService(RequestRepository repository, AdminRequestsMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<AdminRequestsDTO> getRequests(String state)
    {
        return mapper.mapRequestEntityToAdminRequestDTO(repository.getAllByState(state));
    }

    public List<AdminRequestsDTO> searchRequests(String technicianUserName,String state)
    {
        return mapper.mapRequestEntityToAdminRequestDTO(repository.searchByTechnicianUserName(technicianUserName,state));
    }


}
