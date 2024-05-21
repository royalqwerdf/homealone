package com.elice.homealone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HomealoneApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomealoneApplication.class, args);
	}

}