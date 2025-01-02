package com.ELOUSTA.ELOUSTA.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TwilioSmsRequest {
    private String phonenumber; //destination
    private String massage;

    public TwilioSmsRequest(@JsonProperty("phonenumber") String phonenumber,@JsonProperty("message") String massage) {
        this.phonenumber = phonenumber;
        this.massage = massage;
    }

    public String getPhonenuber() {
        return phonenumber;
    }

    public String getMassage() {
        return massage;
    }

    public void setPhonenuber(String phonenuber) {
        this.phonenumber = phonenuber;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    @Override
    public String toString() {
        return "TwilioSmsRequest{" +
                "phonenuber='" + phonenumber + '\'' +
                ", massage='" + massage + '\'' +
                '}';
    }
}
