package com.ELOUSTA.Profile_backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@SuperBuilder
@AllArgsConstructor
@ToString(callSuper = true)
@Table(name = "client")
public class ClientEntity extends UserEntity {

}
