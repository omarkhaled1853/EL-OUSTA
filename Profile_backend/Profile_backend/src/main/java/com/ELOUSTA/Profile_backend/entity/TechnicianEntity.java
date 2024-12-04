package com.ELOUSTA.Profile_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@Table(name = "technician")
public class TechnicianEntity extends UserEntity {
    private Double rate;
    private Date jobStartDate;
    private String description;

//    Each technician has portfolio of works
    @OneToMany(mappedBy = "technicianEntity", cascade = CascadeType.ALL)
    private List<PortfolioEntity> portfolioEntities;

//    Each technician assigned to exactly one domain
    @ManyToOne
    @JoinColumn(name = "domain_id")
    private DomainEntity domainEntity;
}
