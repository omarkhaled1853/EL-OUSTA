package com.ELOUSTA.Profile_backend.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Builder
@Table(name = "domain")
public class DomainEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String photo;

//    Each domain has many technician
    @OneToMany(mappedBy = "domainEntity", cascade = CascadeType.ALL)
    private List<TechnicianEntity> technicianEntities;
}
