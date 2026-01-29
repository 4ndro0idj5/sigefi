package com.fiap.sigefi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SigefiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SigefiApplication.class, args);
	}

}
