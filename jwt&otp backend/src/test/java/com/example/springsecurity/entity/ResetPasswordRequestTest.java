package com.example.springsecurity.entity;

import com.example.springsecurity.dto.ResetPasswordRequest;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class ResetPasswordRequestTest {

    @Test
    void testNoArgsConstructor() {
        ResetPasswordRequest request = new ResetPasswordRequest();
        assertThat(request.getUsername()).isNull();
        assertThat(request.getPassword()).isNull();
    }

    @Test
    void testAllArgsConstructor() {
        ResetPasswordRequest request = new ResetPasswordRequest("testUser", "testPass");
        assertThat(request.getUsername()).isEqualTo("testUser");
        assertThat(request.getPassword()).isEqualTo("testPass");
    }

    @Test
    void testSettersAndGetters() {
        ResetPasswordRequest request = new ResetPasswordRequest();
        request.setUsername("newUser");
        request.setPassword("newPass");

        assertThat(request.getUsername()).isEqualTo("newUser");
        assertThat(request.getPassword()).isEqualTo("newPass");
    }

    @Test
    void testToString() {
        ResetPasswordRequest request = new ResetPasswordRequest("testUser", "testPass");
        String expectedString = "ResetPasswordRequest(username=testUser, password=testPass)";
        assertThat(request.toString()).isEqualTo(expectedString);
    }

    @Test
    void testEqualsAndHashCode() {
        ResetPasswordRequest request1 = new ResetPasswordRequest("testUser", "testPass");
        ResetPasswordRequest request2 = new ResetPasswordRequest("testUser", "testPass");

        assertThat(request1).isEqualTo(request2);
        assertThat(request1.hashCode()).isEqualTo(request2.hashCode());

        ResetPasswordRequest request3 = new ResetPasswordRequest("differentUser", "testPass");
        assertThat(request1).isNotEqualTo(request3);
    }
}
