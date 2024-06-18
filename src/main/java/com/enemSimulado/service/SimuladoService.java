package com.enemSimulado.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enemSimulado.auxiliary.TextAuxiliary;
import com.enemSimulado.dto.QuestionDto;
import com.enemSimulado.dto.SessionDto;
import com.enemSimulado.dto.TelegramDto;

@Service
public class SimuladoService {
	
	@Autowired
	SessionService sessionService;
	
	@Autowired
	QuestionService questionService;
	
	@Autowired
	TextAuxiliary	textAuxiliary;
	
	
	public List<TelegramDto> getRandomQuestion(String chatId, SessionDto activeSession) {
		return questionService.getRandomQuestion(chatId, activeSession);
	} 
	
	

}
