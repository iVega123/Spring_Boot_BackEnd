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
import com.eldorado.springboot.eldoradobackend.model.Cliente;
import com.eldorado.springboot.eldoradobackend.repository.ClienteRepository;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class ClienteController {
    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/clientes")
    public List<Cliente> getAllClientes() {
        return clienteRepository.findAll();
    }

    @GetMapping("/clientes/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable(value = "id") Long clienteId)
            throws ResourceNotFoundException {
        Cliente cliente = clienteRepository.findById(clienteId).orElseThrow(
                () -> new ResourceNotFoundException("Cliente não encontrado utilizando essa id: " + clienteId));
        return ResponseEntity.ok().body(cliente);
    }

    @PostMapping("/clientes")
    public Cliente createCliente(@Validated @RequestBody Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @PutMapping("/cliente/{id}")
    public ResponseEntity<Cliente> updateCliente(@PathVariable(value = "id") Long clienteId,
         @Validated @RequestBody Cliente clienteDetails) throws ResourceNotFoundException {
        Cliente cliente = clienteRepository.findById(clienteId)
        .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado utilizando essa id: " + clienteId));

        cliente.setNome(clienteDetails.getNome());
        cliente.setEmail(clienteDetails.getEmail());
        final Cliente updatedCliente = clienteRepository.save(cliente);
        return ResponseEntity.ok(updatedCliente);
    }

    @DeleteMapping("/clientes/{id}")
    public Map<String, Boolean> deleteCliente(@PathVariable(value = "id") Long clienteId)
         throws ResourceNotFoundException {
        Cliente cliente = clienteRepository.findById(clienteId)
       .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado utilizando essa id: " + clienteId));

        clienteRepository.delete(cliente);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deletado", Boolean.TRUE);
        return response;
    }
}