package com.enemSimulado;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.enemSimulado.service.InitializationService;

@SpringBootApplication
public class EnemSimuladoApplication {

	@Autowired
	InitializationService initializationService;
	
	public static void main(String[] args) {
		SpringApplication.run(EnemSimuladoApplication.class, args);
	}

}
