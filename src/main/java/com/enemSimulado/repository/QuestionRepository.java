package com.enemSimulado.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.enemSimulado.dto.QuestionDto;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionDto, Long>{

}
