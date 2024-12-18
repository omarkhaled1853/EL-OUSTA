package com.ELOUSTA.ELOUSTA.backend.service.homeTest;

import com.ELOUSTA.ELOUSTA.backend.entity.DomainEntity;
import com.ELOUSTA.ELOUSTA.backend.entity.TechnicianEntity;

import java.util.Date;

public final class HomeTechnicianTestData {

    public static DomainEntity domainOneTest() {
        return DomainEntity.builder()
                .name("Carpentry")
                .build();
    }

    public static DomainEntity domainTwoTest() {
        return DomainEntity.builder()
                .name("Electrical")
                .build();
    }

    public static DomainEntity domainThreeTest() {
        return DomainEntity.builder()
                .name("Cleaning")
                .build();
    }

    public static DomainEntity domainFourTest() {
        return DomainEntity.builder()
                .name("Building")
                .build();
    }

    public static TechnicianEntity technicianOneTest (DomainEntity domain) {
        return TechnicianEntity.builder()
                .username("joe44")
                .password("12345678")
                .domainEntity(domain)
                .emailAddress("john.doe@gmail.com")
                .firstName("youssef")
                .lastName("Mahmoud")
                .dob(new Date(1985, 7, 20))
                .phoneNumber("5551234567")
                .city("Alexandria")
                .jobStartDate(new Date(2020, 1, 15))
                .rate(1.0)
                .build();
    }

    public static TechnicianEntity technicianTwoTest (DomainEntity domain) {
        return TechnicianEntity.builder()
                .username("Omar123")
                .password("safePassword321")
                .domainEntity(domain)
                .emailAddress("omar@gmail.com")
                .firstName("Omar")
                .lastName("Khaled")
                .dob(new Date(1990, 3, 25))
                .phoneNumber("5559876543")
                .city("Cairo")
                .jobStartDate(new Date(2001, 5, 10))
                .rate(2.0)
                .build();
    }

    public static TechnicianEntity technicianThreeTest (DomainEntity domain) {
        return TechnicianEntity.builder()
                .username("Nada312")
                .password("pass456Secure")
                .domainEntity(domain)
                .emailAddress("Nada@gmail.com")
                .firstName("Nada")
                .lastName("Fouad")
                .dob(new Date(1990, 11, 12))
                .phoneNumber("5552223333")
                .city("AlBehira")
                .jobStartDate(new Date(2019, 8, 1))
                .rate(3.0)
                .build();
    }

    public static TechnicianEntity technicianFourTest (DomainEntity domain)  {
        return TechnicianEntity.builder()
                .username("Mahmoud123")
                .password("strongPass789")
                .domainEntity(domain)
                .emailAddress("Mahmoud515@gmail.com")
                .firstName("Mahmoud")
                .lastName("Adds")
                .dob(new Date(1995, 1, 15))
                .phoneNumber("01003207216")
                .city("Alexandria")
                .jobStartDate(new Date(2022, 3, 20))
                .rate(4.0)
                .build();
    }

    public static TechnicianEntity technicianFiveTest (DomainEntity domain)  {
        return TechnicianEntity.builder()
                .username("Meedo")
                .password("simplePass101")
                .domainEntity(domain)
                .emailAddress("Meedo@gmail.com")
                .firstName("mohammed")
                .lastName("mounir")
                .dob(new Date(1992, 6, 30))
                .phoneNumber("5556667777")
                .city("Cairo")
                .jobStartDate(new Date(2021, 7, 5))
                .rate(5.0)
                .build();
    }
}