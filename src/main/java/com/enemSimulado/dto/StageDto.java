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
	private Integer stage;
	
	@Column
	private String telegramCommand;

	@Column
	private String description;
	
	@Column
	private Integer parentStage;
	
	@Column
	private Integer childStage;
	
	@Column
	private String message;
	
	@Column
	private Integer hasFollowUp;	
	
	@Column
	private Integer showMenu;
	
	@Column
	private Integer adminStage;
}