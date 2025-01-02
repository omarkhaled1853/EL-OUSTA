//package com.example.springsecurity.entity;
//
//import org.junit.jupiter.api.Test;
//import java.util.Date;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//class TechnicianEntityTest {
//
//    @Test
//    void testNoArgsConstructor() {
//        TechnicianEntity technicianEntity = new TechnicianEntity();
//        assertThat(technicianEntity.getId()).isZero();
//        assertThat(technicianEntity.getUsername()).isNull();
//        assertThat(technicianEntity.getPassword()).isNull();
//        assertThat(technicianEntity.getEmailAddress()).isNull();
//        assertThat(technicianEntity.getFirstName()).isNull();
//        assertThat(technicianEntity.getLastName()).isNull();
//        assertThat(technicianEntity.getDob()).isNull();
//        assertThat(technicianEntity.getPhoneNumber()).isNull();
//        assertThat(technicianEntity.getCity()).isNull();
//        assertThat(technicianEntity.getRoles()).isNull();
//        assertThat(technicianEntity.getDomain()).isNull();
//        assertThat(technicianEntity.getJobStartDate()).isNull();
//    }
//
//    @Test
//    void testAllArgsConstructor() {
//        Date dob = new Date();
//        Date startDate = new Date();
//        TechnicianEntity technicianEntity = new TechnicianEntity(
//                1, "testUser", "testPass", "test@example.com",
//                "John", "Doe", dob, "123456789",
//                "TestCity", "ROLE_TECHNICIAN", "TestDomain", startDate
//        );
//
//        assertThat(technicianEntity.getId()).isEqualTo(1);
//        assertThat(technicianEntity.getUsername()).isEqualTo("testUser");
//        assertThat(technicianEntity.getPassword()).isEqualTo("testPass");
//        assertThat(technicianEntity.getEmailAddress()).isEqualTo("test@example.com");
//        assertThat(technicianEntity.getFirstName()).isEqualTo("John");
//        assertThat(technicianEntity.getLastName()).isEqualTo("Doe");
//        assertThat(technicianEntity.getDob()).isEqualTo(dob);
//        assertThat(technicianEntity.getPhoneNumber()).isEqualTo("123456789");
//        assertThat(technicianEntity.getCity()).isEqualTo("TestCity");
//        assertThat(technicianEntity.getRoles()).isEqualTo("ROLE_TECHNICIAN");
//        assertThat(technicianEntity.getDomain()).isEqualTo("TestDomain");
//        assertThat(technicianEntity.getJobStartDate()).isEqualTo(startDate);
//    }
//
//    @Test
//    void testSettersAndGetters() {
//        TechnicianEntity technicianEntity = new TechnicianEntity();
//        Date dob = new Date();
//        Date startDate = new Date();
//
//        technicianEntity.setId(1);
//        technicianEntity.setUsername("newUser");
//        technicianEntity.setPassword("newPass");
//        technicianEntity.setEmailAddress("new@example.com");
//        technicianEntity.setFirstName("Jane");
//        technicianEntity.setLastName("Smith");
//        technicianEntity.setDob(dob);
//        technicianEntity.setPhoneNumber("987654321");
//        technicianEntity.setCity("NewCity");
//        technicianEntity.setRoles("ROLE_MANAGER");
//        technicianEntity.setDomain("NewDomain");
//        technicianEntity.setJobStartDate(startDate);
//
//        assertThat(technicianEntity.getId()).isEqualTo(1);
//        assertThat(technicianEntity.getUsername()).isEqualTo("newUser");
//        assertThat(technicianEntity.getPassword()).isEqualTo("newPass");
//        assertThat(technicianEntity.getEmailAddress()).isEqualTo("new@example.com");
//        assertThat(technicianEntity.getFirstName()).isEqualTo("Jane");
//        assertThat(technicianEntity.getLastName()).isEqualTo("Smith");
//        assertThat(technicianEntity.getDob()).isEqualTo(dob);
//        assertThat(technicianEntity.getPhoneNumber()).isEqualTo("987654321");
//        assertThat(technicianEntity.getCity()).isEqualTo("NewCity");
//        assertThat(technicianEntity.getRoles()).isEqualTo("ROLE_MANAGER");
//        assertThat(technicianEntity.getDomain()).isEqualTo("NewDomain");
//        assertThat(technicianEntity.getJobStartDate()).isEqualTo(startDate);
//    }
//
//    @Test
//    void testToString() {
//        Date dob = new Date();
//        Date startDate = new Date();
//        TechnicianEntity technicianEntity = TechnicianEntity.builder()
//                .id(1)
//                .username("testUser")
//                .password("testPass")
//                .emailAddress("test@example.com")
//                .firstName("John")
//                .lastName("Doe")
//                .dob(dob)
//                .phoneNumber("123456789")
//                .city("TestCity")
//                .roles("ROLE_TECHNICIAN")
//                .domain("TestDomain")
//                .startDate(startDate)
//                .build();
//
//        String expectedString = "Technician(id=1, username=testUser, password=testPass, emailAddress=test@example.com, " +
//                "firstName=John, lastName=Doe, dob=" + dob + ", phoneNumber=123456789, city=TestCity, " +
//                "roles=ROLE_TECHNICIAN, domain=TestDomain, startDate=" + startDate + ")";
//        assertThat(technicianEntity.toString()).isEqualTo(expectedString);
//    }
//
//    @Test
//    void testEqualsAndHashCode() {
//        Date dob = new Date();
//        Date startDate = new Date();
//
//
//
//        TechnicianEntity technicianEntity1 = new TechnicianEntity(1, "testUser", "testPass", "test@example.com",
//                "John", "Doe", dob, "123456789", "TestCity", "ROLE_TECHNICIAN", "TestDomain", startDate);
//
//        TechnicianEntity technicianEntity2 = new TechnicianEntity(1, "testUser", "testPass", "test@example.com",
//                "John", "Doe", dob, "123456789", "TestCity", "ROLE_TECHNICIAN", "TestDomain", startDate);
//
//        assertThat(technicianEntity1).isEqualTo(technicianEntity2);
//        assertThat(technicianEntity1.hashCode()).isEqualTo(technicianEntity2.hashCode());
//
//        TechnicianEntity technicianEntity3 = new TechnicianEntity(2, "diffUser", "diffPass", "diff@example.com",
//                "Jane", "Smith", dob, "987654321", "DiffCity", "ROLE_ADMIN", "DiffDomain", startDate);
//
//        assertThat(technicianEntity1).isNotEqualTo(technicianEntity3);
//        assertThat(technicianEntity1.hashCode()).isNotEqualTo(technicianEntity3.hashCode());
//    }
//}
