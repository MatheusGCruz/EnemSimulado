package com.enemSimulado.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.enemSimulado.dto.UserDto;

@Repository
public interface UserRepository extends JpaRepository<UserDto, Long> {

}
