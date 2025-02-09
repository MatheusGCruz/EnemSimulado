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
		
		@Column(columnDefinition="nvarchar(max)")
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
		private Integer  alternativaCorreta;
		
		@Column
		private Integer  ano;
		
		@Column
		private Integer  sessao;
		
		@Column
		private Integer  azul;
		
		@Column
		private Integer  amarelo;
		
		@Column
		private Integer  branco;
		
		@Column
		private Integer  rosa;
		
		@Column
		private Integer  laranja;		
		
		@Column
		private Integer  cinza;		
		
		@Column
		private Integer  verde;
		
		@Column
		private String imagem;
		
		@Column
		private String imagemAux;
		
		@Column
		private String imagemAlternativas;
		
		@Column
		private Integer linguagem;
		
		@Column
		private Integer matriz;


}
