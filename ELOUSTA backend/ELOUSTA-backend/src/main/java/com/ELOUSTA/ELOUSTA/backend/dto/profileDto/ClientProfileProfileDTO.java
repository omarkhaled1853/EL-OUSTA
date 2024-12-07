package com.ELOUSTA.ELOUSTA.backend.dto.profileDto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
@ToString(callSuper = true)
public class ClientProfileProfileDTO extends UserProfileDTO {
}
