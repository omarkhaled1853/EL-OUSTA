package com.ELOUSTA.ELOUSTA.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "admin")
public class AdminEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;
    private boolean fixed;
    private boolean canManipulateProfessions;
    private boolean canAccessComplaints;
    private boolean canAccessTechnician;
    private boolean canAccessUsers;
    private boolean canHireAdmins;
}
