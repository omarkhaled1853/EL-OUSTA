package com.ELOUSTA.ELOUSTA.backend.service.homeTest;

import com.ELOUSTA.ELOUSTA.backend.entity.DomainEntity;
import com.ELOUSTA.ELOUSTA.backend.entity.TechnicianEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class HomeTechnicianTestData {
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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

    public static TechnicianEntity technicianOneTest (DomainEntity domain) throws ParseException {  //Carpentry
        return TechnicianEntity.builder()
                .username("joe44")
                .password("12345678")
                .domainEntity(domain)
                .emailAddress("john.doe@gmail.com")
                .firstName("youssef")
                .lastName("Mahmoud")
                .dob(sdf.parse("1985-07-20"))
                .phoneNumber("5551234567")
                .city("Alexandria")
                .jobStartDate(sdf.parse("2020-01-15"))
                .rate(1.0)
                .build();
    }

    public static TechnicianEntity technicianTwoTest (DomainEntity domain) throws ParseException {  //Electrical
        return TechnicianEntity.builder()
                .username("Omar123")
                .password("safePassword321")
                .domainEntity(domain)
                .emailAddress("omar@gmail.com")
                .firstName("Omar")
                .lastName("Khaled")
                .dob(sdf.parse("1990-03-25"))
                .phoneNumber("5559876543")
                .city("Cairo")
                .jobStartDate(sdf.parse("2001-05-10"))
                .rate(2.0)
                .build();
    }

    public static TechnicianEntity technicianThreeTest (DomainEntity domain) throws ParseException {  //Cleaning
        return TechnicianEntity.builder()
                .username("Nada312")
                .password("pass456Secure")
                .domainEntity(domain)
                .emailAddress("Nada@gmail.com")
                .firstName("Nada")
                .lastName("Fouad")
                .dob(sdf.parse("1990-11-12"))
                .phoneNumber("5552223333")
                .city("AlBehira")
                .jobStartDate(sdf.parse("2019-08-01"))
                .rate(3.0)
                .build();
    }

    public static TechnicianEntity technicianFourTest (DomainEntity domain) throws ParseException {  //Electrical
        return TechnicianEntity.builder()
                .username("Mahmoud123")
                .password("strongPass789")
                .domainEntity(domain)
                .emailAddress("Mahmoud515@gmail.com")
                .firstName("Mahmoud")
                .lastName("Adds")
                .dob(sdf.parse("1995-01-15"))
                .phoneNumber("01003207216")
                .city("Alexandria")
                .jobStartDate(sdf.parse("2022-03-20"))
                .rate(4.0)
                .build();
    }

    public static TechnicianEntity technicianFiveTest (DomainEntity domain) throws ParseException {  //Building
        return TechnicianEntity.builder()
                .username("Meedo")
                .password("simplePass101")
                .domainEntity(domain)
                .emailAddress("Meedo@gmail.com")
                .firstName("mohammed")
                .lastName("mounir")
                .dob(sdf.parse("1992-05-30"))
                .phoneNumber("5556667777")
                .city("Cairo")
                .jobStartDate(sdf.parse("2021-07-05"))
                .rate(5.0)
                .build();
    }
}