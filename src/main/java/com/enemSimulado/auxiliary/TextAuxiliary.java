package com.enemSimulado.auxiliary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import com.enemSimulado.dto.MatrixDto;
import com.enemSimulado.dto.QuestionDto;
import com.enemSimulado.dto.TelegramDto;

@Service
public class TextAuxiliary {

	public static String newline = System.getProperty("line.separator");
	
	public String skipLine() {
		StringBuilder skipLineString = new StringBuilder();
		skipLineString.append(newline);
		skipLineString.append(newline);
		
		return skipLineString.toString();
		
	}
	
	public List<String> commaSeparated(String originalString){
		originalString = originalString.replaceAll("\\s+","");
		originalString = originalString.toLowerCase();
		return Arrays.asList(originalString.split("\\s*,\\s*"));		
	}
	
	public Integer list2Int(List<String> stringList, Integer ind) {
		try{
			return Integer.valueOf(stringList.get(ind));
		}catch(Exception ex) {
			System.err.println("Error converting to int: " + ex.getMessage());
		}		
		return 0;
	}
	
	public Integer string2Int(String stringValue) {
		try{
			return Integer.valueOf(stringValue);
		}catch(Exception ex) {
			System.err.println("Error converting to int: " + ex.getMessage());
		}		
		return 0;
	}
	
	public List<Integer> questionParameters(String originalString){
		originalString = originalString.replaceAll("\\s+","");
		originalString = originalString.toLowerCase();
		List<String> parameters = Arrays.asList(originalString.split("\\s*,\\s*"));	
		List<Integer> returnParameters = new ArrayList<Integer>();
		for(String parameter:parameters) {
			switch(parameter) {
				case "ingles": 
					returnParameters.add(1); 
					break;
				case "espanhol": 
					returnParameters.add(2); 
					break;
				
				case "linguagens": 
					returnParameters.add(1); 
					break;
				case "humanas":
					returnParameters.add(2); 
					break;
				case "natureza":
					returnParameters.add(3);
					break;
				case "matematica":
					returnParameters.add(4);	 
					break;				
				
				default:
					returnParameters.add(string2Int(parameter));
					break;
			}
			
		}
		
		return returnParameters;
	}
	
	public String stringFactory(List<QuestionDto> questionList, String Object) {
		
		StringBuilder returnString = new StringBuilder();
		for(QuestionDto question : questionList) {
			
			switch (Object) {
	        case "questao": 
	        	returnString.append(question.getQuestao());
	        	returnString.append(skipLine());
	        	break;	        
			}		
		}		
		return (returnString.isEmpty())?"Sem resultados.":returnString.toString();
	}
	
	public List<TelegramDto> stringMatrixFactory(List<MatrixDto> matrixList, String Object, String chatId) {
		
		StringBuilder returnString = new StringBuilder();
		for(MatrixDto matrix : matrixList) {
			if(Object == "matrix") {
				returnString.append(matrix.getMatrix());
				returnString.append(skipLine());				
			}			
		}	
		
		return returnSimpleMessage((returnString.isEmpty())?"Sem resultados.":returnString.toString(), chatId);
	}
	
	public List<TelegramDto> returnSimpleMessage(String message, String chatId){
		List<TelegramDto> returnList = new ArrayList<TelegramDto>();
		TelegramDto addMessage = new TelegramDto(chatId, message, null, null);
		returnList.add(addMessage);
		
		return returnList;
	}
	
	public String getPeriodFromSeconds(Integer totalTime) {
		Integer hours = totalTime.intValue()/3600;
		Integer hoursRemain = totalTime.intValue()%3600;
		
		Integer minutes = hoursRemain/60;
		Integer seconds = hoursRemain%60;

		StringBuilder returnString = new StringBuilder();
		
		returnString.append(hours.toString());
		returnString.append(" h : ");		
		returnString.append(minutes.toString());
		returnString.append(" min : ");		
		returnString.append(seconds.toString());
		returnString.append(" s ");
		
		return returnString.toString();
		
	}
	
	
	public List<TelegramDto> question2Telegram(List<QuestionDto> questionList, String chatId){
		List<TelegramDto> returnList = new ArrayList<TelegramDto>();
		
		for(QuestionDto question:questionList) {
			returnList.add(questionHeader(question, chatId));
			
			List<String> footer = new ArrayList<String>();
			
			if(question.getImagem() != null) { returnList.add(telegramObject(chatId, null, question.getImagem(), null)); }
			if(question.getImagemAlternativas() != null) { returnList.add(telegramObject(chatId, null, question.getImagemAlternativas(), null)); }			
			if(question.getQuestao() != null) { returnList.add(telegramObject(chatId, question.getQuestao(), null, footer)); }				
			
		}
		
		if(returnList.size() == 0) {
			returnList = returnSimpleMessage("Questão não encontrada", chatId);
		}
		if(returnList.size() >= 10) {
			returnList = returnSimpleMessage("Foram encontradas mais de 10 questões com o termo pesquisado. Por favor, refine sua pesquisa.", chatId);
		}	
		
		return returnList;
	}
	
