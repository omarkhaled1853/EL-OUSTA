package com.ELOUSTA.ELOUSTA.backend.entity;

import com.ELOUSTA.ELOUSTA.backend.entity.notification.ClientNotification;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@Table(name = "client")
public class ClientEntity extends UserEntity {
    @OneToMany(mappedBy = "clientEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("client-notifications")
    private List<ClientNotification>clientNotifications;

    @OneToMany(mappedBy = "clientEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("client-complaints")
    private List<ComplaintEntity> complaints;
}
