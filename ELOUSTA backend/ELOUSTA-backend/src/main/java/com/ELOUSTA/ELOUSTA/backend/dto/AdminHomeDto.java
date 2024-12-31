package com.ELOUSTA.ELOUSTA.backend.dto;

import lombok.Data;

import java.util.Date;

@Data
public class AdminHomeDto {
    private String techName; // Renamed from Techname
    private String location;
    private Date date;
    private String description;
}