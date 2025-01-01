package com.ELOUSTA.ELOUSTA.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class EloustaBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EloustaBackendApplication.class, args);
	}

}
