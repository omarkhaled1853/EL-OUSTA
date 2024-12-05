package com.example.springsecurity.entity;

import org.junit.jupiter.api.Test;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class TechnicianTest {

    @Test
    void testNoArgsConstructor() {
        Technician technician = new Technician();
        assertThat(technician.getId()).isZero();
        assertThat(technician.getUsername()).isNull();
        assertThat(technician.getPassword()).isNull();
        assertThat(technician.getEmailAddress()).isNull();
        assertThat(technician.getFirstName()).isNull();
        assertThat(technician.getLastName()).isNull();
        assertThat(technician.getDob()).isNull();
        assertThat(technician.getPhoneNumber()).isNull();
        assertThat(technician.getCity()).isNull();
        assertThat(technician.getRoles()).isNull();
        assertThat(technician.getDomain()).isNull();
        assertThat(technician.getStartDate()).isNull();
    }

    @Test
    void testAllArgsConstructor() {
        Date dob = new Date();
        Date startDate = new Date();
        Technician technician = new Technician(
                1, "testUser", "testPass", "test@example.com",
                "John", "Doe", dob, "123456789",
                "TestCity", "ROLE_TECHNICIAN", "TestDomain", startDate
        );

        assertThat(technician.getId()).isEqualTo(1);
        assertThat(technician.getUsername()).isEqualTo("testUser");
        assertThat(technician.getPassword()).isEqualTo("testPass");
        assertThat(technician.getEmailAddress()).isEqualTo("test@example.com");
        assertThat(technician.getFirstName()).isEqualTo("John");
        assertThat(technician.getLastName()).isEqualTo("Doe");
        assertThat(technician.getDob()).isEqualTo(dob);
        assertThat(technician.getPhoneNumber()).isEqualTo("123456789");
        assertThat(technician.getCity()).isEqualTo("TestCity");
        assertThat(technician.getRoles()).isEqualTo("ROLE_TECHNICIAN");
        assertThat(technician.getDomain()).isEqualTo("TestDomain");
        assertThat(technician.getStartDate()).isEqualTo(startDate);
    }

    @Test
    void testSettersAndGetters() {
        Technician technician = new Technician();
        Date dob = new Date();
        Date startDate = new Date();

        technician.setId(1);
        technician.setUsername("newUser");
        technician.setPassword("newPass");
        technician.setEmailAddress("new@example.com");
        technician.setFirstName("Jane");
        technician.setLastName("Smith");
        technician.setDob(dob);
        technician.setPhoneNumber("987654321");
        technician.setCity("NewCity");
        technician.setRoles("ROLE_MANAGER");
        technician.setDomain("NewDomain");
        technician.setStartDate(startDate);

        assertThat(technician.getId()).isEqualTo(1);
        assertThat(technician.getUsername()).isEqualTo("newUser");
        assertThat(technician.getPassword()).isEqualTo("newPass");
        assertThat(technician.getEmailAddress()).isEqualTo("new@example.com");
        assertThat(technician.getFirstName()).isEqualTo("Jane");
        assertThat(technician.getLastName()).isEqualTo("Smith");
        assertThat(technician.getDob()).isEqualTo(dob);
        assertThat(technician.getPhoneNumber()).isEqualTo("987654321");
        assertThat(technician.getCity()).isEqualTo("NewCity");
        assertThat(technician.getRoles()).isEqualTo("ROLE_MANAGER");
        assertThat(technician.getDomain()).isEqualTo("NewDomain");
        assertThat(technician.getStartDate()).isEqualTo(startDate);
    }

    @Test
    void testToString() {
        Date dob = new Date();
        Date startDate = new Date();
        Technician technician = Technician.builder()
                .id(1)
                .username("testUser")
                .password("testPass")
                .emailAddress("test@example.com")
                .firstName("John")
                .lastName("Doe")
                .dob(dob)
                .phoneNumber("123456789")
                .city("TestCity")
                .roles("ROLE_TECHNICIAN")
                .domain("TestDomain")
                .startDate(startDate)
                .build();

        String expectedString = "Technician(id=1, username=testUser, password=testPass, emailAddress=test@example.com, " +
                "firstName=John, lastName=Doe, dob=" + dob + ", phoneNumber=123456789, city=TestCity, " +
                "roles=ROLE_TECHNICIAN, domain=TestDomain, startDate=" + startDate + ")";
        assertThat(technician.toString()).isEqualTo(expectedString);
    }

    @Test
    void testEqualsAndHashCode() {
        Date dob = new Date();
        Date startDate = new Date();

        Technician technician1 = new Technician(1, "testUser", "testPass", "test@example.com",
                "John", "Doe", dob, "123456789", "TestCity", "ROLE_TECHNICIAN", "TestDomain", startDate);

        Technician technician2 = new Technician(1, "testUser", "testPass", "test@example.com",
                "John", "Doe", dob, "123456789", "TestCity", "ROLE_TECHNICIAN", "TestDomain", startDate);

        assertThat(technician1).isEqualTo(technician2);
        assertThat(technician1.hashCode()).isEqualTo(technician2.hashCode());

        Technician technician3 = new Technician(2, "diffUser", "diffPass", "diff@example.com",
                "Jane", "Smith", dob, "987654321", "DiffCity", "ROLE_ADMIN", "DiffDomain", startDate);

        assertThat(technician1).isNotEqualTo(technician3);
        assertThat(technician1.hashCode()).isNotEqualTo(technician3.hashCode());
    }
}
