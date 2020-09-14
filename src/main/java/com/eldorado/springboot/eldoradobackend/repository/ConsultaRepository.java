package com.eldorado.springboot.eldoradobackend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eldorado.springboot.eldoradobackend.model.*;

import java.util.Optional;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long>{
    Page<Consulta> findByMedicoId(Long medicoId, Pageable pageable);
    Page<Consulta> findByClienteId(Long clienteId, Pageable pageable);
    Optional<Consulta> findByMedicoIdAndClienteId(Long medicoId, Long clienteId);
}