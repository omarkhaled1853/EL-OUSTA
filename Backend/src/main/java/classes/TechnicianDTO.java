package classes;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class TechnicianDTO {
    private int id;
    private String domain;
    private String fName;
    private String lName;
    private String governorate;
    private String district;
    private Date startDate;
    private String rate;

}
