package com.enemSimulado.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.enemSimulado.dto.SessionDto;

import jakarta.transaction.Transactional;

@Repository
public interface SessionRepository extends JpaRepository<SessionDto, Long> {

	List<SessionDto> findBytelegramChatId(String chatId);
	List<SessionDto> findBytelegramChatIdAndIsActive(String chatId, Long isActive);
	List<SessionDto> findAllBytelegramChatId(String chatId);
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("UPDATE SessionDto SET isActive = 1 WHERE telegramChatId = :chatId AND isActive = 0 AND stage = 401 and id IN"
			+ "(SELECT max(id) FROM SessionDto WHERE telegramChatId = :chatId AND isActive = 0 AND stage = 401)")
	void reactiveSessionByChatId(String chatId);

}
