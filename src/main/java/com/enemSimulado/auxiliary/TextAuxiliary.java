package com.enemSimulado.auxiliary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

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
}
