package com.enemSimulado.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class QuestionDto {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;
		
		@Column
		private String questao;
		
		@Column
		private String alternativaA;
		
		@Column
		private String alternativaB;
		
		@Column
		private String alternativaC;
		
		@Column
		private String alternativaD;
		
		@Column
		private String alternativaE;
		
		@Column
		private int alternativaCorreta;
		
		@Column
		private int ano;
		
		@Column
		private int sessao;


}
