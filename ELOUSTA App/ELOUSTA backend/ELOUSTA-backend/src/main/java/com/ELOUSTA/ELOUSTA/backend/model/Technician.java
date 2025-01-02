package com.ELOUSTA.ELOUSTA.backend.model;

import com.ELOUSTA.ELOUSTA.backend.dto.DomainDTO;
import com.ELOUSTA.ELOUSTA.backend.dto.PortfolioDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Technician {
   private Integer id;
   private String firstName;
   private String lastName;
   private byte[] profilePicture;
   private int requests;
   private int cancelled;
   private int accepted;
   @JsonFormat(pattern = "yyyy-MM-dd")
   private Date dob;
   private String userName;
   private String phoneNumber;
   private String email;
   private String city;
   private Double rate;
   @JsonFormat(pattern = "yyyy-MM-dd")
   private Date startDate;
   private String description;
   private List<PortfolioDTO> portfolioDto;
   private DomainDTO domainDTO;
}
