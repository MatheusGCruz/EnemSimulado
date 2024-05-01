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
import com.enemSimulado.dto.TelegramDto;
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
	
	
	
	
	public String checkIfExist(String command, String chatId) {
		List<Integer> commandsList = textAuxiliary.questionParameters(command);
		SessionDto activeSession = sessionService.findActiveSession(chatId);
		activeSession.setAno(commandsList.get(0));
		activeSession.setQuestao(commandsList.get(1));	
		activeSession.setLinguagem(commandsList.get(2));
		
		Integer matriz = (Integer) activeSession.getQuestao()/45;
		activeSession.setMatriz(matriz+1);
		
		return sessionService.saveSession(activeSession);

	}
	
	public String addNewQuestion(String command, String chatId) {
		SessionDto activeSession = sessionService.findActiveSession(chatId);
		QuestionDto newQuestion = new QuestionDto();
		
		newQuestion.setQuestao(command);
		newQuestion.setAzul(activeSession.getQuestao());
		newQuestion.setAno(activeSession.getAno());
		newQuestion.setMatriz(activeSession.getMatriz());
		newQuestion.setLinguagem(activeSession.getLinguagem());		
		
		questionRepository.save(newQuestion);
		return sessionService.saveSession(activeSession);
	}
	
	public String addNewBulkQuestion(String command, String chatId) {
		SessionDto activeSession = sessionService.findActiveSession(chatId);
		QuestionDto newQuestion = new QuestionDto();
		
		newQuestion.setQuestao(command);
		newQuestion.setAzul(activeSession.getQuestao());
		newQuestion.setAno(activeSession.getAno());
		newQuestion.setMatriz(activeSession.getMatriz());
		newQuestion.setLinguagem(activeSession.getLinguagem());		
		
		questionRepository.save(newQuestion);
		activeSession.setQuestao(activeSession.getQuestao()+1);
		return sessionService.saveSession(activeSession);
	}
	
	public String addImageForQuestion(String fileId, String chatId) {
		SessionDto activeSession = sessionService.findActiveSession(chatId);
		QuestionDto newQuestion = new QuestionDto();

		
		newQuestion = questionRepository.findByAzulAndAnoAndMatrizAndLinguagem(
				activeSession.getQuestao(),activeSession.getAno(),activeSession.getMatriz(),activeSession.getLinguagem());
		
		newQuestion.setImagem(fileId);
		
		questionRepository.save(newQuestion);
		return sessionService.saveSession(activeSession);
	}
	
	public List<TelegramDto> setQuestion(String command, String chatId, Integer stage, String fileId) {
		
		switch(stage) {
			
			case 900: return textAuxiliary.returnSimpleMessage(checkIfExist(command, chatId), chatId);
			case 901: return textAuxiliary.returnSimpleMessage(addNewQuestion(command, chatId), chatId);
			
			case 910: return textAuxiliary.returnSimpleMessage(checkIfExist(command, chatId), chatId);
			case 911: return textAuxiliary.returnSimpleMessage(addNewQuestion(command, chatId), chatId);
			case 912: return textAuxiliary.returnSimpleMessage(addImageForQuestion(fileId, chatId), chatId);
			
			case 920: return textAuxiliary.returnSimpleMessage(checkIfExist(command, chatId), chatId);
			case 921: return textAuxiliary.returnSimpleMessage(addNewBulkQuestion(command, chatId), chatId);
			
		}
	
		return textAuxiliary.returnSimpleMessage("Invalid Consult", chatId);
	}
	
	public List<TelegramDto> getQuestionList(String command, String chatId, Integer stage, String fileId) {
		
		switch(stage) {
			case 401: return getTextQuestion(command, chatId);
			case 402: return getSpecificQuestion(command, chatId);
		}
	
		return new ArrayList<TelegramDto>();
	}
	
	public List<TelegramDto> getTextQuestion(String texto, String chatId) {		
		List<QuestionDto> questionList = new ArrayList<QuestionDto>();
		try {
			questionList = questionRepository.findByQuestaoContaining(texto);
		}catch(Exception ex) {
			System.err.println("Error sending message: " +ex.getMessage());
		}
		
		sessionService.encerrarSessoes(chatId);
		return textAuxiliary.question2Telegram(questionList, chatId);

	}
	
	public List<TelegramDto> getSpecificQuestion(String command, String chatId) {
		List<TelegramDto> returnList = new ArrayList<TelegramDto>();
		List<String> questionParameters = textAuxiliary.commaSeparated(command);
		List<QuestionDto> questionList = new ArrayList<QuestionDto>();
		Integer ano = textAuxiliary.list2Int(questionParameters, 1);
		Integer numero = textAuxiliary.list2Int(questionParameters, 2);
		if(ano == 0) {return textAuxiliary.returnSimpleMessage("Ano inválido", chatId);}
		if(numero == 0) {return textAuxiliary.returnSimpleMessage("Numero de Questão inválido", chatId);}
		
		sessionService.encerrarSessoes(chatId);
		
		switch(questionParameters.get(0)) {
			case "azul": return textAuxiliary.question2Telegram(questionRepository.findByAnoAndAzul(ano, numero), chatId);
		}		

		return textAuxiliary.returnSimpleMessage("Questão não encontrada", chatId);
	}
	
	public List<QuestionDto> getRandomQuestion(String chatId, SessionDto activeSession) {
		List<QuestionDto> questionList = new ArrayList<QuestionDto>();
		return questionList;
	}
	
	

	

		
}
