package com.enemSimulado.dto;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class ContactDto {
	
	public ContactDto(String chatId, String contactMessage, Integer contactType,Integer contactStatus) {
		this.telegramChatId = chatId;
		this.contactType = contactType;
		this.contactMessage = contactMessage;	
		this.contactStatus = contactStatus;
	}
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column
	private LocalDateTime createdAt;
	
	@Column
	private String telegramChatId;
	
	@Column
	private Integer contactType;
	
	@Column 
	private String contactMessage;
	
	@Column
	private String contactResponse;
	
	@Column
	private Integer contactStatus;
	
	@Column
	private LocalDateTime respondedAt;	
	
}



