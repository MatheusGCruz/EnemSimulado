package com.enemSimulado.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import com.enemSimulado.dto.StageDto;
import com.enemSimulado.repository.StageRepository;

@Service
public class StageService {
	
	@Autowired
	StageRepository stageRepository;
	
	@Autowired
	UserService userService;
	
	public List<BotCommand> getCommands(String chatId){
		
		boolean isAdmin = userService.isAdmin(chatId);		
		
    	List<BotCommand> commandsList = new ArrayList<BotCommand>();
    	List<StageDto>	stageList = stageRepository.findAll();
    	
    	for(StageDto stage: stageList) {
    		if(stage.getShowMenu() == 1) {
    			if(stage.getAdminStage() != 1) {
    				commandsList.add(new BotCommand(stage.getTelegramCommand(), stage.getDescription()));
    			}
    			else {
    				if(isAdmin) {
    					commandsList.add(new BotCommand(stage.getTelegramCommand(), stage.getDescription()));
    				}
    			}
    		}
    	}   	
    	
    	
    	return commandsList;
	}
}
