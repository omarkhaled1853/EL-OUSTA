package com.ELOUSTA.ELOUSTA.backend.entity;

import com.ELOUSTA.ELOUSTA.backend.entity.notification.TechnicianNotification;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date jobStartDate;
    private String description;

    //    Each technician has portfolio of works
    @OneToMany(mappedBy = "technicianEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<PortfolioEntity> portfolioEntities;

    //    Each technician assigned to exactly one domain
    @ManyToOne
    @JoinColumn(name = "domain_id", nullable = false)
    private DomainEntity domainEntity;

    @OneToMany(mappedBy = "technicianEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<TechnicianNotification>technicianNotifications;

//    Each technician has many complaints
    @OneToMany(mappedBy = "technicianEntity", cascade = CascadeType.ALL)
    private List<ComplaintEntity> complaints;
}
