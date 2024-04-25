package com.enemSimulado.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.enemSimulado.dto.SessionDto;

@Repository
public interface SessionRepository extends JpaRepository<SessionDto, Long>{

}