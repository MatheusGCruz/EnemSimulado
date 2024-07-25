package com.enemSimulado.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/EnemSimulado")
public class EnemSimuladoController {
	
	@GetMapping
	public String healthCheck(HttpServletResponse response) {	
			
		response.setStatus(200);
		return "Health - OK" ;

	}
}





