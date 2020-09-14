package com.eldorado.springboot.eldoradobackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eldorado.springboot.eldoradobackend.model.*;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long>{

}