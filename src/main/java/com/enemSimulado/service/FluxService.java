package com.enemSimulado.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enemSimulado.auxiliary.TextAuxiliary;
import com.enemSimulado.dto.SessionDto;
import com.enemSimulado.dto.StageDto;
import com.enemSimulado.dto.TelegramDto;
import com.enemSimulado.repository.SessionRepository;
import com.enemSimulado.repository.StageRepository;

@Service
public class FluxService {
	
	@Autowired
	MatrixService matrixService;
	
	@Autowired
	QuestionService questionService;
	
	@Autowired
	SessionService sessionService;

	@Autowired
	SimuladoService simuladoService;
	
	@Autowired
	StageRepository stageRepository;
	
	@Autowired
	TextAuxiliary textAuxiliary;
	
	public List<TelegramDto> getNextMessage(String command, String chatId, String fileId) {
		
		SessionDto activeSession = sessionService.findActiveSession(chatId);
		
		if(activeSession.getIsActive() != null && activeSession.getIsActive() == 1) {
			switch (command) {
				case "/encerrar": return sessionService.encerrarSessoes(chatId);
				case "/ano": return getMessage(command, chatId);
				case "/texto": return getMessage(command, chatId);        
			}

			Integer subGroup = (int) activeSession.getStage()/100;
			switch(subGroup) {
				case 4:	return questionService.getQuestionList(command, chatId, activeSession.getStage(), fileId);	// Pesquisa Questoes
				case 5: return sessionService.configSession(activeSession, command, chatId);					// Config sessao
				case 6: return simuladoService.getRandomQuestion(chatId, activeSession);						// Simulado
				case 9: return questionService.setQuestion(command, chatId, activeSession.getStage(), fileId);	//Registros
			}
			
		}
		else {
			switch (command) {	
	        case "/matrizes"	: return matrixService.getMatrix(chatId);
	        case "/pesquisa"	: return getMessage(command, chatId);
	        case "/simulado"	: return sessionService.createNewSession(chatId, 5);
	        case "/new"			: return sessionService.createNewSession(chatId, 90);
	        case "/newImage"	: return sessionService.createNewSession(chatId, 91);
			}
		}
		
		
		return textAuxiliary.returnSimpleMessage(command.concat(" - Invalid Command"), chatId);
	}
	
	
	
	public List<TelegramDto> getMessage(String command, String chatId) {	
		StageDto currentStage = stageRepository.findOneBytelegramCommand(command);
		if(currentStage != null && currentStage.getHasFollowUp() == 1) {
			sessionService.getActiveSession(chatId, currentStage);				
		}	

		return textAuxiliary.returnSimpleMessage(currentStage.getMessage(), chatId);				
	}
	
	

	
	

}
