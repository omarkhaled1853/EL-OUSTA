//package com.ELOUSTA.ELOUSTA.backend.controller.AdminAddition;
//
//import com.ELOUSTA.ELOUSTA.backend.service.AdminAddition.AdminAdditionDTO;
//import com.ELOUSTA.ELOUSTA.backend.service.AdminAddition.AdminAdditionService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class AdminAdditionController {
//
//
//    @Autowired
//    private AdminAdditionService adminAdditionService;
//
//
//    @PostMapping("/admin/addAdmin/{id}")
//    public boolean addAdmin(@PathVariable int id, @RequestBody AdminAdditionDTO dto)
//    {
//        return adminAdditionService.addAdmin(id,dto);
//    }
//
//}
