package com.enemSimulado.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.enemSimulado.dto.QuestionDto;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionDto, Long>{

	List<QuestionDto> findByQuestaoContaining(String texto);
	
	List<QuestionDto> findByAnoAndAzul(Integer ano, Integer azul);

	QuestionDto findByAzulAndAnoAndMatrizAndLinguagem(Integer questao, Integer ano, Integer matriz, Integer linguagem);

}
