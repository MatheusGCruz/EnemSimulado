package com.enemSimulado.dto;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import lombok.Data;

@Data
public class TelegramDto {
	
	public TelegramDto(String chatId, String text, String photo, String footer) {
		this.chatId = chatId;
		this.text = text;
		this.photo = photo;	
		this.inLineKeys = footer;
	}

	@Column
	private String chatId;
	
	@Column
	private String text;
	
	@Column
	private String photo;
	
	@Transient
	private String inLineKeys;
	

}
