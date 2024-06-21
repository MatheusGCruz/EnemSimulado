package com.enemSimulado.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enemSimulado.dto.TipDto;


@Repository
public interface TipRepository extends JpaRepository<TipDto, Long>{

}