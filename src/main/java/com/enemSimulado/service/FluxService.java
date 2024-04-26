package com.enemSimulado.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enemSimulado.flux.MatrixFlux;

@Service
public class FluxService {
	
	@Autowired
	MatrixFlux matrixFlux;
	
	public String getNextMessage(String command) {
		
		if(command.equals("/matrizes")) {
			return matrixFlux.getMatrix();
		}
		
		
		
		return command.concat(" - Invalid Command");
	}

}
