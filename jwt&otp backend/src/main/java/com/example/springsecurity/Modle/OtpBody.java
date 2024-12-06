package com.example.springsecurity.Modle;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OtpBody {
    private String userphonenumber;
    private String otp;

    public OtpBody(@JsonProperty("userphonenumber") String userphonenumber,@JsonProperty("otp") String otp) {
        this.userphonenumber = userphonenumber;
        this.otp = otp;
    }

    public void setUserphonenumber(String userphonenumber) {
        this.userphonenumber = userphonenumber;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getUserphonenumber() {
        return userphonenumber;
    }

    public String getOtp() {
        return otp;
    }
}
