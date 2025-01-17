package com.ELOUSTA.ELOUSTA.backend.controller.Admin.user.requests.page;

import com.ELOUSTA.ELOUSTA.backend.dto.requestDto.ClientRequestCountDTO;
import com.ELOUSTA.ELOUSTA.backend.repository.ClientCountRequest;
import com.ELOUSTA.ELOUSTA.backend.repository.ClientRepository;
import com.ELOUSTA.ELOUSTA.backend.service.admin.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminUserRequestsPage {
    @Autowired
    private ClientCountRequest clientCountRequest;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AdminService adminService;

    @GetMapping("/client/requests")
    public List<ClientRequestCountDTO> getClientRequestsCount(){
        List<Object[]> results = clientCountRequest.findClientRequestCounts();

        List<ClientRequestCountDTO> dtos = new ArrayList<>();

        for(Object[] result: results){
            Integer id = (Integer) result[0];
            String emailAddress = (String) result[1];
            String username = (String) result[2];
            String firstName = (String) result[3];
            String lastName = (String) result[4];
            String phoneNumber = (String) result[5];
            Long cnt = (Long) result[6];

            dtos.add(ClientRequestCountDTO
                    .builder()
                    .id(id)
                    .emailAddress(emailAddress)
                    .username(username)
                    .firstName(firstName)
                    .lastName(lastName)
                    .phoneNumber(phoneNumber)
                    .cnt(cnt)
                    .build());
        }
        System.out.println(dtos.toString());
        return dtos;
    }
    @DeleteMapping("/client/delete")
    public boolean deleteClientFromSystem(@RequestParam int clientId, @RequestParam int adminId){
        if(adminService.adminCanDeleteClient(adminId)){
            clientRepository.deleteById(clientId);
            return true;
        }
        return false;
    }
}
