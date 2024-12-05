package com.ELOUSTA.ELOUSTA.backend.service.home;

import com.ELOUSTA.ELOUSTA.backend.dto.DomainDTO;
import com.ELOUSTA.ELOUSTA.backend.dto.PortfolioDto;
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
   private String location;
   private Double rate;
   private int experience;
   private String description;
   private List<PortfolioDto> portfolioDto;
   private DomainDTO domainDTO;
}
