package com.enemSimulado.service;

import java.time.LocalDateTime;
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
public class SessionService {
	
	@Autowired
	SessionRepository sessionRepository;
	
	@Autowired
	StageRepository stageRepository;
	
	@Autowired
	TextAuxiliary textAuxiliary;
	
	public List<TelegramDto> createNewSession(String chatId, Integer stage) {
    	StageDto newStage = stageRepository.findFirstByStage(stage);
    	SessionDto activeSession = getActiveSession(chatId, newStage);      	
    	return configSession(activeSession, "", chatId);
	}
	
	public List<TelegramDto> createNewFastSim(String chatId, Integer stage) {
    	StageDto newStage = stageRepository.findFirstByStage(stage);
    	SessionDto activeSession = getActiveSession(chatId, newStage); 
    	activeSession.setAno(0);
    	activeSession.setLinguagem(1);
    	activeSession.setMatriz(0);
    	activeSession.setQuantidadeTopico(5);
    	activeSession.setOcultarCorreta(1);
    	return configSession(activeSession, "", chatId);
	}
	
	public List<TelegramDto> configSession(SessionDto activeSession, String command, String chatId) {		
		
		List<TelegramDto> returnList = new ArrayList<TelegramDto>();
		List<TelegramDto> messageList = new ArrayList<TelegramDto>();
		switch(activeSession.getStage()) {	
			case 501:	
				activeSession.setAno(textAuxiliary.string2Int(command));
				break;
							
		}
		returnList.addAll(textAuxiliary.returnSimpleMessage(saveSession(activeSession), chatId));	
		returnList.addAll(messageList);
		
		return returnList;
	}
	
	public List<TelegramDto> encerrarSessoes(String chatId) {		
		
		List<SessionDto> userSessions = new ArrayList<SessionDto>();
		userSessions = sessionRepository.findAllBytelegramChatId(chatId);
		
		for(SessionDto session: userSessions) {
			session.setEndedAt(LocalDateTime.now());
			session.setIsActive(0);
			sessionRepository.save(session);
		}
		
		List<TelegramDto> returnList = new ArrayList<TelegramDto>();
		TelegramDto addMessage = new TelegramDto(chatId, "Sessão Encerrada", null, null);
		returnList.add(addMessage);
		return returnList;
	}
	
	public SessionDto findActiveSession(String chatId) {
		List<SessionDto> userSessions = sessionRepository.findBytelegramChatId(chatId);
		
		SessionDto activeSession = new SessionDto();
		
		for(SessionDto session: userSessions) {
			if(session.getIsActive() == 1) {
				activeSession = session;
			}
		}
		
		return activeSession;
	}
	
	public SessionDto getActiveSession(String chatId, StageDto currentStage) {
		
		List<SessionDto> userSessions = sessionRepository.findBytelegramChatId(chatId);
		
		SessionDto activeSession = new SessionDto();
		
		for(SessionDto session: userSessions) {
			if(session.getIsActive() == 1) {
				activeSession = session;
			}
		}
		
		if(activeSession.getIsActive() == null || activeSession.getIsActive()== 0) {
			SessionDto newSession = new SessionDto();
			newSession.setIsActive(currentStage.getHasFollowUp());
			newSession.setTelegramChatId(chatId);
			newSession.setCreatedAt(LocalDateTime.now());
			newSession.setStage(currentStage.getStage());	
			newSession.setNextStage(currentStage.getChildStage());
			activeSession = sessionRepository.save(newSession);
		}
		else {
			activeSession.setIsActive(currentStage.getHasFollowUp());
			activeSession.setStage(currentStage.getStage());
			activeSession.setNextStage(currentStage.getChildStage());
			activeSession = sessionRepository.save(activeSession);
		}
		
		return activeSession;
	}
	
	public String saveSession(SessionDto activeSession) {
		StageDto currentStage = stageRepository.findFirstByStage(activeSession.getStage());
		StageDto nextStage = stageRepository.findFirstByStage(activeSession.getNextStage());
		activeSession.setStage(activeSession.getNextStage());
		if(nextStage != null) {	activeSession.setNextStage(nextStage.getChildStage());	}
		sessionRepository.save(activeSession);
		return currentStage.getMessage();		
		
	}
	

}
