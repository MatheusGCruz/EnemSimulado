package com.enemSimulado.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.enemSimulado.dto.SessionDto;

@Repository
public interface SessionRepository extends JpaRepository<SessionDto, Long> {

	List<SessionDto> findBytelegramChatId(String chatId);
	List<SessionDto> findBytelegramChatIdAndIsActive(String chatId, Long isActive);
	List<SessionDto> findAllBytelegramChatId(String chatId);
}
