package com.enemSimulado.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enemSimulado.dto.AnswerDto;
import com.enemSimulado.repository.AnswerRepository;

@Service
public class AnswerService {
	
	@Autowired
	AnswerRepository answerRepository;
	
	public String getQuantity(String chatId) {
		return answerRepository.getQuantityAnswered(chatId).toString();
	}
	
	public void updateAnswer(Integer answerId, String chatId, String messageId) {
		AnswerDto existAnswer = answerRepository.getByChatIdAndMessageIdAndCurrentlyActive(chatId, null, 1);
		if(existAnswer == null || existAnswer.getChatId() == null) {
			return;
		}
		
		if(existAnswer.getMessageId() == null)
		{
			existAnswer.setAnsweredAt(LocalDateTime.now());
			existAnswer.setAnswerId(answerId);
			existAnswer.setMessageId(messageId);			
			answerRepository.save(existAnswer);
			
			return;
		}
		
		if(existAnswer.getMessageId() == messageId) {
			existAnswer.setAnsweredAt(LocalDateTime.now());
			existAnswer.setAnswerId(answerId);
			answerRepository.save(existAnswer);
			
			return;
		}
		
	}
	
	public void inactivateSessions(String chatId) {
		answerRepository.inactivateAnswerSessions(chatId);
	}
}
