package com.ELOUSTA.ELOUSTA.backend.dto;

import lombok.Data;
@Data
//from table of technicians
public class ProfessionCardDTO {
    private int TechId;
    private String TechName;
    private String Email;
    private String ProfessionName;
    private double rate;
    private int ComplainNumber;  // Count nuber of complain where techid == this techid
}
