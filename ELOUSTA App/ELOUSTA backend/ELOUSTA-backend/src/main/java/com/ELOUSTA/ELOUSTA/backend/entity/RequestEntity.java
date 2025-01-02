package com.ELOUSTA.ELOUSTA.backend.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Request")
public class RequestEntity {

    @Column(name = "USERID")
    private int userId;

    @Column(name = "TECHID")
    private int techId;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "STATE")
    private String state;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "LOCATION")
    private String Location;

    @Column(name = "START_DATE")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date startDate;

    @Column(name = "END_DATE")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date endDate;
}
