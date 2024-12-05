package com.ELOUSTA.Profile_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@MappedSuperclass
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
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
    private String profilePicture;
}
