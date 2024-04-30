package com.enemSimulado.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;

@Data
public class TelegramDto {
	
	public TelegramDto(String chatId, String text, String photo) {
		this.chatId = chatId;
		this.text = text;
		this.photo = photo;		
	}

	@Column
	private String chatId;
	
	@Column
	private String text;
	
	@Column
	private String photo;

}
