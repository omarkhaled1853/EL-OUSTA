package com.example.springsecurity.Enums;

public enum ValidationStatus {
    INVALID_USERNAME("Invalid username"), // When user signup with repeated username for other user
    INVALID_EMAIL("Invalid email address"), // When user signup with repeated email for other user
    VALID("valid"),// When user make valid signup
    FAIL("fail");// When user make signIn with wrong email address or password


    private final String message;
    ValidationStatus(String s) {
        this.message = s;
    }

    public String getMessage() {
        return message;
    }
}
