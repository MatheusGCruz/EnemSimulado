package com.enemSimulado.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.enemSimulado.dto.MatrixDto;

@Repository
public interface MatrixRepository extends JpaRepository<MatrixDto, Long>{

}