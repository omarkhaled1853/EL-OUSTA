package com.ELOUSTA.ELOUSTA.backend.dto;

import lombok.Data;

import java.util.Date;

@Data
public class DialogDTO {
    private String TechName;
    private String profession;
    private String City;
    private String EmailAddress;
    private String phoneNumber;
    private double Rate;
    private Date signUpDate;
}
