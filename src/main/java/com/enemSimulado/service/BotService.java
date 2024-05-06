package com.enemSimulado.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import com.enemSimulado.auxiliary.TextAuxiliary;
import com.enemSimulado.dto.TelegramDto;

@Service
public class BotService extends TelegramLongPollingBot {
		
		private final String botToken = System.getenv("enem_token");
	    private final String baseChatId = System.getenv("enem_chat_id");
	    
	    private boolean botStarted = false;
	    
	    @Autowired	    FluxService 	fluxService;
	    @Autowired    	StageService 	stageService;
	    @Autowired		TextAuxiliary	textAuxiliary;
	        
	    public String sendNewMessage(BotService bot) {     	
	    	String botMessage = "";    	
 	
	    	if(!botStarted) {
	        	
	            try {
	                TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
	                botsApi.registerBot(bot);
	                setActualCommands(baseChatId);
	                bot.sendInitialMessage();
	                botStarted = true;
	            } catch (TelegramApiException e) {
	                e.printStackTrace();
	            }
	        }
	    	else { 	
	    		 botMessage = "Instance Already created";
	        	bot.sendMessage(botMessage, baseChatId);
	    	}
	    	return botMessage;

	    }


		@Override
	    public void onUpdateReceived(Update update) {
			
			// New Message
			if(update.getMessage() != null) {
				String chatId = update.getMessage().getChatId().toString();
				String receivedMessage = update.getMessage().getText();
				String fileId = "";
				if(receivedMessage == null) {receivedMessage = "Empty Message";};
				try {				
						if(update.getMessage() != null && update.getMessage().getPhoto() != null && update.getMessage().getPhoto().get(0).getFileId() != null) {
							fileId = update.getMessage().getPhoto().get(0).getFileId();
							receivedMessage = "Image received";
						}
					
				}
				catch(Exception ex) {
					System.err.println("Error sending message: " + ex.getMessage());
				}
				
				sendTelegramMessages(fluxService.getNextMessage(receivedMessage, chatId, fileId)); 
			}
			
			// Edited Message
			if(update.getEditedMessage() != null) {
				String chatId = update.getEditedMessage().getChatId().toString();
				String receivedMessage = update.getEditedMessage().getText();
				String editedMessageId = update.getEditedMessage().getMessageId().toString();
				String teste ="";
			}
 	
	    	
	    }
	    
	    
	    public void sendTelegramMessages(List<TelegramDto> messageList) {
	    	
	    	for(TelegramDto message: messageList) {
	    		setActualCommands(message.getChatId());
	    		if(message.getText() != null) {sendMessage(message.getText(), message.getChatId());	}
	    		if(message.getPhoto() != null) {sendImage(message.getPhoto(), message.getChatId());	}
	    	}
	    }

	    public void sendMessage(String message, String chatId) {
	    	
	        SendMessage newMessage = new SendMessage();
	        newMessage.setChatId(chatId);
	        newMessage.setText(message);
	        	
	        try {
	        	execute(newMessage);
	            System.out.println("Message sent successfully!");
	      
	        } catch (TelegramApiException e) {
	            System.err.println("Error sending message: " + e.getMessage());
	        }
	    }
	    
	    public void sendInitialMessage() {
	    			
	        SendMessage newMessage = new SendMessage();
	        newMessage.setChatId(baseChatId);
	        newMessage.setText("Creating New Instance");
	        newMessage.setReplyMarkup(textAuxiliary.replyKeyboard());
	        try {
	        	execute(newMessage);
	            System.out.println("Message sent successfully!");
	      
	        } catch (TelegramApiException e) {
	            System.err.println("Error sending message: " + e.getMessage());
	        }
	    }
	    
	    public void sendImage(String fileId,String chatId) {
	        SendPhoto sendPhotoRequest = new SendPhoto();
	        sendPhotoRequest.setChatId(chatId);
	        sendPhotoRequest.setPhoto(new InputFile(fileId));

	        try {
	            execute(sendPhotoRequest);
	        } catch (TelegramApiException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    private void setActualCommands(String chatId) {
            SetMyCommands setCommands = new SetMyCommands();
            setCommands.setCommands(stageService.getCommands(chatId));
            try {
				execute(setCommands);
			} catch (TelegramApiException e) {
				e.printStackTrace();
			}
	    }
	    

	    @Override
	    public String getBotUsername() {
	        return "YourBotUsername";
	    }

	    @Override
	    public String getBotToken() {
	        return botToken;
	    }
	    
	

}
