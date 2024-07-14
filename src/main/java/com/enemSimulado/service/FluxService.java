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
	
	@Autowired	MatrixService 	matrixService;
	@Autowired	QuestionService questionService;
	@Autowired	SessionService 	sessionService;
	@Autowired	SimuladoService simuladoService;
	@Autowired 	UserService 	userService;
	@Autowired	StageRepository stageRepository;
	@Autowired	TextAuxiliary 	textAuxiliary;
	@Autowired 	ContactService	contactService;
	
	public List<TelegramDto> getNextMessage(String commands, String chatId, String fileId) {
		
		String command = commands.split("@")[0];
		SessionDto activeSession = sessionService.findActiveSession(chatId);
		
		if(activeSession.getIsActive() != null && activeSession.getIsActive() == 1) {
			switch (commands) {
				case "/encerrar": 		return sessionService.encerrarSessoes(chatId);
				case "/ano": 			return getMessage(command, chatId);
				case "/texto": 			return getMessage(command, chatId);   
				case "/dica": 			return getMessage(command, chatId); 
				case "/comentario": 	return getMessage(command, chatId); 
				case "/bug": 			return getMessage(command, chatId); 
				case "/reclamacao": 	return getMessage(command, chatId); 
			}
			if(activeSession.getStage() != null) {
				Integer subGroup = (int) activeSession.getStage()/100;
				switch(subGroup) {
					case 4:	return questionService.getQuestionList(command, chatId, activeSession.getStage(), fileId);	// Pesquisa Questoes
					case 5: return sessionService.configSession(activeSession, command, chatId, null);						// Config sessao
					case 6: return simuladoService.getRandomQuestion(chatId, activeSession);							// Simulado
					case 8:	return contactService.insertContact(command, chatId, activeSession.getStage());				// Contatos
					case 9: return questionService.setQuestion(command, chatId, activeSession.getStage(), fileId);		// Registros
				}
			}
			
			return textAuxiliary.returnSimpleMessage(command.concat(" - Comando inválido para a sessão ativa. Encerre a sessão etentenovamente"), chatId);
		}
		else {
			switch (command) {	
			case "/registrar"				: return userService.registerUser("Teste", chatId, 1);
	        case "/matrizes"				: return matrixService.getMatrix(chatId);
	        case "/pesquisa"				: return getMessage(command, chatId);
	        case "/simulado"				: return sessionService.createNewSim(chatId, 5, 45);
	        case "/simulado_rapido"			: return sessionService.createNewSim(chatId, 51, 5);
	        case "/contato"					: return getMessage(command, chatId);
	        case "/nova_questao"			: return sessionService.createNewSession(chatId, 90);
	        case "/nova_questao_com_imagem"	: return sessionService.createNewSession(chatId, 91);
	        case "/lote_questao"			: return sessionService.createNewSession(chatId, 92);	        
			}
		}
		
		if(command == "/encerrar") {
			return textAuxiliary.returnSimpleMessage("Sessão encerrada.", chatId);
		}
		return textAuxiliary.returnSimpleMessage(command.concat(" - Comando inválido"), chatId);
	}
	
	
	
	public List<TelegramDto> getMessage(String command, String chatId) {
		command = command.replaceAll("[^a-zA-Z0-9]", "");
		StageDto currentStage = stageRepository.findOneBytelegramCommand(command);
		if(currentStage != null && currentStage.getHasFollowUp() == 1) {
			sessionService.getActiveSession(chatId, currentStage);				
		}	

		return textAuxiliary.returnSimpleMessage(currentStage.getMessage(), chatId);				
	}
	
	

	
	

}
