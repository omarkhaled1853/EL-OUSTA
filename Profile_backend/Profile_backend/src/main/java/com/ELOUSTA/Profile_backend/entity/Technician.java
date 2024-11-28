package com.ELOUSTA.Profile_backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@SuperBuilder
public class Technician extends User{
    private Double rate;
    private Date jobStartDate;

//    Each technician has portfolio of works
    @OneToMany(mappedBy = "technician", cascade = CascadeType.ALL)
    private List<Portfolio> portfolios;

//    Each technician assigned to exactly one domain
    @ManyToOne
    @JoinColumn(name = "domain_id")
    private Domain domain;
}
