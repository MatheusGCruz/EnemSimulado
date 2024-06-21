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
public class TipDto {
	public TipDto(String telegramChatId, String tipMessage, Integer questionId) {
		this.telegramChatId = telegramChatId;
		this.tipMessage = tipMessage;
		this.questionId = questionId;	
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column
	private LocalDateTime createdAt;
	
	@Column
	private String telegramChatId;
	
	@Column 
	private String tipMessage;
	
	@Column
	private Integer questionId; 	
	
	@Column
	private Integer upVotes;
	
	@Column
	private Integer downVotes;	
	
}