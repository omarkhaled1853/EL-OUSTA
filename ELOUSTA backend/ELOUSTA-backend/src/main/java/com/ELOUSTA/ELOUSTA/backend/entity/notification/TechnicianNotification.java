package com.ELOUSTA.ELOUSTA.backend.entity.notification;

import com.ELOUSTA.ELOUSTA.backend.entity.TechnicianEntity;
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
@Table(name = "technicianNotification")
public class TechnicianNotification extends Notification {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "technician_id")
    @JsonBackReference // To prevents the recursive serialization on the notification side
    private TechnicianEntity technicianEntity;
}
