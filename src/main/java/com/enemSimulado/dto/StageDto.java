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
public class StageDto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column
	private String stage;
	
	@Column
	private String telegramCommand;

	@Column
	private String description;
	
	@Column
	private String parentStage;
	
	@Column
	private String message;
}