package com.enemSimulado.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Service
public class BotService extends TelegramLongPollingBot {
		
		private final String botToken = System.getenv("bot_token");
	    private final String baseChatId = System.getenv("base_chat_id");
	    
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
	    	char commandChar = '/';
			
	    	if(commandChar == receivedMessage.charAt(0)){
	    		sendMessage(fluxService.getNextMessage(receivedMessage), chatId);
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

	    @Override
	    public String getBotUsername() {
	        return "YourBotUsername";
	    }

	    @Override
	    public String getBotToken() {
	        return botToken;
	    }
	    
	

}
