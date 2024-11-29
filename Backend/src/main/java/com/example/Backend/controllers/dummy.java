package com.example.Backend.controllers;

import com.example.Backend.entities.technicianEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.Backend.repositories.technicianRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin("*")
public class dummy {

    @Autowired
    private technicianRepository repo;
    @GetMapping("/populate")
    public void populateTable()
    {
        List<technicianEntity> technicians = new ArrayList<>();
        System.out.println("uploaded");

        technicians.add(new technicianEntity(1, "johnDoe", "password123", "IT", "john.doe@example.com", "John", "Doe", new Date(1990, 5, 10), "1234567890", "Google", "California", "LA", new Date(2020, 1, 1)));
        technicians.add(new technicianEntity(2, "janeSmith", "securePass!@#", "Networking", "jane.smith@example.com", "Jane", "Smith", new Date(1985, 8, 25), "0987654321", "Microsoft", "Texas", "Houston", new Date(2021, 3, 15)));
        technicians.add(new technicianEntity(3, "mikeJohnson", "mike1234", "Software", "mike.johnson@example.com", "Mike", "Johnson", new Date(1989, 11, 15), "1122334455", "Apple", "New York", "Brooklyn", new Date(2019, 6, 10)));
        technicians.add(new technicianEntity(4, "lucyWang", "lucy123!abc", "Security", "lucy.wang@example.com", "Lucy", "Wang", new Date(1993, 3, 20), "2233445566", "IBM", "California", "San Francisco", new Date(2022, 5, 25)));
        technicians.add(new technicianEntity(5, "kevinBrown", "password!@345", "Database", "kevin.brown@example.com", "Kevin", "Brown", new Date(1992, 7, 12), "3344556677", "Oracle", "Florida", "Miami", new Date(2020, 8, 5)));
        technicians.add(new technicianEntity(6, "emmaDavis", "Emm@1234", "Support", "emma.davis@example.com", "Emma", "Davis", new Date(1995, 1, 30), "4455667788", "Dell", "Nevada", "Las Vegas", new Date(2021, 4, 18)));
        technicians.add(new technicianEntity(7, "oliverMiller", "OliverM@01", "Development", "oliver.miller@example.com", "Oliver", "Miller", new Date(1988, 6, 5), "5566778899", "HP", "California", "San Diego", new Date(2021, 9, 12)));
        technicians.add(new technicianEntity(8, "sophiaWilson", "Sophia@2024", "Infrastructure", "sophia.wilson@example.com", "Sophia", "Wilson", new Date(1991, 9, 17), "6677889900", "Cisco", "Texas", "Austin", new Date(2019, 11, 22)));
        technicians.add(new technicianEntity(9, "liamMoore", "Liam1234@", "DevOps", "liam.moore@example.com", "Liam", "Moore", new Date(1994, 12, 25), "7788990011", "Intel", "Ohio", "Columbus", new Date(2022, 2, 28)));
        technicians.add(new technicianEntity(10, "chloeTaylor", "Chloe!@789", "Cloud", "chloe.taylor@example.com", "Chloe", "Taylor", new Date(1996, 4, 3), "8899001122", "Amazon", "Oregon", "Portland", new Date(2023, 7, 30)));
        repo.saveAll(technicians);
    }
}
