package com.ELOUSTA.Profile_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String name;
    private String profilePicture;
    private int requests;
    private int cancelled;
    private int accepted;
    private Date dob;
    private String userName;
    private String phoneNumber;
    private String email;
    private String location;
}
