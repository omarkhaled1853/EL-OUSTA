package com.ELOUSTA.User_profile.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@SuperBuilder
@Data
public class User {
    protected String firstName;
    protected String lastName;
    protected String userName;
    protected String phoneNumber;
    protected Data dob;
    protected String email;
    protected String password;
    protected Data signUpDate;
}
