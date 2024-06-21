package com.enemSimulado.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enemSimulado.auxiliary.TextAuxiliary;
import com.enemSimulado.dto.ContactDto;
import com.enemSimulado.dto.TelegramDto;
import com.enemSimulado.dto.TipDto;
import com.enemSimulado.repository.ContactRepository;
import com.enemSimulado.repository.TipRepository;

@Service
public class ContactService {
	
	@Autowired	SessionService 		sessionService;
	@Autowired 	ContactRepository 	contactRepository;
	@Autowired 	TipRepository 		tipRepository;
	@Autowired	AnswerService 		answerService;
	@Autowired	TextAuxiliary 		textAuxiliary;
	
	public List<TelegramDto> insertContact(String command, String chatId, Integer stage) {
		
		switch(stage) {
			case 801: return insertTip(command, chatId, stage);
			case 802: return insertComment(command, chatId, stage);
			case 803: return insertBug(command, chatId, stage);
			case 804: return insertIssue(command, chatId, stage);

		}
		
		sessionService.encerrarSessoes(chatId);
		return new ArrayList<TelegramDto>();
	}
	
	public List<TelegramDto> insertTip(String command, String chatId, Integer stage) {
		TipDto newTip = new TipDto(chatId, command, 1);
		tipRepository.save(newTip);
		sessionService.encerrarSessoes(chatId);
		return textAuxiliary.returnSimpleMessage("Dica registrada com sucesso. Ela será exibida para outros usuários que solicitarem.", chatId);
	}
	
	public List<TelegramDto> insertComment(String command, String chatId, Integer stage) {
		ContactDto newContact = new ContactDto(chatId, command, 2, 2);
		newContact.setCreatedAt(LocalDateTime.now());
		contactRepository.save(newContact);
		sessionService.encerrarSessoes(chatId);
		return textAuxiliary.returnSimpleMessage("Comentario registrado com sucesso", chatId);
	}
	
	public List<TelegramDto> insertBug(String command, String chatId, Integer stage) {
		ContactDto newContact = new ContactDto(chatId, command, 3, 0);
		newContact.setCreatedAt(LocalDateTime.now());
		contactRepository.save(newContact);
		sessionService.encerrarSessoes(chatId);
		return textAuxiliary.returnSimpleMessage("Bug registrado com sucesso. O mesmo será avaliado e tratado.", chatId);
	}
	
	public List<TelegramDto> insertIssue(String command, String chatId, Integer stage) {
		ContactDto newContact = new ContactDto(chatId, command, 4, 2);
		newContact.setCreatedAt(LocalDateTime.now());
		contactRepository.save(newContact);
		sessionService.encerrarSessoes(chatId);
		return textAuxiliary.returnSimpleMessage("Reclamação registrada com sucesso", chatId);
	}
	


}
