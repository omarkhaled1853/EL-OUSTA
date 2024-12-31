package com.ELOUSTA.ELOUSTA.backend.entity;

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

//    Many complaints related to one client
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private int clientId;

//    Many complaints related to one technician
    @ManyToOne
    @JoinColumn(name = "tech_id", nullable = false)
    private int techId;

    @Column(nullable = false)
    private String complaintBody;

    @Column(nullable = false)
    private String state;

//   0 -> client to tech
//   1 -> tech to client
    @Column(nullable = false)
    private boolean direction;
}
