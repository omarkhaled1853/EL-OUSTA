package com.ELOUSTA.Profile_backend.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@MappedSuperclass
@Data
@SuperBuilder
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstName;
    private String lastName;
    private Date dob;
    private String userName;
    private String phoneNumber;
    private String email;
    private String password;
    private Date signUpDate;
    private String governorate;
    private String district;
    private String roles;
    private String profilePicture;
}
