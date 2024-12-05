package com.ELOUSTA.Profile_backend.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
@ToString(callSuper = true)
public class ClientDTO extends UserDTO{
}
