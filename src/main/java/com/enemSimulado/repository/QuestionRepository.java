package com.enemSimulado.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.enemSimulado.dto.QuestionDto;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionDto, Long>{

	List<QuestionDto> findByQuestaoContaining(String texto);
	
	List<QuestionDto> findByAnoAndAzul(Integer ano, Integer azul);

	QuestionDto findByAzulAndAnoAndMatrizAndLinguagem(Integer questao, Integer ano, Integer matriz, Integer linguagem);
	
	@Query("SELECT Q.id FROM QuestionDto Q LEFT JOIN AnswerDto A  ON A.questionId = Q.id AND A.chatId = :chatId AND A.currentlyActive = 1 WHERE Q.matriz = :matriz AND A.chatId is null")
	List<Integer> getAllValidQuestionsId(@Param("chatId") String chatId,@Param("matriz") Integer matriz);

}
