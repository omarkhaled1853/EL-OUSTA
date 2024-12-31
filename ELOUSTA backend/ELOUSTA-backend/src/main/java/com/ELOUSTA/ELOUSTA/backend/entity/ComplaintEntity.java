package com.ELOUSTA.ELOUSTA.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "complaint")
public class ComplaintEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Foreign key to ClientEntity
    @JoinColumn(name = "client_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference // To prevents the recursive serialization on the notification side
    private ClientEntity clientEntity;

    // Foreign key to TechnicianEntity

    @JoinColumn(name = "tech_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference // To prevents the recursive serialization on the notification side
    private TechnicianEntity technicianEntity;

    @Column(nullable = false)
    private String complaintBody;

    private String state; // Example: "Pending", "Resolved"

    private int direction; // 1 for tech to client, 0 for client to tech
}
