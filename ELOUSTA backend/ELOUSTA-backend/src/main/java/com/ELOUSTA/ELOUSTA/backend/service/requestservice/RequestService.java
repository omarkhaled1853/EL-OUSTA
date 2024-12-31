package com.ELOUSTA.ELOUSTA.backend.service.requestservice;

import com.ELOUSTA.ELOUSTA.backend.entity.AdminEntity;
import com.ELOUSTA.ELOUSTA.backend.entity.DomainEntity;
import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;
import com.ELOUSTA.ELOUSTA.backend.entity.TechnicianEntity;
import com.ELOUSTA.ELOUSTA.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RequestService {

    @Autowired
    private RequestRepo requestRepo;
    @Autowired
    private DomainRepository domainRepository;
    @Autowired
    TechnicianRepository technicianRepository;
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    ClientRepository clientRepository;
    public RequestEntity Saverequest(RequestEntity request) {
        return requestRepo.save(request);
    }


    /// requesters of admin home ///////////////////
    public List<RequestEntity> getRequestService(String state){
        return requestRepo.getRequests(state);
    }
    public  List<DomainEntity> getProfessions()
    {
        return domainRepository.getall();
    }

    public DomainEntity saveProfession(DomainEntity profession)
    {
        return domainRepository.save(profession);
    }
    public List<TechnicianEntity> getprofessiontech()
    {
        return  technicianRepository.findAll();
    }
    public void deletetech(int id)
    {
        technicianRepository.deleteById(id);
    }
    public int technumbers()
    {
        return technicianRepository.findAll().size();
    }
    public int clientnumbers()
    {
        return clientRepository.findAll().size();
    }
    public int complainsnumbers()
    {
        return 62;
//        return complaintRepository.findAll();
    }
    public int requestnumbers()
    {
        return requestRepo.findAll().size();
    }
    public boolean canDomanipulatprofession(int id) {
        // Use findById to retrieve a single admin by ID
        Optional<AdminEntity> adminOptional = adminRepository.findById(id);

        // Check if the admin exists and return the value of canManipulateProfessions
        return adminOptional.map(AdminEntity::isCanManipulateProfessions).orElse(false);
    }
    public boolean canDodelete(int id) {
        // Use findById to retrieve a single admin by ID
        Optional<AdminEntity> adminOptional = adminRepository.findById(id);

        // Check if the admin exists and return the value of canManipulateProfessions
        return adminOptional.map(AdminEntity::isCanAccessTechnician).orElse(false);
    }



}