	private TelegramDto questionHeader(QuestionDto question, String chatId) {
		StringBuilder header = new StringBuilder();
		header.append("Questão azul: " + question.getAzul().toString());
		header.append(", Ano: "+question.getAno().toString());		
		
		TelegramDto newObject = new TelegramDto(chatId, header.toString(), null, null);
		return newObject;
	}
	
	private TelegramDto telegramObject(String chatId, String message, String photo, List<String> inLineKeys) {
		TelegramDto newObject = new TelegramDto(chatId, message, photo, inLineKeys);
		return newObject;
	}
	
	
	public InlineKeyboardMarkup replyKeyboard(List<String> inLineKeys) {
		
		List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        List<InlineKeyboardButton> secondRow = new ArrayList<>();
        
		if(inLineKeys.size() < 6) {
			List<String> optionsList = new ArrayList<String>();
			optionsList.add("A");
			optionsList.add("B");
			optionsList.add("C");
			optionsList.add("D");
			optionsList.add("E");
			
	        for(String option:optionsList) {
	        	InlineKeyboardButton newButton = new InlineKeyboardButton();
	        	newButton.setText("⚪️"+option);
	        	newButton.setCallbackData(option);        	
	        	firstRow.add(newButton);
	        }
	        
	    	InlineKeyboardButton pularButton = new InlineKeyboardButton();
	    	pularButton.setText("Pular");
	    	pularButton.setCallbackData("Pular");    	
	    	
	    	InlineKeyboardButton encerrarButton = new InlineKeyboardButton();
	    	encerrarButton.setText("Encerrar");
	    	encerrarButton.setCallbackData("Encerrar");  
	    	secondRow.add(pularButton);
	    	secondRow.add(encerrarButton);

	        // Add buttons A, B, C, D, E, and "pular" to the row
	        // Add the row to the keyboard
	        keyboard.add(firstRow);
	        keyboard.add(secondRow);
	        
		}
		
		else {
	        for(String inLineKey:inLineKeys) {
	        	InlineKeyboardButton newButton = new InlineKeyboardButton();
	        	newButton.setText(inLineKey);
	        	newButton.setCallbackData(inLineKey);  
	        	List<InlineKeyboardButton> newRow = new ArrayList<>();
	        	newRow.add(newButton);
	        	keyboard.add(newRow);
	        }
		}

        // Create an InlineKeyboardMarkup object with the keyboard
        InlineKeyboardMarkup markupKeyboard = new InlineKeyboardMarkup();
        markupKeyboard.setKeyboard(keyboard);
        
        return markupKeyboard;
	}
	
public InlineKeyboardMarkup replyKeyboardAnswered(Integer correct, Integer answer, Boolean hideAnswer) {
		
		List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        List<InlineKeyboardButton> secondRow = new ArrayList<>();
        
			List<String> optionsList = new ArrayList<String>();
			optionsList.add("A");
			optionsList.add("B");
			optionsList.add("C");
			optionsList.add("D");
			optionsList.add("E");
			
	        for(String option:optionsList) {
	        	InlineKeyboardButton newButton = new InlineKeyboardButton();
	        	newButton.setText("⚪️"+option);
	        	newButton.setCallbackData(option);        	
	        	firstRow.add(newButton);
	        }
	        
	        for(int i =1; i<firstRow.size()+1; i++) {
	        	String buttonText = firstRow.get(i-1).getCallbackData();
	        	if(i == answer && hideAnswer) {
	        		firstRow.get(i-1).setText("🟡 "+buttonText);
	        	}
	        	
	        	if(i == answer && !hideAnswer) {
	        		firstRow.get(i-1).setText("🔴 "+buttonText);
	        	}
	        	
	        	if(i == correct && !hideAnswer) {
	        		firstRow.get(i-1).setText("🟢 "+buttonText);
	        	}
	        	
	        	if(correct == 0 && !hideAnswer) {
	        		firstRow.get(i-1).setText("🟢 "+buttonText);
	        	}
	        }
	        
	    	InlineKeyboardButton pularButton = new InlineKeyboardButton();
	    	pularButton.setText("Pular");
	    	pularButton.setCallbackData("Pular");    	
	    	
	    	InlineKeyboardButton encerrarButton = new InlineKeyboardButton();
	    	encerrarButton.setText("Encerrar");
	    	encerrarButton.setCallbackData("Encerrar");  
	    	secondRow.add(pularButton);
	    	secondRow.add(encerrarButton);

	        // Add buttons A, B, C, D, E, and "pular" to the row
	        // Add the row to the keyboard
	        keyboard.add(firstRow);
	        keyboard.add(secondRow);		
		

		// Create an InlineKeyboardMarkup object with the keyboard
        InlineKeyboardMarkup markupKeyboard = new InlineKeyboardMarkup();
        markupKeyboard.setKeyboard(keyboard);
        
        return markupKeyboard;
	}
	
	public Integer getAuxiliaryResponse(String answer) {
		Integer response = 0;
		switch(answer){
			case ("A"): return 1;
			case ("B"): return 2;
			case ("C"): return 3;
			case ("D"): return 4;
			case ("E"): return 5;
			case ("Pular"): return 10;
			case ("Encerrar"): return 99;
		}
		
		return response;
	}
	
}
