package ru.t1.aophome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AopHomeApplication {

	public static void main(String[] args) {
		SpringApplication.run(AopHomeApplication.class, args);
	}

}
