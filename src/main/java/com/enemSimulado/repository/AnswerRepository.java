package com.enemSimulado.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.enemSimulado.dto.AnswerDto;

@Repository
public interface AnswerRepository extends JpaRepository<AnswerDto, Long>{
	
	@Query("SELECT COUNT(*) FROM AnswerDto WHERE chatId = :chatId AND currentlyActive = 1")
    Long getQuantityAnswered(@Param("chatId") String chatId);

}
