package com.enemSimulado.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enemSimulado.repository.AnswerRepository;

@Service
public class AnswerService {
	
	@Autowired
	AnswerRepository answerRepository;
	
	public String getQuantity(String chatId) {
		return answerRepository.getQuantityAnswered(chatId, 1).toString();
	}
}
