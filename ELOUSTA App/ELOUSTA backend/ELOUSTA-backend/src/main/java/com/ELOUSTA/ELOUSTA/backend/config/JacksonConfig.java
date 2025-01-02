package com.ELOUSTA.ELOUSTA.backend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        // Configure the date format globally (dd/MM/yyyy)
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        objectMapper.setDateFormat(dateFormat);

        // Optional: Disable timestamps for dates
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        return objectMapper;
    }
}
