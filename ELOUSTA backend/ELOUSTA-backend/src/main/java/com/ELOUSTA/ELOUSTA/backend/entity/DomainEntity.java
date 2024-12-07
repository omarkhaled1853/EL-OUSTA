package com.ELOUSTA.ELOUSTA.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "domain")
public class DomainEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String photo;

//    Each domain has many technician
    @OneToMany(mappedBy = "domainEntity")
    private List<TechnicianEntity> technicianEntities;
}
