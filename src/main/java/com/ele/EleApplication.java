package com.ele;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EleApplication {

	public static void main(String[] args) {
		SpringApplication.run(EleApplication.class, args);
	}
}
