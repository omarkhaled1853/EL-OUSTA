package com.ELOUSTA.ELOUSTA.backend.dto.AdminAddition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminAdditionDTO {
    private String username;
    private String password;
    private String email;
    private boolean canManipulateProfessions;
    private boolean canAccessComplaints;
    private boolean canAccessTechnician;
    private boolean canAccessUsers;
    private boolean canHireAdmins;

}
