package com.example.springsecurity.Enums;

public enum ValidationStatus {
    INVALID_USERNAME("Invalid username"),
    INVALID_EMAIL("Invalid email address"),
    VALID("valid");

    private final String message;
    ValidationStatus(String s) {
        this.message = s;
    }

    public String getMessage() {
        return message;
    }
}
