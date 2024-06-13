package com.enemSimulado.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enemSimulado.dto.ContactDto;


@Repository
public interface ContactRepository extends JpaRepository<ContactDto, Long>{

}
