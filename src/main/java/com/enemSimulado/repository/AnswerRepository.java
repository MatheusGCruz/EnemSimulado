package com.enemSimulado.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.enemSimulado.dto.AnswerDto;

import jakarta.transaction.Transactional;

@Repository
public interface AnswerRepository extends JpaRepository<AnswerDto, Long>{
	
	@Query("SELECT COUNT(*) FROM AnswerDto WHERE chatId = :chatId AND currentlyActive = 1")
    Long getQuantityAnswered(@Param("chatId") String chatId);
	
	@Query("SELECT COUNT(*) FROM AnswerDto WHERE chatId = :chatId AND currentlyActive = 1 AND answerId = 0")
    Long getQuantityNotAnswered(@Param("chatId") String chatId);
	
	@Query("SELECT DATEDIFF(SECOND, MIN(answeredAt),MAX(createdAt)) FROM AnswerDto WHERE chatId = :chatId AND currentlyActive = 1")
    Integer getTimeElapsed(@Param("chatId") String chatId);
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("UPDATE AnswerDto SET currentlyActive = 0 WHERE chatId = :chatId AND currentlyActive = 1")
    void inactivateAnswerSessions(@Param("chatId") String chatId);
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("UPDATE AnswerDto SET messageId = :messageId WHERE chatId = :chatId AND currentlyActive = 1 AND messageId is null")
    void updateAnswer(@Param("chatId") String chatId, @Param("chatId") String messageId);

	//@Query("SELECT * FROM AnswerDto WHERE chatId = :chatId AND currentlyActive = 1 AND messageId is null")
    //AnswerDto getNotAnswered(@Param("chatId") String chatId);

	AnswerDto getByChatIdAndMessageIdAndCurrentlyActive(String chatId, String messageId, int i);
	List<AnswerDto> getByChatIdAndCurrentlyActive(String chatId, int i);

	@Query("SELECT COUNT(*) FROM AnswerDto WHERE chatId = :chatId AND currentlyActive = 1 AND matrix = :matrix AND answerId = correctAnswerId")
	Integer getCorrectAnswersByMatrix(String chatId, int matrix);
	
	@Query("SELECT COUNT(*) FROM AnswerDto WHERE chatId = :chatId AND currentlyActive = 1 AND matrix = :matrix")
	Integer getAnswersByMatrix(String chatId, int matrix);
	
	@Query("SELECT COUNT(*) FROM AnswerDto WHERE chatId = :chatId AND currentlyActive = 1 AND answerId = correctAnswerId")
	Integer getAllCorrectAnswers(String chatId);
	
	@Query("SELECT COUNT(*) FROM AnswerDto WHERE chatId = :chatId AND currentlyActive = 1")
	Integer getAllAnswers(String chatId);


}
