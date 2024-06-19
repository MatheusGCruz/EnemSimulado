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
public class AnswerDto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column
	private String chatId;
	
	@Column
	private Integer questionId;
	
	@Column
	private Integer matrix;
	
	@Column
	private Integer answerId;
	
	@Column
	private Integer correctAnswerId;
	
	@Column
	private Integer currentlyActive;
	
	@Column
	private LocalDateTime createdAt;
	
	@Column
	private LocalDateTime answeredAt;
	
	@Column
	private String messageId;
	
}