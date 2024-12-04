package com.testOTP.TestOtp.Modle;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailStructure {
    private String subject;
    private String message;

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setMessage(String massege) {
        this.message = massege;
    }
}
