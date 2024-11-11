package com.example.gradle_demo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@OpenAPIDefinition
@SpringBootApplication
public class GradleDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(GradleDemoApplication.class, args);
	}

}
