package com.example.springsecurity.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@SuperBuilder
@AllArgsConstructor
@ToString(callSuper = true)
@Table(name = "client")
public class ClientEntity extends UserEntity{

}