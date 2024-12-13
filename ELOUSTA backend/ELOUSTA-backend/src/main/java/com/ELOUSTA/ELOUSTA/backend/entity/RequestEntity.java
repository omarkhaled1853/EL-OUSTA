package com.ELOUSTA.ELOUSTA.backend.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "request")
public class RequestEntity {

    private int userID;
    private int techID;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String state;
    private String attachment;
    private Date startDate;
    private Date endDate;
}
