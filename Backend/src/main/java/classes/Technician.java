package classes;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
public class Technician {
   private int id;
   private String userName;
   private String domain;
   private String emailAddress;
   private String fName;
   private String lName;
   @JsonFormat(pattern = "yyyy-MM-dd")
   private Date dob;
   private String phoneNumber;
   private String authProvider;
   private String governorate;
   private String district;
   @JsonFormat(pattern = "yyyy-MM-dd")
   private Date startDate;
   private String rate;

}
