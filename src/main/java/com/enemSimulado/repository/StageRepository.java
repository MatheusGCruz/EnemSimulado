package com.enemSimulado.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enemSimulado.dto.StageDto;

@Repository
public interface StageRepository  extends JpaRepository<StageDto, Long> {

	StageDto findOneBytelegramCommand(String command);

	StageDto findFirstByStage(Integer stage);

}
