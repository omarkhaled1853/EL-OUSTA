package com.ELOUSTA.Profile_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "portfolio")
public class PortfolioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String photo;

//    Each portfolio of works assigned to exactly one technician
    @ManyToOne
    @JoinColumn(name = "technician_id")
    private TechnicianEntity technicianEntity;
}
