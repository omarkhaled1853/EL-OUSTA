package com.ELOUSTA.ELOUSTA.backend.dto;

import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@MappedSuperclass
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private byte[] profilePicture;
    private int requests;
    private int cancelled;
    private int accepted;
    private Date dob;
    private String userName;
    private String phoneNumber;
    private String email;
    private String location;
}
