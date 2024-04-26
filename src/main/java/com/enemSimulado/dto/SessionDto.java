package com.enemSimulado.dto;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class SessionDto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column
	private String telegramChatId;
	
	@Column
	private int stage;
	
	@Column
	private int isActive;
	
	@Column
	private LocalDateTime createdAt;
	
	@Column
	private LocalDateTime simulEndedAt;
	
	@Column
	private LocalDateTime endedAt;
}