package com.enemSimulado.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enemSimulado.auxiliary.TextAuxiliary;
import com.enemSimulado.dto.MatrixDto;
import com.enemSimulado.dto.TelegramDto;
import com.enemSimulado.repository.MatrixRepository;

@Service
public class MatrixService {
	
	@Autowired
	MatrixRepository matrixRepository;
	
	@Autowired
	TextAuxiliary textAuxiliary;
	
	public List<TelegramDto> getMatrix(String chatId) {
		
		List<MatrixDto> matrixList = new ArrayList<MatrixDto>();
				
		try {
			matrixList = matrixRepository.findAll();
			
		}catch(Exception ex) {
			return textAuxiliary.returnSimpleMessage(ex.getMessage(), chatId);
		}
		
		return textAuxiliary.stringMatrixFactory(matrixList, "matrix", chatId);
	}
	
	


}
