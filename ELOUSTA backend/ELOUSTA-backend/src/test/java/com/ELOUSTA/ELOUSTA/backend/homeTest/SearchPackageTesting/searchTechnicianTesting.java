//package com.ELOUSTA.ELOUSTA.backend.homeTest.SearchPackageTesting;
//
//import com.example.Backend.classes.TechnicianDTO;
//import com.example.Backend.entities.technicianEntity;
//import com.example.Backend.repositories.technicianRepository;
//import com.example.Backend.services.searchTechnicianService;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//@SpringBootTest
//public class searchTechnicianTesting {
//
//
//
//    @Autowired
//    private searchTechnicianService searchTechService ;
//
//    @Autowired
//    private technicianRepository Repository;
//
//    @BeforeEach
//    public void setup()
//    {
//        Repository.deleteAll();
//        List<technicianEntity> technicians = new ArrayList<>();
//
//        technicianEntity tech1 = new technicianEntity();
//        tech1.setId(1);
//        tech1.setUsername("joe44");
//        tech1.setPassword("12345678");
//        tech1.setDomain("Carpentry");
//        tech1.setEmailAddress("john.doe@gmail.com");
//        tech1.setFName("youssef");
//        tech1.setLName("Mahmoud");
//        tech1.setDob(new Date(1985, 7, 20));
//        tech1.setPhoneNumber("5551234567");
//        tech1.setAuthProvider("Local");
//        tech1.setGovernorate("Alexandria");
//        tech1.setDistrict("Smouha");
//        tech1.setStartDate(new Date(2020, 1, 15));
//        tech1.setRate("1");
//
//        technicianEntity tech2 = new technicianEntity();
//        tech2.setId(2);
//        tech2.setUsername("Omar123");
//        tech2.setPassword("safePassword321");
//        tech2.setDomain("Electrical");
//        tech2.setEmailAddress("omar@gmail.com");
//        tech2.setFName("Omar");
//        tech2.setLName("Khaled");
//        tech2.setDob(new Date(1990, 3, 25));
//        tech2.setPhoneNumber("5559876543");
//        tech2.setAuthProvider("Google");
//        tech2.setGovernorate("Cairo");
//        tech2.setDistrict("Faisal");
//        tech2.setStartDate(new Date(2021, 5, 10));
//        tech2.setRate("2");
//
//        technicianEntity tech3 = new technicianEntity();
//        tech3.setId(3);
//        tech3.setUsername("Nada312");
//        tech3.setPassword("pass456Secure");
//        tech3.setDomain("Cleaning");
//        tech3.setEmailAddress("Nada@gmail.com");
//        tech3.setFName("Nada");
//        tech3.setLName("Fouad");
//        tech3.setDob(new Date(1990, 11, 12));
//        tech3.setPhoneNumber("5552223333");
//        tech3.setAuthProvider("Local");
//        tech3.setGovernorate("AlBehira");
//        tech3.setDistrict("kafrAlDawar");
//        tech3.setStartDate(new Date(2019, 8, 1));
//        tech3.setRate("3");
//
//        technicianEntity tech4 = new technicianEntity();
//        tech4.setId(4);
//        tech4.setUsername("Mahmoud123");
//        tech4.setPassword("strongPass789");
//        tech4.setDomain("Electrical");
//        tech4.setEmailAddress("Mahmoud515@gmail.com");
//        tech4.setFName("Mahmoud");
//        tech4.setLName("Adds");
//        tech4.setDob(new Date(1995, 1, 15));
//        tech4.setPhoneNumber("01003207216");
//        tech4.setAuthProvider("Google");
//        tech4.setGovernorate("Alexandria");
//        tech4.setDistrict("AlRaml");
//        tech4.setStartDate(new Date(2022, 3, 20));
//        tech4.setRate("4");
//
//        technicianEntity tech5 = new technicianEntity();
//        tech5.setId(5);
//        tech5.setUsername("Meedo");
//        tech5.setPassword("simplePass101");
//        tech5.setDomain("Building");
//        tech5.setEmailAddress("Meedo@gmail.com");
//        tech5.setFName("mohammed");
//        tech5.setLName("mounir");
//        tech5.setDob(new Date(1992, 6, 30));
//        tech5.setPhoneNumber("5556667777");
//        tech5.setAuthProvider("Local");
//        tech5.setGovernorate("Cairo");
//        tech5.setDistrict("6 october");
//        tech5.setStartDate(new Date(2021, 7, 5));
//        tech5.setRate("5");
//
//        technicians.add(tech1);
//        technicians.add(tech2);
//        technicians.add(tech3);
//        technicians.add(tech4);
//        technicians.add(tech5);
//        Repository.saveAll(technicians);
//    }
//    @Test
//    void searchQuery1()
//    {
//        boolean found=false;
//        List<TechnicianDTO>DTOs=searchTechService.searchTechnician("you");
//        for (TechnicianDTO dto:DTOs) {
//            if(dto.getFName().equals("youssef")) {
//                found = true;
//                break;
//            }
//        }
//        Assertions.assertEquals(found,true);
//    }
//
//
//    @Test
//    void searchQuery2()
//    {
//        boolean found=false;
//        List<TechnicianDTO>DTOs=searchTechService.searchTechnician("lec");
//        for (TechnicianDTO dto:DTOs) {
//            if(dto.getDomain().equals("Electrical")) {
//                found = true;
//                break;
//            }
//        }
//        Assertions.assertEquals(found,true);
//    }
//    @Test
//    void searchQuery3()
//    {
//        boolean found=false;
//        List<TechnicianDTO>DTOs=searchTechService.searchTechnician("Alex");
//        for (TechnicianDTO dto:DTOs) {
//            if(dto.getGovernorate().equals("Alexandria")) {
//                found = true;
//                break;
//            }
//        }
//        Assertions.assertEquals(found,true);
//    }
//
//
//}
