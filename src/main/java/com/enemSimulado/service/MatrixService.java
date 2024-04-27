package com.enemSimulado.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enemSimulado.auxiliary.TextAuxiliary;
import com.enemSimulado.dto.MatrixDto;
import com.enemSimulado.repository.MatrixRepository;

@Service
public class MatrixService {
	
	@Autowired
	MatrixRepository matrixRepository;
	
	@Autowired
	TextAuxiliary textAuxiliary;
	
	public String getMatrix() {
		
		List<MatrixDto> matrixList = new ArrayList<MatrixDto>();
				
		try {
			matrixList = matrixRepository.findAll();
			
		}catch(Exception ex) {
			return ex.getMessage();
		}
		
		return stringFactory(matrixList, "matrix");
	}
	
	
	private String stringFactory(List<MatrixDto> matrixList, String Object) {
		
		StringBuilder returnString = new StringBuilder();
		for(MatrixDto matrix : matrixList) {
			if(Object == "matrix") {
				returnString.append(matrix.getMatrix());
				returnString.append(textAuxiliary.skipLine());
				
			}
			
		}
		
		
		return (returnString.isEmpty())?"Sem resultados.":returnString.toString();
	}

}
