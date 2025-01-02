package com.ELOUSTA.ELOUSTA.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "complaint")
public class ComplaintEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JoinColumn(name = "client_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference("client-complaints")// To prevents the recursive serialization on the notification side
    private ClientEntity clientEntity;

    // Foreign key to TechnicianEntity

    @JoinColumn(name = "tech_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference // To prevents the recursive serialization on the notification side
    private TechnicianEntity technicianEntity;

    @Column(nullable = false)
    private String complaintBody;

    @Column(nullable = false)
    private String state;

//   0 -> client to tech
//   1 -> tech to client
    @Column(nullable = false)
    private int direction;
}
