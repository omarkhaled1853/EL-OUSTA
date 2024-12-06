package com.example.springsecurity.Modle;

import com.example.springsecurity.Modle.MailStructure;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
class MailStructureTest {
    @Autowired
   private MailStructure mailStructure = new MailStructure();
    @Test
    public void test_mail_structure(){
        //given
        mailStructure.setSubject("Mail structure test");
        mailStructure.setMessage("check test please");
        //when
        String excpectedsubject = "Mail structure test";
        String excpectmessage = "check test please";
        //then
        assertThat(excpectedsubject).isEqualTo("Mail structure test");
        assertThat(excpectmessage).isEqualTo("check test please");
    }

}