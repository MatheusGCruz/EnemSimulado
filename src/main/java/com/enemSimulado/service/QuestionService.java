package com.enemSimulado.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enemSimulado.auxiliary.TextAuxiliary;
import com.enemSimulado.dto.MatrixDto;
import com.enemSimulado.dto.QuestionDto;
import com.enemSimulado.dto.SessionDto;
import com.enemSimulado.dto.StageDto;
import com.enemSimulado.repository.QuestionRepository;
import com.enemSimulado.repository.StageRepository;

@Service
public class QuestionService {

	@Autowired
	QuestionRepository questionRepository;
	
	@Autowired
	StageRepository stageRepository;
		
	@Autowired
	SessionService sessionService;
	
	@Autowired
	TextAuxiliary textAuxiliary;
	
	public String getQuestion(String command, String chatId, Integer stage) {
		
		switch(stage) {
			case 401: return getTextQuestion(command, chatId);
			case 402: return getSpecificQuestion(command, chatId);
		}
	
		return "Invalid Consult";
	}
	
	public String getTextQuestion(String texto, String chatId) {		
		List<QuestionDto> questionList = new ArrayList<QuestionDto>();
		try {
			questionList = questionRepository.findByQuestaoContaining(texto);
		}catch(Exception ex) {
			return ex.getMessage();
		}
		
		sessionService.encerrarSessoes(chatId);
		return stringFactory(questionList, "questao");
	}
	
	public String getSpecificQuestion(String command, String chatId) {
		List<String> questionParameters = textAuxiliary.commaSeparated(command);
		List<QuestionDto> questionList = new ArrayList<QuestionDto>();
		Integer ano = textAuxiliary.list2Int(questionParameters, 1);
		Integer numero = textAuxiliary.list2Int(questionParameters, 2);
		if(ano == 0) {return "Ano inválido";}
		if(numero == 0) {return "Numero da Questão inválido";}
		
		sessionService.encerrarSessoes(chatId);
		
		switch(questionParameters.get(0)) {
			case "azul": return stringFactory(questionRepository.findByAnoAndAzul(ano, numero), "questao");
		}		

		return stringFactory(questionList, "questao");
	}
	
	public String getRandomQuestion(String chatId, SessionDto activeSession) {
		List<QuestionDto> questionList = new ArrayList<QuestionDto>();
		return stringFactory(questionList, "questao");
	}
	
	
	private String stringFactory(List<QuestionDto> questionList, String Object) {
		
		StringBuilder returnString = new StringBuilder();
		for(QuestionDto question : questionList) {
			
			switch (Object) {
	        case "questao": 
	        	returnString.append(question.getQuestao());
	        	returnString.append(textAuxiliary.skipLine());
	        	break;	        
			}		
		}		
		return (returnString.isEmpty())?"Sem resultados.":returnString.toString();
	}
	

		
}
