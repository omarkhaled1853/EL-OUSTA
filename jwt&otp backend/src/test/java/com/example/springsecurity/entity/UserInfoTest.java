package com.example.springsecurity.entity;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class UserInfoTest {

    @Test
    void testNoArgsConstructor() {
        UserInfo userInfo = new UserInfo();
        assertThat(userInfo.getId()).isZero();
        assertThat(userInfo.getUsername()).isNull();
        assertThat(userInfo.getPassword()).isNull();
        assertThat(userInfo.getEmailAddress()).isNull();
        assertThat(userInfo.getFirstName()).isNull();
        assertThat(userInfo.getLastName()).isNull();
        assertThat(userInfo.getDob()).isNull();
        assertThat(userInfo.getPhoneNumber()).isNull();
        assertThat(userInfo.getCity()).isNull();
        assertThat(userInfo.getRoles()).isNull();
    }

    @Test
    void testAllArgsConstructor() {
        Date dob = new Date();
        UserInfo userInfo = new UserInfo(
                1, "testUser", "testPass", "test@example.com",
                "John", "Doe", dob, "123456789",
                "TestCity", "ROLE_USER"
        );

        assertThat(userInfo.getId()).isEqualTo(1);
        assertThat(userInfo.getUsername()).isEqualTo("testUser");
        assertThat(userInfo.getPassword()).isEqualTo("testPass");
        assertThat(userInfo.getEmailAddress()).isEqualTo("test@example.com");
        assertThat(userInfo.getFirstName()).isEqualTo("John");
        assertThat(userInfo.getLastName()).isEqualTo("Doe");
        assertThat(userInfo.getDob()).isEqualTo(dob);
        assertThat(userInfo.getPhoneNumber()).isEqualTo("123456789");
        assertThat(userInfo.getCity()).isEqualTo("TestCity");
        assertThat(userInfo.getRoles()).isEqualTo("ROLE_USER");
    }

    @Test
    void testSettersAndGetters() {
        UserInfo userInfo = new UserInfo();
        Date dob = new Date();

        userInfo.setId(1);
        userInfo.setUsername("newUser");
        userInfo.setPassword("newPass");
        userInfo.setEmailAddress("new@example.com");
        userInfo.setFirstName("Jane");
        userInfo.setLastName("Smith");
        userInfo.setDob(dob);
        userInfo.setPhoneNumber("987654321");
        userInfo.setCity("NewCity");
        userInfo.setRoles("ROLE_ADMIN");

        assertThat(userInfo.getId()).isEqualTo(1);
        assertThat(userInfo.getUsername()).isEqualTo("newUser");
        assertThat(userInfo.getPassword()).isEqualTo("newPass");
        assertThat(userInfo.getEmailAddress()).isEqualTo("new@example.com");
        assertThat(userInfo.getFirstName()).isEqualTo("Jane");
        assertThat(userInfo.getLastName()).isEqualTo("Smith");
        assertThat(userInfo.getDob()).isEqualTo(dob);
        assertThat(userInfo.getPhoneNumber()).isEqualTo("987654321");
        assertThat(userInfo.getCity()).isEqualTo("NewCity");
        assertThat(userInfo.getRoles()).isEqualTo("ROLE_ADMIN");
    }

    @Test
    void testToString() {
        Date dob = new Date();
        UserInfo userInfo = UserInfo.builder()
                .id(1)
                .username("testUser")
                .password("testPass")
                .emailAddress("test@example.com")
                .firstName("John")
                .lastName("Doe")
                .dob(dob)
                .phoneNumber("123456789")
                .city("TestCity")
                .roles("ROLE_USER")
                .build();

        String expectedString = "UserInfo(id=1, username=testUser, password=testPass, emailAddress=test@example.com, " +
                "firstName=John, lastName=Doe, dob=" + dob + ", phoneNumber=123456789, city=TestCity, roles=ROLE_USER)";
        assertThat(userInfo.toString()).isEqualTo(expectedString);
    }

    @Test
    void testEqualsAndHashCode() {
        Date dob = new Date();

        UserInfo userInfo1 = new UserInfo(1, "testUser", "testPass", "test@example.com",
                "John", "Doe", dob, "123456789", "TestCity", "ROLE_USER");

        UserInfo userInfo2 = new UserInfo(1, "testUser", "testPass", "test@example.com",
                "John", "Doe", dob, "123456789", "TestCity", "ROLE_USER");

        assertThat(userInfo1).isEqualTo(userInfo2);
        assertThat(userInfo1.hashCode()).isEqualTo(userInfo2.hashCode());

        UserInfo userInfo3 = new UserInfo(2, "differentUser", "differentPass", "different@example.com",
                "Jane", "Smith", dob, "987654321", "DifferentCity", "ROLE_ADMIN");

        assertThat(userInfo1).isNotEqualTo(userInfo3);
        assertThat(userInfo1.hashCode()).isNotEqualTo(userInfo3.hashCode());
    }
}
