package com.enemSimulado.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
		AnswerDto existAnswer = new AnswerDto();
		existAnswer = answerRepository.getByChatIdAndMessageIdAndCurrentlyActive(chatId, null, 1);
		
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
	
	public AnswerDto getAnswer(String chatId, Integer messageId) {
		return answerRepository.getByChatIdAndMessageIdAndCurrentlyActive(chatId, messageId.toString(), 1);
	}
	
	public List<AnswerDto> getAllSessionAnswers(String chatId) 				{	
		return answerRepository.getByChatIdAndCurrentlyActive(chatId, 1);
	}
	
	public List<Integer> getCorrectAnswerNumbers(String chatId) {
		List<Integer> returnList = new ArrayList<Integer>();	
		returnList.add(answerRepository.getAllCorrectAnswers(chatId));
		
		for(int i = 1; i < 5; i++) {
			returnList.add(answerRepository.getCorrectAnswersByMatrix(chatId, i));		
		}		
		return returnList;
	}
	
	public List<Integer> getAnswerNumbers(String chatId) {
		List<Integer> returnList = new ArrayList<Integer>();		
		returnList.add(answerRepository.getAllAnswers(chatId));
		
		for(int i = 1; i < 5; i++) {
			returnList.add(answerRepository.getAnswersByMatrix(chatId, i));		
		}		
		return returnList;
	}
	
	
	//Direct Repository re-routing
	public void inactivateSessions(String chatId) 							{	answerRepository.inactivateAnswerSessions(chatId);					}
	public void save(AnswerDto newAnswer) 									{	answerRepository.save(newAnswer);									}
	public void inactivateAnswerSessions(String chatId) 					{	answerRepository.inactivateAnswerSessions(chatId);					}
	public Long getQuantityAnswered(String chatId) 							{	return answerRepository.getQuantityAnswered(chatId);				}
	public Long getQuantityNotAnswered(String chatId) 						{	return answerRepository.getQuantityNotAnswered(chatId);				}
	public Integer getTimeElapsed(String chatId) 							{	return answerRepository.getTimeElapsed(chatId);						}






}
