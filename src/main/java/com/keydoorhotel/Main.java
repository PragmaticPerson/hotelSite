package com.keydoorhotel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableAutoConfiguration
@EnableJpaRepositories("com.keydoorhotel.dao")
@EntityScan("com.keydoorhotel.service.model")
public class Main {
	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}
}
