//package com.example.springsecurity.entity;
//
//import org.junit.jupiter.api.Test;
//
//import java.util.Date;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//class ClientEntityTest {
//
//    @Test
//    void testNoArgsConstructor() {
//        ClientEntity clientEntity = new ClientEntity();
//        assertThat(clientEntity.getId()).isZero();
//        assertThat(clientEntity.getUsername()).isNull();
//        assertThat(clientEntity.getPassword()).isNull();
//        assertThat(clientEntity.getEmailAddress()).isNull();
//        assertThat(clientEntity.getFirstName()).isNull();
//        assertThat(clientEntity.getLastName()).isNull();
//        assertThat(clientEntity.getDob()).isNull();
//        assertThat(clientEntity.getPhoneNumber()).isNull();
//        assertThat(clientEntity.getCity()).isNull();
//        assertThat(clientEntity.getRoles()).isNull();
//    }
//
////    @Test
////    void testAllArgsConstructor() {
////        Date dob = new Date();
////        ClientEntity clientEntity = new ClientEntity(
////                1, "testUser", "testPass", "test@example.com",
////                "John", "Doe", dob, "123456789",
////                "TestCity", "ROLE_USER"
////        );
////
////        assertThat(clientEntity.getId()).isEqualTo(1);
////        assertThat(clientEntity.getUsername()).isEqualTo("testUser");
////        assertThat(clientEntity.getPassword()).isEqualTo("testPass");
////        assertThat(clientEntity.getEmailAddress()).isEqualTo("test@example.com");
////        assertThat(clientEntity.getFirstName()).isEqualTo("John");
////        assertThat(clientEntity.getLastName()).isEqualTo("Doe");
////        assertThat(clientEntity.getDob()).isEqualTo(dob);
////        assertThat(clientEntity.getPhoneNumber()).isEqualTo("123456789");
////        assertThat(clientEntity.getCity()).isEqualTo("TestCity");
////        assertThat(clientEntity.getRoles()).isEqualTo("ROLE_USER");
////    }
//
//    @Test
//    void testSettersAndGetters() {
//        ClientEntity clientEntity = new ClientEntity();
//        Date dob = new Date();
//
//        clientEntity.setId(1);
//        clientEntity.setUsername("newUser");
//        clientEntity.setPassword("newPass");
//        clientEntity.setEmailAddress("new@example.com");
//        clientEntity.setFirstName("Jane");
//        clientEntity.setLastName("Smith");
//        clientEntity.setDob(dob);
//        clientEntity.setPhoneNumber("987654321");
//        clientEntity.setCity("NewCity");
//        clientEntity.setRoles("ROLE_ADMIN");
//
//        assertThat(clientEntity.getId()).isEqualTo(1);
//        assertThat(clientEntity.getUsername()).isEqualTo("newUser");
//        assertThat(clientEntity.getPassword()).isEqualTo("newPass");
//        assertThat(clientEntity.getEmailAddress()).isEqualTo("new@example.com");
//        assertThat(clientEntity.getFirstName()).isEqualTo("Jane");
//        assertThat(clientEntity.getLastName()).isEqualTo("Smith");
//        assertThat(clientEntity.getDob()).isEqualTo(dob);
//        assertThat(clientEntity.getPhoneNumber()).isEqualTo("987654321");
//        assertThat(clientEntity.getCity()).isEqualTo("NewCity");
//        assertThat(clientEntity.getRoles()).isEqualTo("ROLE_ADMIN");
//    }
//
//    @Test
//    void testToString() {
//        Date dob = new Date();
//        ClientEntity clientEntity = ClientEntity.builder()
//                .id(1)
//                .username("testUser")
//                .password("testPass")
//                .emailAddress("test@example.com")
//                .firstName("John")
//                .lastName("Doe")
//                .dob(dob)
//                .phoneNumber("123456789")
//                .city("TestCity")
//                .roles("ROLE_USER")
//                .build();
//
//        String expectedString = "ClientEntity(id=1, username=testUser, password=testPass, emailAddress=test@example.com, " +
//                "firstName=John, lastName=Doe, dob=" + dob + ", phoneNumber=123456789, city=TestCity, roles=ROLE_USER)";
//        assertThat(clientEntity.toString()).isEqualTo(expectedString);
//    }
//
////    @Test
////    void testEqualsAndHashCode() {
////        Date dob = new Date();
////
////        ClientEntity clientEntity1 = new ClientEntity(1, "testUser", "testPass", "test@example.com",
////                "John", "Doe", dob, "123456789", "TestCity", "ROLE_USER");
////
////        ClientEntity clientEntity2 = new ClientEntity(1, "testUser", "testPass", "test@example.com",
////                "John", "Doe", dob, "123456789", "TestCity", "ROLE_USER");
////
////        assertThat(clientEntity1).isEqualTo(clientEntity2);
////        assertThat(clientEntity1.hashCode()).isEqualTo(clientEntity2.hashCode());
////
////        ClientEntity clientEntity3 = new ClientEntity(2, "differentUser", "differentPass", "different@example.com",
////                "Jane", "Smith", dob, "987654321", "DifferentCity", "ROLE_ADMIN");
////
////        assertThat(clientEntity1).isNotEqualTo(clientEntity3);
////        assertThat(clientEntity1.hashCode()).isNotEqualTo(clientEntity3.hashCode());
////    }
//}
