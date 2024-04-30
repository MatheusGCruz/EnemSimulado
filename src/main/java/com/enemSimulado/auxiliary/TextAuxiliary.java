package com.enemSimulado.auxiliary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

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
		TelegramDto addMessage = new TelegramDto(chatId, message, null);
		returnList.add(addMessage);
		
		return returnList;
	}
	
	
	public List<TelegramDto> question2Telegram(List<QuestionDto> questionList, String chatId){
		List<TelegramDto> returnList = new ArrayList<TelegramDto>();
		
		for(QuestionDto question:questionList) {
			if(question.getImagem() != null) { returnList.add(telegramObject(chatId, null, question.getImagem())); }
			if(question.getQuestao() != null) { returnList.add(telegramObject(chatId, question.getQuestao(), null)); }
			if(question.getImagemAlternativas() != null) { returnList.add(telegramObject(chatId, null, question.getImagemAlternativas())); }
		}
		
		return returnList;
	}
	
	
	private TelegramDto telegramObject(String chatId, String message, String photo) {
		TelegramDto newObject = new TelegramDto(chatId, message, photo);
		return newObject;
	}
	
}
