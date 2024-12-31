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
@IdClass(FeedbackEntityPK.class)
@Table(name = "feedback")
public class FeedbackEntity {

    @Id
    @Column(name = "CLIENTID")
    private int clientId;

    @Id
    @Column(name = "TECHID")
    private int techId;

    @Column(name = "RATE")
    private double rate;

    @Column(name = "COMMENT_BODY")
    private String commentBody;
}
