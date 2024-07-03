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
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import com.enemSimulado.auxiliary.TextAuxiliary;
import com.enemSimulado.dto.AnswerDto;
import com.enemSimulado.dto.SessionDto;
import com.enemSimulado.dto.TelegramDto;

@Service
public class BotService extends TelegramLongPollingBot {
		
		private final String botToken = System.getenv("enem_token");
	    private final String baseChatId = System.getenv("enem_chat_id");
	    
	    private boolean botStarted = false;
	    
	    @Autowired	    FluxService 	fluxService;
	    @Autowired    	StageService 	stageService;
	    @Autowired    	AnswerService 	answerService;
	    @Autowired    	SessionService 	sessionService;
	    @Autowired    	QuestionService questionService;
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
				String fileId = null;
				if(receivedMessage == null) {receivedMessage = "Empty Message";};
				try {				
						if(update.getMessage() != null && update.getMessage().getPhoto() != null && update.getMessage().getPhoto().get(0).getFileId() != null) {
							fileId = update.getMessage().getPhoto().get(0).getFileId();
							receivedMessage = update.getMessage().getCaption();
						}
						if(update.getMessage() != null && update.getMessage().getCaption() != null && receivedMessage == "Empty Message") {
							receivedMessage = update.getMessage().getCaption();
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
			
			if(update.hasCallbackQuery()) {
				String selectedAnswer = update.getCallbackQuery().getData();
				String chatId = update.getCallbackQuery().getFrom().getId().toString();
				Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
				String command = "None";				
				SessionDto activeSession = sessionService.getActiveSessionByChatId(chatId);				
				switch(selectedAnswer){
					case	"A":
					case	"B":
					case	"C":
					case	"D":
					case	"E":
								answerService.updateAnswer(textAuxiliary.getAuxiliaryResponse(selectedAnswer), chatId, messageId.toString());
								editMessage(update.getCallbackQuery().getMessage().getText() +" ", chatId, messageId, textAuxiliary.getAuxiliaryResponse(selectedAnswer));								 
									break;
					case	"Skip": answerService.updateAnswer(0, chatId, messageId.toString());
									break;
					case	"English":
					case	"Spanish":
									sessionService.setLanguage(chatId, selectedAnswer);
									break;
					case 	"Close": sessionService.encerrarSessoes(chatId);
									command = "/encerrar";
									break;									
				}
				
				if(activeSession.getStage() == 69) {
					updateAllAnswers(chatId, activeSession);
					sendTelegramMessages(questionService.endSim(chatId, activeSession));
				}
				else {				
					sendTelegramMessages(fluxService.getNextMessage(command, chatId, null));
				}
				
			}
 	
	    	
	    }
	    
	    
	    public void sendTelegramMessages(List<TelegramDto> messageList) {
	    	
	    	for(TelegramDto message: messageList) {
	    			setActualCommands(message.getChatId());
	    			if(message.getText() != null && message.getInLineKeys() == null) {sendMessage(message.getText(), message.getChatId());	}
	    			if(message.getPhoto() != null) {sendImage(message.getPhoto(), message.getChatId());	}
	    			if(message.getText() != null && message.getInLineKeys() != null) {sendInLineKeys(message);	}
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
	    
	    public void editMessage(String message, String chatId, Integer messageId, Integer selectedAnswer) {
	    	
	    	SessionDto activeSession = sessionService.findActiveSession(chatId);
	    	AnswerDto answer = answerService.getAnswer(chatId, messageId);
	    	
	    	List<String> replyList = new ArrayList<String>();
	    	
	    	Boolean hideAnswer = true;
	    	Integer correctAnswer = 0;
	    	if(activeSession != null && activeSession.getOcultarCorreta() == 0) {
	    		hideAnswer = false;
	    	}
	    	if(answer != null && answer.getCorrectAnswerId() != null) {
	    		correctAnswer = answer.getCorrectAnswerId();
	    	}
	    	
	    	
            EditMessageText newMessage = new EditMessageText();
            newMessage.setChatId(chatId);
            newMessage.setMessageId(messageId);
            newMessage.setText(message);
            
            
            newMessage.setReplyMarkup(textAuxiliary.replyKeyboardAnswered(correctAnswer, selectedAnswer, hideAnswer));
            
            try {
                execute(newMessage); 
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
	    }
	    
	    public Message sendInLineKeys(TelegramDto message) {
	    	
	        SendMessage newMessage = new SendMessage();
	        newMessage.setChatId(message.getChatId());
	        newMessage.setText(message.getText());
	        newMessage.setReplyMarkup(textAuxiliary.replyKeyboard(message.getInLineKeys()));
	        
	        Message returnMessage = new Message();
	        
	        try {
	        	returnMessage = execute(newMessage);
	            System.out.println("InLine sent successfully!");
	      
	        } catch (TelegramApiException e) {
	            System.err.println("Error sending message: " + e.getMessage());
	        }
	        
	        
	        //answerService.updateAnswer(0, message.getChatId(), returnMessage.getMessageId().toString());
	        return returnMessage;
	    }
	    
	    public void sendInitialMessage() {
	    			
	        SendMessage newMessage = new SendMessage();
	        newMessage.setChatId(baseChatId);
	        newMessage.setText("Creating New Instance");
	        newMessage.setReplyMarkup(textAuxiliary.replyKeyboard("Menu"));
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
	    
	    public void updateAllAnswers(String chatId, SessionDto activeSession) {
	    	List<AnswerDto> answers = answerService.getAllSessionAnswers(chatId);
	    	for(AnswerDto answer: answers) {
	    		String message = questionService.getQuestionById(answer.getQuestionId()).getQuestao();
	    		editMessage("Avaliada: "+message, answer.getChatId(), Integer.parseInt(answer.getMessageId()), answer.getAnswerId());
	    	}    	
	    	
	    }
	    
	

}
