package com.enemSimulado.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enemSimulado.auxiliary.TextAuxiliary;
import com.enemSimulado.dto.TelegramDto;
import com.enemSimulado.repository.ContactRepository;

@Service
public class ContactService {
	
	@Autowired
	SessionService sessionService;
	
	@Autowired
	ContactRepository contactRepository;
	
	@Autowired
	TextAuxiliary textAuxiliary;
	
	public List<TelegramDto> insertContact(String command, String chatId, Integer stage) {
		
		switch(stage) {
			case 801: return insertTip(command, chatId, stage);
			case 802: return insertTip(command, chatId, stage);
			case 803: return insertTip(command, chatId, stage);
			case 804: return insertTip(command, chatId, stage);

		}
		
		sessionService.encerrarSessoes(chatId);
		return new ArrayList<TelegramDto>();
	}
	
	public List<TelegramDto> insertTip(String command, String chatId, Integer stage) {

		sessionService.encerrarSessoes(chatId);
		return textAuxiliary.returnSimpleMessage(stage.toString(), chatId);
	}

}
