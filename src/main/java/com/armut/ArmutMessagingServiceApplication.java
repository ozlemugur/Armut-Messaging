package com.armut;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ArmutMessagingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArmutMessagingServiceApplication.class, args);
	}

}
