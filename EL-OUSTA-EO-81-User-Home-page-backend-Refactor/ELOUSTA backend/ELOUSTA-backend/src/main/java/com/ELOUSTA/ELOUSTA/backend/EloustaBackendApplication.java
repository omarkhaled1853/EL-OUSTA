package com.ELOUSTA.ELOUSTA.backend;

import com.ELOUSTA.ELOUSTA.backend.service.DataGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EloustaBackendApplication implements CommandLineRunner {

	@Autowired
	private DataGenerationService dataGenerationService;

	public static void main(String[] args) {
		SpringApplication.run(EloustaBackendApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Generate the test data when the application starts
		dataGenerationService.generateTestData();
		System.out.println("Test data generated successfully.");
	}
}
