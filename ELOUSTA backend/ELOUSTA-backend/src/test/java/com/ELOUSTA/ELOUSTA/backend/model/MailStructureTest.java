package com.ELOUSTA.ELOUSTA.backend.model;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

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