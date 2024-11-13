package com.pedrovisk;

import org.springframework.boot.SpringApplication;

public class TestCalculatorChallengeApiApplication {

	public static void main(String[] args) {
		SpringApplication.from(CalculatorChallengeApiApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
