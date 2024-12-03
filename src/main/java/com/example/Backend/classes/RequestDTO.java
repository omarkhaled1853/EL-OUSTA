package com.example.Backend.classes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestDTO {
    private int userID;
    private int technicianID;
    private int ID;
    private List<Byte> Attachment;
    private String state;
    private Date startDate;
    private Date endDate;

}
