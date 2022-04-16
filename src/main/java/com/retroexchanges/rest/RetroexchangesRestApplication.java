package com.retroexchanges.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class RetroexchangesRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(RetroexchangesRestApplication.class, args);
	}
}
