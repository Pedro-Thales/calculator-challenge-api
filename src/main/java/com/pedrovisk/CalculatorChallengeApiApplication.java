package com.pedrovisk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CalculatorChallengeApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CalculatorChallengeApiApplication.class, args);
	}

}
