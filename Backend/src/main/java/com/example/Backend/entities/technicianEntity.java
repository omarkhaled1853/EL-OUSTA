package com.example.Backend.entities;

import jakarta.persistence.Entity;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class technicianEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;
    private String domain;
    private String emailAddress;
    private String fName;
    private String lName;
    private Date dob;
    private String phoneNumber;
    private String authProvider;
    private String governorate;
    private String district;
    private Date startDate;
    private String rate;
}