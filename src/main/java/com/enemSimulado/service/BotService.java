package com.enemSimulado.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import com.enemSimulado.dto.TelegramDto;

@Service
public class BotService extends TelegramLongPollingBot {
		
		private final String botToken = System.getenv("enem_token");
	    private final String baseChatId = System.getenv("enem_chat_id");
	    
	    private boolean botStarted = false;
	    
	    @Autowired
	    FluxService fluxService;
	    
	        
	    public String sendNewMessage(BotService bot) {     	
	    	String botMessage = "";
 	
	    	if(!botStarted) {
	        	
	            try {
	                TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
	                botsApi.registerBot(bot);
	                botMessage = "Creating New Instance";
	                bot.sendMessage(botMessage, baseChatId);
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
			String chatId = update.getMessage().getChatId().toString();
			String receivedMessage = update.getMessage().getText();
			String fileId = "";
			try {
				if(receivedMessage == null) {
					receivedMessage = "Empty Message";
					if(update.getMessage() != null && update.getMessage().getPhoto() != null && update.getMessage().getPhoto().get(0).getFileId() != null) {
						fileId = update.getMessage().getPhoto().get(0).getFileId();
						receivedMessage = "Image received";
					}
				}
				
			}
			catch(Exception ex) {
				System.err.println("Error sending message: " + ex.getMessage());
			}
			
			sendTelegramMessages(fluxService.getNextMessage(receivedMessage, chatId, fileId));  	
	    	
	    }
	    
	    
	    public void sendTelegramMessages(List<TelegramDto> messageList) {
	    	for(TelegramDto message: messageList) {
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

	    @Override
	    public String getBotUsername() {
	        return "YourBotUsername";
	    }

	    @Override
	    public String getBotToken() {
	        return botToken;
	    }
	    
	

}
