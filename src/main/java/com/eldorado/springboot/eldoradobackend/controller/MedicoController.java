package com.eldorado.springboot.eldoradobackend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eldorado.springboot.eldoradobackend.exception.*;
import com.eldorado.springboot.eldoradobackend.model.Medico;
import com.eldorado.springboot.eldoradobackend.repository.MedicoRepository;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/")
public class MedicoController {
    @Autowired
    private MedicoRepository medicoRepository;

    @GetMapping("/medicos")
    public List<Medico> getAllMedicos() {
        return medicoRepository.findAll();
    }

    @GetMapping("/medicos/{id}")
    public ResponseEntity<Medico> getMedicoById(@PathVariable(value = "id") Long medicoId)
            throws ResourceNotFoundException {
        Medico medico = medicoRepository.findById(medicoId).orElseThrow(
                () -> new ResourceNotFoundException("Medico não encontrado utilizando essa id: " + medicoId));
        return ResponseEntity.ok().body(medico);
    }

    @PostMapping("/medicos")
    public Medico createMedico(@Validated @RequestBody Medico medico) {
        return medicoRepository.save(medico);
    }

    @PutMapping("/medicos/{id}")
    public ResponseEntity<Medico> updateMedico(@PathVariable(value = "id") Long medicoId,
         @Validated @RequestBody Medico medicoDetails) throws ResourceNotFoundException {
        Medico medico = medicoRepository.findById(medicoId)
        .orElseThrow(() -> new ResourceNotFoundException("Medico não encontrado utilizando essa id: " + medicoId));

        medico.setNome(medicoDetails.getNome());
        medico.setEmail(medicoDetails.getEmail());
        medico.setSenha(medicoDetails.getSenha());
        final Medico updatedMedico = medicoRepository.save(medico);
        return ResponseEntity.ok(updatedMedico);
    }

    @DeleteMapping("/medicos/{id}")
    public Map<String, Boolean> deleteMedico(@PathVariable(value = "id") Long medicoId)
         throws ResourceNotFoundException {
        Medico medico = medicoRepository.findById(medicoId)
       .orElseThrow(() -> new ResourceNotFoundException("Medico não encontrado utilizando essa id: " + medicoId));

        medicoRepository.delete(medico);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deletado", Boolean.TRUE);
        return response;
    }
}