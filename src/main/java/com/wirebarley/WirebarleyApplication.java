package com.wirebarley;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class WirebarleyApplication {

	public static void main(String[] args) {
		SpringApplication.run(WirebarleyApplication.class, args);
	}

}
