package com.ELOUSTA.Profile_backend.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
public class Portfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String photo;

//    Each portfolio of works assigned to exactly one technician
    @ManyToOne
    @JoinColumn(name = "technician_id")
    private Technician technician;
}
