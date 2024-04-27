package com.enemSimulado.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enemSimulado.dto.SessionDto;

@Service
public class SimuladoService {
	
	@Autowired
	SessionService sessionService;
	
	@Autowired
	QuestionService questionService;
	
	
	public String getRandomQuestion(String chatId, SessionDto activeSession) {
		return questionService.getRandomQuestion(chatId, activeSession);
	} 
	
	

}
