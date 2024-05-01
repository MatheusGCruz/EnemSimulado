package com.enemSimulado.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enemSimulado.auxiliary.TextAuxiliary;
import com.enemSimulado.dto.TelegramDto;
import com.enemSimulado.dto.UserDto;
import com.enemSimulado.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	TextAuxiliary textAuxiliary;
	
	public boolean isAdmin(String chatId) {
		
		UserDto currentUser = userRepository.findOneByTelegramChatId(chatId);		
		
		return (currentUser != null && currentUser.getIsAdmin() == 1);
	}
	
	public List<TelegramDto> registerUser(String userName, String chatId, Integer isAdmin){
		String returnMessage = new String();
		
		UserDto newUser = new UserDto();
		newUser.setIsActive(1);
		newUser.setIsAdmin(isAdmin);
		newUser.setTelegramChatId(chatId);
		newUser.setUsername(userName);
		newUser.setCreatedAt(LocalDateTime.now());
		
		try {
			UserDto createdUser = userRepository.save(newUser);
			if(createdUser.getIsActive() == 1) {
				returnMessage = "User created";
			}
		}
		catch(Exception ex){
			returnMessage = "Error when creating: " + ex.getMessage();
		}
		
		return textAuxiliary.returnSimpleMessage(returnMessage, chatId);
		
	}
	

}
