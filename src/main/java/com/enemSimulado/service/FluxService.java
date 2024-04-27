package com.enemSimulado.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enemSimulado.dto.SessionDto;
import com.enemSimulado.dto.StageDto;
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
	
	public String getNextMessage(String command, String chatId) {
		
		SessionDto activeSession = sessionService.findActiveSession(chatId);
		
		if(activeSession.getIsActive() != null && activeSession.getIsActive() == 1) {
			switch (command) {
				case "/encerrar": return sessionService.encerrarSessoes(chatId);
				case "/ano": return getMessage(command, chatId);
				case "/texto": return getMessage(command, chatId);        
			}

			Integer subGroup = (int) activeSession.getStage()/100;
			switch(subGroup) {
				case 4:	return questionService.getQuestion(command, chatId, activeSession.getStage());	// Pesquisa Questoes
				case 5: return sessionService.configSession(activeSession, command);					// Config sessao
				case 6: return simuladoService.getRandomQuestion(chatId, activeSession);				// Simulado
			}
			
		}
		else {
			switch (command) {	
	        case "/matrizes": return matrixService.getMatrix();
	        case "/pesquisa": return getMessage(command, chatId);
	        case "/simulado": return sessionService.createNewSession(chatId);
			}
		}
		
		
		return command.concat(" - Invalid Command");
	}
	
	
	
	public String getMessage(String command, String chatId) {	
		StageDto currentStage = stageRepository.findOneBytelegramCommand(command);
		if(currentStage != null && currentStage.getHasFollowUp() == 1) {
			sessionService.getActiveSession(chatId, currentStage);				
		}	
		return currentStage.getMessage();				
	}
	
	

	
	

}
