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
	private Integer stage;
	
	@Column
	private Integer nextStage;
	
	@Column
	private Integer isActive;
	
	@Column
	private LocalDateTime createdAt;
	
	@Column
	private LocalDateTime simulEndedAt;
	
	@Column
	private LocalDateTime endedAt;
	
	// Simulado
	@Column
	private Integer ano;
	
	@Column
	private Integer matriz;
	
	@Column
	private Integer linguagem;
	
	@Column
	private Integer quantidadeTopico;
	
	@Column
	private Integer ocultarCorreta;
	
}
