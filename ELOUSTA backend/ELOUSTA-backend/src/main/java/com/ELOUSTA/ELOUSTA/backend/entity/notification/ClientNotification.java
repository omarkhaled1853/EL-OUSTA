package com.ELOUSTA.ELOUSTA.backend.entity.notification;

import com.ELOUSTA.ELOUSTA.backend.entity.ClientEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@Table(name = "clientNotification")
public class ClientNotification extends Notification {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    @JsonBackReference("client-notifications")
    private ClientEntity clientEntity;
}
