package com.ELOUSTA.User_profile.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Builder
public class Domain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String photo;

//    Each domain has many technician
    @OneToMany(mappedBy = "domain", cascade = CascadeType.ALL)
    private List<Technician> technicians;
}
