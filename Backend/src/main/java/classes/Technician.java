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
   private String Email;
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


   //WILL be used in the service class
   //TODO:convert technician to DTO in the service class

   public TechnicianDTO technicianToTechnicianDTO()
   {
      TechnicianDTO dto=new TechnicianDTO.TechnicianDTOBuilder()
              .id(this.id)
              .domain(this.domain)
              .fName(this.fName)
              .lName(this.lName)
              .governorate(this.governorate)
              .district(this.district)
              .startDate(this.startDate)
              .rate(this.rate)
              .build();
      return dto;
   }
}
