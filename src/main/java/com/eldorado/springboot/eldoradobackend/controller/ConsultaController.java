package com.eldorado.springboot.eldoradobackend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.eldorado.springboot.eldoradobackend.exception.*;
import com.eldorado.springboot.eldoradobackend.model.Cliente;
import com.eldorado.springboot.eldoradobackend.model.Consulta;
import com.eldorado.springboot.eldoradobackend.repository.ClienteRepository;
import com.eldorado.springboot.eldoradobackend.repository.ConsultaRepository;
import com.eldorado.springboot.eldoradobackend.repository.MedicoRepository;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class ConsultaController {
    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private ClienteRepository ClienteRepository;

    @Autowired
    private MedicoRepository MedicoRepository;

    @RequestMapping(value = "/consultas", method = RequestMethod.GET)
    public List<Consulta> getAllConsulta() {
        return consultaRepository.findAll();
    }

    @GetMapping("/medicos/{id}/consultas")
    public Page<Consulta> getAllConsultasByMedicoId(@PathVariable(value = "id") Long consultaid, Pageable pageable) {
        return consultaRepository.findByMedicoId(consultaid, pageable);
    }

    @GetMapping("/clientes/{id}/consultas")
    public Page<Consulta> getAllConsultasByClienteId(@PathVariable(value = "id") Long consultaid, Pageable pageable) {
        return consultaRepository.findByClienteId(consultaid, pageable);
    }

    @GetMapping("/consultas/{id}")
    public ResponseEntity<Consulta> getConsultaById(@PathVariable(value = "id") Long consultaId)
            throws ResourceNotFoundException {
        Consulta consulta = consultaRepository.findById(consultaId).orElseThrow(
                () -> new ResourceNotFoundException("Consulta não encontrado utilizando essa id: " + consultaId));
        return ResponseEntity.ok().body(consulta);
    }

    @PostMapping("/medicos/{id}/clientes/{idc}/consultas")
    public Consulta createConsulta(@PathVariable(value = "id") Long medicoId,
            @PathVariable(value = "idc") Long clienteId, @Validated @RequestBody Consulta consulta)
            throws ResourceNotFoundException {
        return MedicoRepository.findById(medicoId).map(medico -> {
            consulta.setMedico(medico);
            Cliente cliente = ClienteRepository.findById(clienteId).get();
            consulta.setCliente(cliente);
            return consultaRepository.save(consulta);
        }).orElseThrow(() -> new ResourceNotFoundException("PostId " + consulta + " not found"));
    }

    @PutMapping("/medicos/{id}/clientes/{idc}/consultas/{idconsulta}")
    public Consulta updateConsulta(@PathVariable(value = "id") Long medicoId,
            @PathVariable(value = "idc") Long clienteId, @PathVariable(value = "idconsulta") Long consultaid,
            @Validated @RequestBody Consulta consultaRequest) throws ResourceNotFoundException {
        if (!MedicoRepository.existsById(medicoId)) {
            throw new ResourceNotFoundException("Medico com a id: " + medicoId + " não encontrado ");
        }
        if (!ClienteRepository.existsById(clienteId)) {
            throw new ResourceNotFoundException("Cliente com a id: " + clienteId + " não encontrado ");
        }

        return consultaRepository.findById(consultaid).map(consulta -> {
            consulta.setStatus(consultaRequest.getStatus());
            consulta.setDiaMarcado(consultaRequest.getDiaMarcado());
            return consultaRepository.save(consulta);
        }).orElseThrow(() -> new ResourceNotFoundException("Consulta com o id: " + consultaid + "não encontrado"));
    }

    @DeleteMapping("/consultas/{id}")
    public Map<String, Boolean> deleteConsulta(@PathVariable(value = "id") Long consultaId)
            throws ResourceNotFoundException {
        Consulta consulta = consultaRepository.findById(consultaId).orElseThrow(
                () -> new ResourceNotFoundException("Medico não encontrado utilizando essa id: " + consultaId));

        consultaRepository.delete(consulta);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deletado", Boolean.TRUE);
        return response;
    }
}