package com.ELOUSTA.Profile_backend.entity;

import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@SuperBuilder
public class ClientEntity extends UserEntity {
}
