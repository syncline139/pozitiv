package ru.company.positiv;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PositivApplication {

	public static void main(String[] args) {
		SpringApplication.run(PositivApplication.class, args);

	}
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
